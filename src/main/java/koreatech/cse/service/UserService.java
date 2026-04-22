package koreatech.cse.service;

import koreatech.cse.domain.Authority;
import koreatech.cse.domain.Contact;
import koreatech.cse.domain.User;
import koreatech.cse.domain.constant.AccountState;
import koreatech.cse.domain.constant.Role;
import koreatech.cse.domain.constant.SignupResult;
import koreatech.cse.domain.constant.Designation;
import koreatech.cse.domain.dto.AdminCreateRequest;
import koreatech.cse.domain.dto.AdminProfessorProfileUpdateDTO;
import koreatech.cse.domain.dto.AdminStudentProfileUpdateDTO;
import koreatech.cse.domain.dto.StudentProfileUpdateDTO;
import koreatech.cse.domain.role.student.GraduationResearchPlan;
import koreatech.cse.domain.role.student.StudentCourse;
import koreatech.cse.repository.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.joda.time.DateTime;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Inject
    private UserMapper userMapper;
    @Inject
    private AuthorityMapper authorityMapper;
    @Inject
    private PasswordEncoder passwordEncoder;
    @Inject
    private ContactMapper contactMapper;
    @Inject
    private StudentCourseMapper studentCourseMapper;
    @Inject
    private SemesterMapper semesterMapper;
    @Inject
    private UploadedFileMapper uploadedFileMapper;
    @Inject
    private FileService fileService;
    @Inject
    private GraduationResearchPlanMapper graduationResearchPlanMapper;

    @Transactional(rollbackFor = Exception.class)
    public SignupResult signup(User user, String confirmPassword) {
        UUID requestId = UUID.randomUUID();
        String number = user != null ? StringUtils.trimToNull(user.getNumber()) : null;
        logger.info("[{}] Signup attempt number={}", requestId, sanitizeForLog(number));

        try {
            if (user == null) {
                return SignupResult.INVALID_INPUT;
            }

            String rawUsername = user.getUsername();
            String rawNumber = user.getNumber();
            String password = user.getPassword();
            String passwordConfirmation = StringUtils.trimToNull(confirmPassword);
            Contact requestedContact = user.getContact();
            if (requestedContact == null) {
                logger.error("[{}] Signup failed: missing request contact number={}", requestId, sanitizeForLog(number));
                return SignupResult.INVALID_INPUT;
            }

            String rawFirstName = requestedContact.getFirstName();
            String rawLastName = requestedContact.getLastName();

            if (isEmptyInput(rawUsername) || isEmptyInput(rawNumber) || isEmptyInput(rawFirstName)
                    || isEmptyInput(rawLastName)) {
                return SignupResult.INVALID_INPUT;
            }

            String username = normalizeUsername(rawUsername);
            String firstName = StringUtils.trimToNull(rawFirstName);
            String lastName = StringUtils.trimToNull(rawLastName);

            if (number == null || username == null || StringUtils.isBlank(password) || passwordConfirmation == null
                    || firstName == null || lastName == null) {
                return SignupResult.INVALID_INPUT;
            }

            if (!password.equals(confirmPassword)) {
                return SignupResult.INVALID_INPUT;
            }

            User stored = userMapper.findByNumber(number);
            if (stored == null) {
                logger.warn("[{}] Signup failed: student not found number={}", requestId, sanitizeForLog(number));
                return SignupResult.STUDENT_NOT_FOUND;
            }

            Contact storedContact = contactMapper.findByUserId(stored.getId());
            if (storedContact == null) {
                logger.error("[{}] Signup failed: registered contact missing number={}", requestId, sanitizeForLog(number));
                return SignupResult.IDENTITY_MISMATCH;
            }

            if (!matchesRegisteredIdentity(storedContact, firstName, lastName)) {
                logger.warn("[{}] Signup failed: identity mismatch number={}", requestId, sanitizeForLog(number));
                return SignupResult.IDENTITY_MISMATCH;
            }

            User existingUser = userMapper.findByUsername(username);
            if (existingUser != null && existingUser.getId() != stored.getId()) {
                return SignupResult.USERNAME_TAKEN;
            }

            AccountState storedAccountState = stored.getAccountState();
            if (storedAccountState != AccountState.PENDING) {
                logger.warn("[{}] Signup blocked: already active number={} state={}", requestId, sanitizeForLog(number),
                        storedAccountState);
                return SignupResult.ALREADY_ACTIVE;
            }

            stored.setUsername(username);
            stored.setPassword(passwordEncoder.encode(password));
            stored.setAccountState(AccountState.ACTIVE);

            userMapper.updateFromSignup(stored);
            ensureAuthority(stored.getId(), Role.user);
            ensureAuthority(stored.getId(), Role.student);

            logger.info("[{}] User activated successfully number={}", requestId, sanitizeForLog(number));
            return SignupResult.SUCCESS;
        } catch (Exception e) {
            logger.error("[{}] Signup unexpected error number={}", requestId, sanitizeForLog(number), e);
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            }
            throw new IllegalStateException("Unexpected signup failure", e);
        }
    }

    public Boolean signupAdmin(AdminCreateRequest req) {

        if (req.getUsername() == null || req.getPassword() == null)
            return false;

        if (isUniqueUsername(req.getUsername().trim())) {
            User user = new User();
            user.setUsername(req.getUsername().trim());
            user.setPassword(passwordEncoder.encode(req.getPassword()));
            user.setAccountState(AccountState.ACTIVE);

            Contact contact = new Contact();
            contact.setFirstName(req.getFirstName());
            contact.setLastName(req.getLastName());
            user.setContact(contact);

            userMapper.insert(user);
            contact.setUserId(user.getId());
            contactMapper.insert(contact);

            Authority authority = new Authority();
            authority.setUserId(user.getId());
            authority.setRole(Role.user);
            authorityMapper.insert(authority);

            Authority adminAuthority = new Authority();
            adminAuthority.setUserId(user.getId());
            adminAuthority.setRole(Role.admin);
            authorityMapper.insert(adminAuthority);

            logger.info("Admin user created id={}", user.getId());
            return true;
        }
        return false;
    }

    /**
     * Register a new student (used by manual registration and Excel import).
     * Creates user, contact, and ROLE_STUDENT authority in single atomic
     * transaction.
     */
    @Transactional
    public Boolean register(User user, Role role) {
        if (user == null || role == null || user.getContact() == null) {
            return false;
        }

        String number = StringUtils.trimToNull(user.getNumber());
        if (number == null) {
            return false;
        }
        user.setNumber(number);

        if (role == Role.student) {
            normalizeStudentAdvisor(user);
        } else {
            user.setAdvisorId(0);
        }

        if (role == Role.professor) {
            Contact contact = user.getContact();
            String normalizedFirstName = StringUtils.trimToNull(contact.getFirstName());
            String normalizedLastName = StringUtils.trimToNull(contact.getLastName());
            if (normalizedFirstName == null || normalizedLastName == null || user.getDivisionId() <= 0) {
                return false;
            }
            contact.setFirstName(normalizedFirstName);
            contact.setLastName(normalizedLastName);
        }

        if (role == Role.professor) {
            User duplicateProfessor = userMapper.findByNumber(number);
            if (duplicateProfessor != null) {
                logger.warn("Professor registration rejected: duplicated professor number={}", sanitizeForLog(number));
                return false;
            }
        }

        User stored = userMapper.findByNumber(number);
        if (stored != null)
            return false;
        userMapper.insert(user);
        user.getContact().setUserId(user.getId());
        Contact contact = user.getContact();
        contactMapper.insert(contact);

        ensureAuthority(user.getId(), role);

        return true;
    }

    public int resolveStudentAdvisorId(int advisorId, int studentDivisionId) {
        if (advisorId <= 0) {
            return 0;
        }

        User advisor = userMapper.findOne(advisorId);
        if (advisor == null) {
            logger.warn("Invalid advisor assignment normalized to unassigned: advisorId={} not found", advisorId);
            return 0;
        }

        Authority advisorProfessorRole = authorityMapper.findByUserIdAndRole(advisorId, Role.professor.toCode());
        if (advisorProfessorRole == null) {
            logger.warn("Invalid advisor assignment normalized to unassigned: userId={} is not a professor", advisorId);
            return 0;
        }

        int advisorDivisionId = advisor.getDivisionId();
        if (studentDivisionId > 0 && advisorDivisionId > 0 && studentDivisionId != advisorDivisionId) {
            logger.warn("Invalid advisor assignment normalized to unassigned: studentDivision={} advisorDivision={} advisorId={}",
                    studentDivisionId, advisorDivisionId, advisorId);
            return 0;
        }
        return advisorId;
    }

    @Transactional
    public boolean updateAdminStudentProfile(AdminStudentProfileUpdateDTO updateDTO, MultipartFile profileFile)
            throws java.io.IOException {
        if (updateDTO == null || updateDTO.getId() <= 0 || updateDTO.getDivisionId() <= 0
                || updateDTO.getSchoolYear() <= 0 || StringUtils.isBlank(updateDTO.getStatus())) {
            return false;
        }

        User persistedUser = userMapper.findOne(updateDTO.getId());
        if (persistedUser == null) {
            return false;
        }

        Contact persistedContact = ensureContact(updateDTO.getId());
        persistedUser.setDivisionId(updateDTO.getDivisionId());
        persistedUser.setAdvisorId(resolveStudentAdvisorId(updateDTO.getAdvisorId(), updateDTO.getDivisionId()));
        persistedUser.setSchoolYear(updateDTO.getSchoolYear());
        persistedUser.setStatus(StringUtils.trimToNull(updateDTO.getStatus()));

        applyAdminStudentContactUpdate(persistedContact, updateDTO.getContact());
        userMapper.update(persistedUser);
        saveContact(persistedContact);
        replaceProfileImageIfPresent(profileFile, persistedUser);
        return true;
    }

    @Transactional
    public boolean updateAdminProfessorProfile(AdminProfessorProfileUpdateDTO updateDTO) {
        if (updateDTO == null || updateDTO.getId() <= 0 || updateDTO.getDivisionId() <= 0) {
            return false;
        }

        User persistedUser = userMapper.findOne(updateDTO.getId());
        if (persistedUser == null) {
            return false;
        }

        Contact persistedContact = ensureContact(updateDTO.getId());
        persistedUser.setDivisionId(updateDTO.getDivisionId());
        persistedUser.setAccountState(resolveProfessorAccountState(updateDTO.isEnabled(), persistedUser.getAccountState()));

        applyAdminProfessorContactUpdate(persistedContact, updateDTO.getContact());
        userMapper.update(persistedUser);
        userMapper.updateAccountState(persistedUser);
        saveContact(persistedContact);
        return true;
    }

    @Transactional
    public boolean updateStudentSchoolYear(int userId, int schoolYear) {
        if (userId <= 0 || schoolYear <= 0) {
            return false;
        }

        User persistedUser = userMapper.findOne(userId);
        if (persistedUser == null) {
            return false;
        }

        persistedUser.setSchoolYear(schoolYear);
        userMapper.update(persistedUser);
        return true;
    }

    @Transactional
    public boolean updateStudentSelfProfile(int userId, StudentProfileUpdateDTO updateDTO, MultipartFile profileFile)
            throws java.io.IOException {
        if (userId <= 0 || updateDTO == null) {
            return false;
        }

        User persistedUser = userMapper.findOne(userId);
        if (persistedUser == null) {
            return false;
        }

        Contact persistedContact = ensureContact(userId);
        applyStudentSelfProfileUpdate(persistedContact, updateDTO);
        saveContact(persistedContact);
        replaceProfileImageIfPresent(profileFile, persistedUser);
        return true;
    }

    @Transactional
    public boolean saveStudentGraduationResearchPlan(int userId, GraduationResearchPlan plan) {
        if (userId <= 0 || plan == null) {
            return false;
        }

        User persistedUser = userMapper.findOne(userId);
        if (persistedUser == null) {
            return false;
        }

        plan.setUserId(userId);
        plan.setYear(resolvePlanYear(plan.getSubmitDate()));

        GraduationResearchPlan stored = graduationResearchPlanMapper.findByUserId(userId);
        if (stored == null) {
            plan.setApprove(-1);
            graduationResearchPlanMapper.insert(plan);
            return true;
        }

        plan.setId(stored.getId());
        plan.setApprove(stored.getApprove());
        graduationResearchPlanMapper.update(plan);
        return true;
    }

    private void normalizeStudentAdvisor(User user) {
        user.setAdvisorId(resolveStudentAdvisorId(user.getAdvisorId(), user.getDivisionId()));
    }

    private int resolvePlanYear(String submitDate) {
        if (StringUtils.isBlank(submitDate)) {
            return 0;
        }

        try {
            Date parsedDate = koreatech.cse.util.DateHelper.parse("yyyy-MM-dd", submitDate);
            return new DateTime(parsedDate).getYear();
        } catch (Exception ignored) {
            return 0;
        }
    }

    private void applyStudentSelfProfileUpdate(Contact persistedContact, StudentProfileUpdateDTO updateDTO) {
        persistedContact.setFirstName(StringUtils.trimToNull(updateDTO.getFirstName()));
        persistedContact.setLastName(StringUtils.trimToNull(updateDTO.getLastName()));

        Contact submittedContact = updateDTO.getContact();
        persistedContact.setCellPhone(trimContactValue(submittedContact == null ? null : submittedContact.getCellPhone()));
        persistedContact.setPhone(trimContactValue(submittedContact == null ? null : submittedContact.getPhone()));
        persistedContact.setPostCode(trimContactValue(submittedContact == null ? null : submittedContact.getPostCode()));
        persistedContact.setAddress(trimContactValue(submittedContact == null ? null : submittedContact.getAddress()));
        persistedContact.setParentName(trimContactValue(submittedContact == null ? null : submittedContact.getParentName()));
        persistedContact.setRelation(trimContactValue(submittedContact == null ? null : submittedContact.getRelation()));
        persistedContact.setParentCellPhone(trimContactValue(submittedContact == null ? null : submittedContact.getParentCellPhone()));
        persistedContact.setParentPhone(trimContactValue(submittedContact == null ? null : submittedContact.getParentPhone()));
        persistedContact.setParentPostCode(trimContactValue(submittedContact == null ? null : submittedContact.getParentPostCode()));
        persistedContact.setParentAddress(trimContactValue(submittedContact == null ? null : submittedContact.getParentAddress()));
    }

    private void applyAdminStudentContactUpdate(Contact persistedContact, Contact submittedContact) {
        persistedContact.setFirstName(trimContactValue(submittedContact == null ? null : submittedContact.getFirstName()));
        persistedContact.setLastName(trimContactValue(submittedContact == null ? null : submittedContact.getLastName()));
        persistedContact.setGradYear(trimContactValue(submittedContact == null ? null : submittedContact.getGradYear()));
        persistedContact.setGradSemester(trimContactValue(submittedContact == null ? null : submittedContact.getGradSemester()));
        persistedContact.setGradDate(trimContactValue(submittedContact == null ? null : submittedContact.getGradDate()));
        persistedContact.setGradDegree(trimContactValue(submittedContact == null ? null : submittedContact.getGradDegree()));
        persistedContact.setDegreeNumber(trimContactValue(submittedContact == null ? null : submittedContact.getDegreeNumber()));
        persistedContact.setCertNumber(trimContactValue(submittedContact == null ? null : submittedContact.getCertNumber()));
    }

    private void applyAdminProfessorContactUpdate(Contact persistedContact, Contact submittedContact) {
        persistedContact.setFirstName(trimContactValue(submittedContact == null ? null : submittedContact.getFirstName()));
        persistedContact.setLastName(trimContactValue(submittedContact == null ? null : submittedContact.getLastName()));
    }

    private Contact ensureContact(int userId) {
        Contact persistedContact = contactMapper.findByUserId(userId);
        if (persistedContact != null) {
            return persistedContact;
        }

        Contact newContact = new Contact();
        newContact.setUserId(userId);
        return newContact;
    }

    private void saveContact(Contact contact) {
        Contact storedContact = contactMapper.findByUserId(contact.getUserId());
        if (storedContact == null) {
            contactMapper.insert(contact);
            return;
        }

        contact.setId(storedContact.getId());
        contactMapper.update(contact);
    }

    private void replaceProfileImageIfPresent(MultipartFile multipartFile, User persistedUser) throws java.io.IOException {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return;
        }

        String extension = fileService.getExtension(multipartFile.getOriginalFilename());
        if (!isSupportedProfileImage(extension)) {
            return;
        }

        uploadedFileMapper.deleteProfileByUser(persistedUser);
        DateTime now = new DateTime();
        fileService.processUploadedFile(multipartFile, persistedUser, Designation.profile, 0, 0, now.getYear());
    }

    private boolean isSupportedProfileImage(String extension) {
        if (extension == null) {
            return false;
        }

        return "png".equalsIgnoreCase(extension)
                || "jpg".equalsIgnoreCase(extension)
                || "jpeg".equalsIgnoreCase(extension)
                || "gif".equalsIgnoreCase(extension);
    }

    private String trimContactValue(String value) {
        return StringUtils.trimToNull(value);
    }

    private AccountState resolveProfessorAccountState(boolean enabled, AccountState currentState) {
        if (enabled) {
            return AccountState.ACTIVE;
        }
        return currentState == AccountState.PENDING ? AccountState.PENDING : AccountState.DISABLED;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username/password.");
        }
        List<Authority> authorities = authorityMapper.findByUserId(user.getId());
        user.setAuthorities(authorities);
        return user;
    }

    public boolean isUniqueUsername(String username) {
        return userMapper.findByUsername(username) == null;
    }

    private boolean matchesRegisteredIdentity(Contact storedContact, String firstName, String lastName) {
        if (storedContact == null) {
            return false;
        }

        return normalizeIdentityValue(storedContact.getFirstName()).equals(normalizeIdentityValue(firstName))
                && normalizeIdentityValue(storedContact.getLastName()).equals(normalizeIdentityValue(lastName));
    }

    private String normalizeIdentityValue(String value) {
        return StringUtils.trimToEmpty(value).toLowerCase(Locale.ROOT);
    }

    private boolean isEmptyInput(String value) {
        return value == null || value.trim().isEmpty();
    }

    private String sanitizeForLog(String value) {
        if (value == null) return "n/a";
        return value.replace('\r', '_').replace('\n', '_');
    }

    private String normalizeUsername(String username) {
        return StringUtils.trimToNull(username) == null ? null : username.trim().toLowerCase(Locale.ROOT);
    }

    private void ensureAuthority(int userId, Role role) {
        Authority existingAuthority = authorityMapper.findByUserIdAndRole(userId, role.toCode());
        if (existingAuthority != null) {
            return;
        }

        Authority authority = new Authority();
        authority.setUserId(userId);
        authority.setRole(role);
        authorityMapper.insert(authority);
    }

    public int getCompleteSemesterCount(int userId) {
        LinkedHashSet<Integer> semesterSet = studentCourseMapper.findSemesterIdByUserIdValid(userId);
        if (semesterSet == null || semesterSet.isEmpty()) {
            return 0;
        }

        List<Integer> semesterIds = new ArrayList<>(semesterSet);

        List<Map<String, Object>> validRows =
                studentCourseMapper.countValidByUserIdGroupBySemester(userId, semesterIds);
        List<Map<String, Object>> totalRows =
                studentCourseMapper.countByUserIdGroupBySemester(userId, semesterIds);

        Map<Integer, Long> validCounts = toSemesterCountMap(validRows);
        Map<Integer, Long> totalCounts = toSemesterCountMap(totalRows);

        int completeSemesterCount = 0;
        for (Integer semesterId : semesterIds) {
            long valid = validCounts.getOrDefault(semesterId, 0L);
            long total = totalCounts.getOrDefault(semesterId, 0L);
            if (total > 0 && valid == total) {
                completeSemesterCount++;
            }
        }
        return completeSemesterCount;
    }

    private static Map<Integer, Long> toSemesterCountMap(List<Map<String, Object>> rows) {
        Map<Integer, Long> result = new HashMap<>();
        for (Map<String, Object> row : rows) {
            int semesterId = ((Number) row.get("semesterId")).intValue();
            long cnt = ((Number) row.get("cnt")).longValue();
            result.put(semesterId, cnt);
        }
        return result;
    }

}
