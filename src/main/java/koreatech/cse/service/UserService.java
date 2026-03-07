package koreatech.cse.service;

import koreatech.cse.domain.Authority;
import koreatech.cse.domain.Contact;
import koreatech.cse.domain.User;
import koreatech.cse.domain.constant.Role;
import koreatech.cse.domain.role.student.StudentCourse;
import koreatech.cse.domain.univ.Semester;
import koreatech.cse.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
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

    public String signup(User user, Role role) {

        if (user.getUsername() == null || user.getPassword() == null)
            return "info";

        if (isUniqueUsername(user.getUsername().trim())) {

            User stored = userMapper.findByNumber(user.getNumber());
            if (stored == null)
                return "number";

            // Check if account is already activated
            if (stored.isConfirm()) {
                return "activated";
            }

            stored.setUsername(user.getUsername());
            stored.setPassword(passwordEncoder.encode(user.getPassword()));
            stored.setEnabled(true);
            stored.setConfirm(true);

            userMapper.updateFromSignup(stored);
            Contact contact = user.getContact();
            Contact storedContact = contactMapper.findByUserId(stored.getId());

            storedContact.setFirstName(contact.getFirstName());
            storedContact.setLastName(contact.getLastName());
            contactMapper.update(storedContact);

            // Insert ROLE_USER authority only if it doesn't already exist
            Authority existingUser = authorityMapper.findByUserIdAndRole(stored.getId(), Role.user.toCode());
            if (existingUser == null) {
                Authority authority = new Authority();
                authority.setUserId(stored.getId());
                authority.setRole(Role.user);
                authorityMapper.insert(authority);
            }

            // Insert role-specific authority only if it doesn't already exist
            if (role == Role.admin) {
                Authority existingAdmin = authorityMapper.findByUserIdAndRole(stored.getId(), Role.admin.toCode());
                if (existingAdmin == null) {
                    Authority adminAuthority = new Authority();
                    adminAuthority.setUserId(stored.getId());
                    adminAuthority.setRole(Role.admin);
                    authorityMapper.insert(adminAuthority);
                }
            } else if (role == Role.student) {
                Authority existingStudent = authorityMapper.findByUserIdAndRole(stored.getId(), Role.student.toCode());
                if (existingStudent == null) {
                    Authority studentAuthority = new Authority();
                    studentAuthority.setUserId(stored.getId());
                    studentAuthority.setRole(Role.student);
                    authorityMapper.insert(studentAuthority);
                }
            } else if (role == Role.professor) {
                Authority existingProf = authorityMapper.findByUserIdAndRole(stored.getId(), Role.professor.toCode());
                if (existingProf == null) {
                    Authority profAuthority = new Authority();
                    profAuthority.setUserId(stored.getId());
                    profAuthority.setRole(Role.professor);
                    authorityMapper.insert(profAuthority);
                }
            }
            System.out.println("user created :" + new Date());
            return "success";

        }
        return "error";
    }

    public Boolean signupAdmin(User user) {

        if (user.getUsername() == null || user.getPassword() == null)
            return false;

        if (isUniqueUsername(user.getUsername().trim())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userMapper.insert(user);
            user.getContact().setUserId(user.getId());
            Contact contact = user.getContact();
            contactMapper.insert(contact);

            Authority authority = new Authority();
            authority.setUserId(user.getId());
            authority.setRole(Role.user);
            authorityMapper.insert(authority);

            Authority adminAuthority = new Authority();
            adminAuthority.setUserId(user.getId());
            adminAuthority.setRole(Role.admin);
            authorityMapper.insert(adminAuthority);

            System.out.println("user created :" + new Date());
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
    public Boolean register(User user) {

        String number = user.getNumber();
        User stored = userMapper.findByNumber(number);
        if (stored != null)
            return false;
        userMapper.insert(user);
        user.getContact().setUserId(user.getId());
        Contact contact = user.getContact();
        contactMapper.insert(contact);

        // Add ROLE_STUDENT authority so student appears in queries
        Authority studentAuthority = new Authority();
        studentAuthority.setUserId(user.getId());
        studentAuthority.setRole(Role.student);
        authorityMapper.insert(studentAuthority);

        return true;
    }

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

    public int getCompleteSemesterCount(int userId) {
        LinkedHashSet<Integer> semesterSet = studentCourseMapper.findSemesterIdByUserIdValid(userId);
        if (semesterSet == null)
            return 0;

        int completeSemesterCount = 0;
        for (Integer semesterId : semesterSet) {
            List<StudentCourse> validCourses = studentCourseMapper.findByUserIdSemesterIdValid(userId, semesterId);
            List<StudentCourse> allCourses = studentCourseMapper.findByUserIdSemesterId(userId, semesterId);
            if (validCourses == null || allCourses == null)
                continue;
            if (validCourses.size() == allCourses.size())
                completeSemesterCount++;
        }
        return completeSemesterCount;
    }

}
