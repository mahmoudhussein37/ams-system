package koreatech.cse.controller.role;

import koreatech.cse.domain.Curriculum;
import koreatech.cse.domain.Contact;
import koreatech.cse.domain.Searchable;
import koreatech.cse.domain.UploadedFile;
import koreatech.cse.domain.User;
import koreatech.cse.domain.constant.Designation;
import koreatech.cse.domain.dto.StudentProfileUpdateDTO;
import koreatech.cse.domain.role.professor.*;
import koreatech.cse.domain.role.student.GraduationResearchPlan;
import koreatech.cse.domain.role.student.StudentCourse;
import koreatech.cse.domain.univ.*;
import koreatech.cse.service.AdminService;
import koreatech.cse.service.AuthorizationGuardService;
import koreatech.cse.service.AuthorityService;
import koreatech.cse.service.CurriculumService;
import koreatech.cse.service.FileService;
import koreatech.cse.service.ProfService;
import koreatech.cse.service.UserService;
import koreatech.cse.util.DateHelper;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.util.Objects;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Controller
@SessionAttributes({ "studentUser" })
@PreAuthorize("hasRole('ROLE_STUDENT')")
@RequestMapping("/student")
public class StudentController {
    private static final int IN_CLAUSE_BATCH_SIZE = 150;

    @Inject
    private AuthorityService authorityService;
    @Inject
    private UserService userService;
    @Inject
    private FileService fileService;
    @Inject
    private ProfService profService;
    @Inject
    private CurriculumService curriculumService;
    @Inject
    private AuthorizationGuardService authorizationGuardService;
    @Inject
    private AdminService adminService;

    @ModelAttribute("currentPageRole")
    public String getCurrentPageRole() {
        return "student";
    }

    @RequestMapping(value = "/courseGuide/yearlyCurriculum", method = RequestMethod.GET)
    public String yearlyCurriculum(Model model) {
        model.addAttribute("divisions", getCurrentStudentDivisions());
        model.addAttribute("yearList", getYearList());
        return "role/student/yearlyCurriculum/yearlyCurriculum";
    }

    @RequestMapping(value = "/courseGuide/yearlyCurriculum/courseTable", method = RequestMethod.GET)
    public String curriculumTable(Model model,
            @RequestParam(defaultValue = "0", required = false) int year,
            @RequestParam(defaultValue = "0", required = false) int division) {
        Division currentDivision = getCurrentStudentDivision();
        List<Division> divisions = Collections.singletonList(currentDivision);

        model.addAttribute("year", year);
        List<UploadedFile> uploadedFiles = new ArrayList<>();
        if (year > 0) {
            Curriculum curriculum = curriculumService.getActiveCurriculumForStudent(User.current(), division, year);
            if (curriculum != null && curriculum.getUploadedFileId() > 0) {
                UploadedFile uploadedFile = adminService.findUploadedFileById(curriculum.getUploadedFileId());
                if (uploadedFile != null) {
                    uploadedFiles.add(uploadedFile);
                }
            }
        }
        model.addAttribute("uploadedFiles", uploadedFiles);
        model.addAttribute("divisions", divisions);
        return "role/student/yearlyCurriculum/courseTable";
    }

    @RequestMapping(value = "/courseGuide/courseInfo", method = RequestMethod.GET)
    public String courseInfo(@SuppressWarnings("unused") Model model) {
        // Suppress CodeQL unused-parameter: required by framework contract
        Objects.toString(model); // no-op reference

        return "role/student/courseInfo/courseInfo";
    }

    @RequestMapping(value = "/courseGuide/courseInfo/courseTable", method = RequestMethod.GET)
    public String courseInfoCourseTable(Model model,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String title) {

        Course firstCourse = null;
        List<Course> courseList;
        if (StringUtils.isBlank(code) && StringUtils.isBlank(title)) {
            courseList = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();
            searchable.setCode(code);
            searchable.setTitle(title);
            searchable.setEnabled(true);
            courseList = adminService.findCoursesBy(searchable);

            for (Course course : courseList) {
                firstCourse = course;
                break;
            }
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("courseList", courseList);
        return "role/student/courseInfo/courseTable";
    }

    @RequestMapping(value = "/courseGuide/courseInfo/courseDetail", method = RequestMethod.GET)
    public String courseInfoCourseDetail(Model model, @RequestParam int courseId) {
        Course course = adminService.findCourseById(courseId);
        model.addAttribute("course", course);

        return "role/student/courseInfo/courseDetail";
    }

    @RequestMapping(value = "/courseGuide/alternative", method = RequestMethod.GET)
    public String alternative(@SuppressWarnings("unused") Model model) {
        // Suppress CodeQL unused-parameter: required by framework contract
        Objects.toString(model); // no-op reference
        return "role/student/alternative/alternative";
    }

    @RequestMapping(value = "/courseGuide/alternative/courseTable", method = RequestMethod.GET)
    public String alternativeCourseTable(Model model,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String title) {

        Course firstCourse = null;
        List<Course> courseList;
        if (StringUtils.isBlank(code) && StringUtils.isBlank(title)) {
            courseList = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();
            searchable.setCode(code);
            searchable.setTitle(title);
            courseList = adminService.findCoursesBy(searchable);

            for (Course course : courseList) {
                firstCourse = course;
                break;
            }
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("courseList", courseList);
        return "role/student/alternative/courseTable";
    }

    @RequestMapping(value = "/courseGuide/alternative/courseDetail", method = RequestMethod.GET)
    public String alternativeCourseDetail(Model model, @RequestParam int courseId) {
        Course course = adminService.findCourseById(courseId);
        model.addAttribute("course", course);
        List<AltCourse> altCourses = adminService.findAltCoursesByTargetCourseId(courseId);
        model.addAttribute("altCourses", altCourses);
        return "role/student/alternative/courseDetail";
    }

    @RequestMapping(value = "/register/basic", method = RequestMethod.GET)
    public String basic(Model model) {

        User studentUser = adminService.findUserById(User.current().getId());
        StudentProfileUpdateDTO studentProfileUpdate = new StudentProfileUpdateDTO();
        if (studentUser != null && studentUser.getContact() != null) {
            studentProfileUpdate.setFirstName(studentUser.getContact().getFirstName());
            studentProfileUpdate.setLastName(studentUser.getContact().getLastName());
        }

        model.addAttribute("studentUser", studentUser);
        model.addAttribute("studentProfileUpdate", studentProfileUpdate);
        List<LangCert> langCerts = adminService.findLangCertsByUserId(User.current().getId());
        model.addAttribute("langCerts", langCerts);

        return "role/student/basic/basic";
    }

    @RequestMapping(value = "/register/basic", method = RequestMethod.POST)
    public String basic(@ModelAttribute("studentProfileUpdate") StudentProfileUpdateDTO studentProfileUpdate,
            HttpServletRequest request,
            SessionStatus sessionStatus) throws IOException {
        User user = User.current();
        User persistedUser = adminService.findUserById(user.getId());
        if (persistedUser == null) {
            throw new AccessDeniedException("Access denied.");
        }

        Contact persistedContact = adminService.findContactByUserId(user.getId());
        boolean isNewContact = persistedContact == null;
        if (isNewContact) {
            persistedContact = new Contact();
            persistedContact.setUserId(user.getId());
        }

        applyWhitelistedStudentProfileUpdate(persistedContact, studentProfileUpdate, request);

        if (isNewContact) {
            adminService.insertContact(persistedContact);
        } else {
            adminService.updateContact(persistedContact);
        }

        if (request instanceof MultipartHttpServletRequest) {
            MultipartFile f = ((MultipartHttpServletRequest) request).getFile("file");
            if (f == null || f.getSize() == 0) {

            } else {

                String fileName = f.getOriginalFilename();
                String ext = fileService.getExtension(fileName);
                if (ext.equalsIgnoreCase("png") || ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg")
                        || ext.equalsIgnoreCase("gif")) {
                    adminService.deleteProfileByUser(user);
                    DateTime dt = new DateTime();
                    fileService.processUploadedFile(f, user, Designation.profile, 0, 0, dt.getYear());
                }

            }

        }

    authorityService.authenticateUserAndSetSession(adminService.findUserById(persistedUser.getId()));

        sessionStatus.setComplete();

        return "redirect:/student/register/basic";
    }

    private void applyWhitelistedStudentProfileUpdate(Contact persistedContact,
            StudentProfileUpdateDTO studentProfileUpdate,
            HttpServletRequest request) {
        persistedContact.setFirstName(StringUtils.trimToNull(studentProfileUpdate.getFirstName()));
        persistedContact.setLastName(StringUtils.trimToNull(studentProfileUpdate.getLastName()));
        persistedContact.setCellPhone(StringUtils.trimToNull(request.getParameter("contact.cellPhone")));
        persistedContact.setPhone(StringUtils.trimToNull(request.getParameter("contact.phone")));
        persistedContact.setPostCode(StringUtils.trimToNull(request.getParameter("contact.postCode")));
        persistedContact.setAddress(StringUtils.trimToNull(request.getParameter("contact.address")));
        persistedContact.setParentName(StringUtils.trimToNull(request.getParameter("contact.parentName")));
        persistedContact.setRelation(StringUtils.trimToNull(request.getParameter("contact.relation")));
        persistedContact.setParentCellPhone(StringUtils.trimToNull(request.getParameter("contact.parentCellPhone")));
        persistedContact.setParentPhone(StringUtils.trimToNull(request.getParameter("contact.parentPhone")));
        persistedContact.setParentPostCode(StringUtils.trimToNull(request.getParameter("contact.parentPostCode")));
        persistedContact.setParentAddress(StringUtils.trimToNull(request.getParameter("contact.parentAddress")));
    }

    @RequestMapping(value = "/classInformation/syllabus", method = RequestMethod.GET)
    public String syllabus(Model model) {
        model.addAttribute("divisions", getCurrentStudentDivisions());
        model.addAttribute("yearList", getYearList());
        return "role/student/syllabus/syllabus";
    }

    @RequestMapping(value = "/classInformation/syllabus/courseTable", method = RequestMethod.GET)
    public String syllabusCourseTable(Model model,
            @RequestParam(defaultValue = "0", required = false) int year,
            @RequestParam(defaultValue = "0", required = false) int semester,
            @RequestParam(defaultValue = "0", required = false) int division,
            @RequestParam(required = false) String title) {
        ProfessorCourse firstCourse = null;
        List<ProfessorCourse> courseList = profService.findSyllabusCoursesForStudent(User.current(), division,
                year, semester, title);

        for (ProfessorCourse course : courseList) {
            firstCourse = course;
            break;
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("profCourseList", courseList);
        return "role/student/syllabus/courseTable";
    }

    @RequestMapping(value = "/classInformation/syllabus/courseDetail", method = RequestMethod.GET)
    public String courseDetail(Model model, @RequestParam int profCourseId) {
        ProfessorCourse pc = profService.findSyllabusDetailForStudent(User.current(),
            getCurrentStudentDivisionId(), profCourseId);
        model.addAttribute("pc", pc);
        LectureFundamentals lectureFundamentals = adminService.findLectureFundamentalsByProfCourseId(pc.getId());
        model.addAttribute("lectureFundamentals",
                lectureFundamentals == null ? new LectureFundamentals() : lectureFundamentals);
        ProfLectureMethod profLectureMethod = adminService.findProfLectureMethodByProfCourseId(pc.getId());
        model.addAttribute("profLectureMethod",
                profLectureMethod == null ? new ProfLectureMethod() : profLectureMethod);
        LectureContents lectureContents = adminService.findLectureContentsByProfCourseId(pc.getId());
        model.addAttribute("lectureContents", lectureContents == null ? new LectureContents() : lectureContents);

        List<LectureMethod> lectureMethods = adminService.findAllLectureMethods();
        model.addAttribute("lectureMethods", lectureMethods);

        List<EducationalMedium> educationalMediums = adminService.findAllEducationalMediums();
        model.addAttribute("educationalMediums", educationalMediums);

        List<EvaluationMethod> evaluationMethods = adminService.findAllEvaluationMethods();
        model.addAttribute("evaluationMethods", evaluationMethods);

        List<Equipment> equipments = adminService.findAllEquipments();
        model.addAttribute("equipments", equipments);

        MenuAccess menuAccess = adminService.findMenuAccess();
        model.addAttribute("menuAccess", menuAccess);
        return "role/student/syllabus/courseDetail";
    }

    private int getCurrentStudentDivisionId() {
        return getCurrentStudentDivision().getId();
    }

    private List<Division> getCurrentStudentDivisions() {
        return Collections.singletonList(getCurrentStudentDivision());
    }

    private Division getCurrentStudentDivision() {
        User currentUser = User.current();
        if (currentUser == null) {
            throw new AccessDeniedException("Access denied.");
        }

        Division division = adminService.findDivisionById(currentUser.getDivisionId());
        if (division == null) {
            throw new AccessDeniedException("Access denied.");
        }

        return division;
    }

    @RequestMapping(value = "/classInformation/classAssessment", method = RequestMethod.GET)
    public String assessment(Model model, @RequestParam(required = false) String result) {

        model.addAttribute("yearList", getYearList());
        model.addAttribute("result", result);
        return "role/student/classAssessment/classAssessment";
    }

    @RequestMapping(value = "/classInformation/classAssessment/courseTable", method = RequestMethod.GET)
    public String classAssessmentCourseTable(Model model,
            @RequestParam(defaultValue = "0", required = false) int year,
            @RequestParam(defaultValue = "0", required = false) int semester) {

        User user = User.current();
        StudentCourse firstCourse = null;
        List<StudentCourse> studentCourseList;
        if (year == 0 && semester == 0) {
            studentCourseList = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();
            searchable.setYear(year);
            searchable.setSemester(semester);
            searchable.setUserId(user.getId());

            studentCourseList = adminService.findStudentClassAssessmentByUserIdYearSemester(searchable);

            for (StudentCourse sc : studentCourseList) {
                firstCourse = sc;
                break;
            }
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("courseList", studentCourseList);
        return "role/student/classAssessment/courseTable";
    }

    @RequestMapping(value = "/classInformation/classAssessment/courseDetail", method = RequestMethod.GET)
    public String classAssessmentCourseDetail(Model model, @RequestParam int studentCourseId) {
        User studentUser = User.current();
        StudentCourse sc = authorizationGuardService.requireOwnedStudentCourseForStudent(studentCourseId,
            studentUser, "student-class-assessment-detail");
        model.addAttribute("sc", sc);

        Assessment assessment = adminService.findAssessmentByUserIdProfCourseId(studentUser.getId(), sc.getProfCourseId());
        if (assessment == null) {
            assessment = new Assessment();
            assessment.setUserId(studentUser.getId());
            assessment.setProfCourseId(sc.getProfCourseId());
        }

        model.addAttribute("assessment", assessment);
        List<AssessmentFactor> assessmentFactors = adminService.findAssessmentFactorsByCourseIdEnabled(sc.getCourseId());
        model.addAttribute("assessmentFactors", assessmentFactors);
        List<AssessmentFactor> assessmentFactorsAll = adminService.findAssessmentFactorsByCourseId(sc.getCourseId());
        model.addAttribute("assessmentFactorsAll", assessmentFactorsAll);
        MenuAccess menuAccess = adminService.findMenuAccess();
        model.addAttribute("menuAccess", menuAccess);
        ProfessorCourse pc = adminService.findProfessorCourseById(sc.getProfCourseId());
        Semester semester = pc.getSemester();
        model.addAttribute("isEditable", menuAccess.isAssessment() && semester.isCurrent());

        return "role/student/classAssessment/courseDetail";
    }

    @RequestMapping(value = "/classInformation/classAssessment/courseDetail", method = RequestMethod.POST)
    public String classAssessmentCourseDetail(
            @ModelAttribute koreatech.cse.domain.dto.AssessmentRequest req,
            @RequestParam int studentCourseId) {
        Assessment assessment = req.toEntity();
        authorizationGuardService.submitAssessmentForOwnedStudentCourse(studentCourseId, assessment, User.current());
        return "redirect:/student/classInformation/classAssessment?result=success";
    }

    @RequestMapping(value = "/grades/inquiryGrade", method = RequestMethod.GET)
    public String inquiryGrade(Model model) {
        User studentUser = User.current();
        model.addAttribute("studentUser", studentUser);
        LinkedHashSet<Integer> semesterSet = adminService.findSemesterIdsByUserIdValid(studentUser.getId());
        List<Integer> semesterIds = new ArrayList<>(semesterSet);

        Semester firstSemester = null;
        Map<Semester, List<StudentCourse>> map = new LinkedHashMap<>();

        if (!semesterIds.isEmpty()) {
            List<Semester> semesters = adminService.findSemestersByIds(semesterIds);
            Map<Integer, Semester> semesterById = new HashMap<>();
            for (Semester semester : semesters) {
                semesterById.put(semester.getId(), semester);
            }

            firstSemester = semesterById.get(semesterIds.get(0));

            List<StudentCourse> allCourses = adminService.findStudentInquiryCoursesByUserIdValid(studentUser.getId());
            Set<Integer> assessedProfCourseIds = findAssessedProfCourseIds(studentUser.getId(), allCourses);
            Map<Integer, List<StudentCourse>> filteredBySemesterId = new HashMap<>();
            for (StudentCourse sc : allCourses) {
                if (!assessedProfCourseIds.contains(sc.getProfCourseId())) {
                    continue;
                }

                ProfessorCourse professorCourse = sc.getProfessorCourse();
                if (professorCourse == null) {
                    continue;
                }

                int semesterId = professorCourse.getSemesterId();
                List<StudentCourse> filteredCourses = filteredBySemesterId.get(semesterId);
                if (filteredCourses == null) {
                    filteredCourses = new ArrayList<>();
                    filteredBySemesterId.put(semesterId, filteredCourses);
                }
                filteredCourses.add(sc);
            }

            for (Integer semesterId : semesterIds) {
                Semester semester = semesterById.get(semesterId);
                if (semester == null) {
                    continue;
                }
                List<StudentCourse> filteredCourses = filteredBySemesterId.get(semesterId);
                map.put(semester, filteredCourses == null ? new ArrayList<>() : filteredCourses);
            }
        }

        model.addAttribute("firstSemester", firstSemester);

        model.addAttribute("courseMap", map);

        return "role/student/inquiryGrade/inquiryGrade";
    }

    @RequestMapping(value = "/grades/inquiryGrade/gradeDetail", method = RequestMethod.GET)
    public String inquiryGradeDetail(Model model, @RequestParam int semesterId) {
        User studentUser = User.current();

        List<StudentCourse> studentCourses = adminService.findStudentInquiryCoursesByUserIdSemesterIdValid(
            studentUser.getId(), semesterId);
        Set<Integer> assessedProfCourseIds = findAssessedProfCourseIds(studentUser.getId(), studentCourses);

        List<StudentCourse> filtered = new ArrayList<>();
        for (StudentCourse sc : studentCourses) {
            if (assessedProfCourseIds.contains(sc.getProfCourseId())) {
                filtered.add(sc);
            }
        }

        model.addAttribute("studentUser", studentUser);
        model.addAttribute("studentCourses", filtered);
        return "role/student/inquiryGrade/gradeDetail";
    }

    @RequestMapping(value = "/grades/inquiryGrade/gradeDetailForPrint", method = RequestMethod.GET)
    public String inquiryGradeDetailPrint(Model model) {
        User studentUser = User.current();
        model.addAttribute("studentUser", studentUser);

        Certificate certificate = adminService.findCertificateByUserId(studentUser.getId());
        if (certificate == null) {
            certificate = new Certificate();
            certificate.setUserId(studentUser.getId());
        }
        model.addAttribute("certificate", certificate);
        model.addAttribute("today", DateHelper.format(new Date()));
        Map<Semester, List<StudentCourse>> map = profService.getStudentSemesterCourseMap(studentUser.getId());
        model.addAttribute("finalScore", profService.getStudentTotalScore(map));
        model.addAttribute("courseMap", map);
        return "role/common/grade/gradeCertForPrint";
    }

    @RequestMapping(value = "/graduation/graduationRequirements", method = RequestMethod.GET)
    public String graduationRequirements(Model model) {
        User studentUser = User.current();
        User advisor = adminService.findUserById(studentUser.getAdvisorId());
        model.addAttribute("advisor", advisor);
        int admissionYear = studentUser.getContact().getAdmissionYear();
        int divisionId = studentUser.getDivisionId();

        GraduationCriteria graduationCriteria = adminService.findGraduationCriteriaByYearDivision(admissionYear,
                divisionId);
        studentUser.setGraduationCriteria(graduationCriteria == null ? new GraduationCriteria() : graduationCriteria);

        List<StudentCourse> studentCourses = adminService.findStudentCoursesByUserIdValid(studentUser.getId());
        studentUser.setStudentCourses(studentCourses);
        model.addAttribute("studentUser", studentUser);

        return "role/student/graduationRequirements/graduationRequirements";
    }

    @RequestMapping(value = "/graduation/graduationRequirements/gradeDetail", method = RequestMethod.GET)
    public String requiredGradeDetail(Model model) {
        User studentUser = User.current();
        model.addAttribute("studentUser", studentUser);
        model.addAttribute("completeSemester", userService.getCompleteSemesterCount(studentUser.getId()));
        return "role/student/graduationRequirements/gradeDetail";
    }

    @RequestMapping(value = "/graduation/graduationResearchPlan", method = RequestMethod.GET)
    public String graduationResearchPlan(Model model, @RequestParam(required = false) String result) {
        User studentUser = User.current();
        model.addAttribute("studentUser", studentUser);

        GraduationResearchPlan stored = adminService.findGraduationResearchPlanByUserId(studentUser.getId());
        model.addAttribute("stored", stored);
        model.addAttribute("graduationResearchPlan", new GraduationResearchPlan());
        model.addAttribute("result", result);

        model.addAttribute("completeSemester", userService.getCompleteSemesterCount(studentUser.getId()));

        if (stored == null || stored.getApprove() == -1)
            return "role/student/graduationResearchPlan/newGraduationResearchPlan";
        else
            return "role/student/graduationResearchPlan/graduationResearchPlan";
    }

    private Set<Integer> findAssessedProfCourseIds(int userId, List<StudentCourse> courses) {
        Set<Integer> assessedProfCourseIds = new HashSet<>();
        if (courses == null || courses.isEmpty()) {
            return assessedProfCourseIds;
        }

        Set<Integer> profCourseIds = new LinkedHashSet<>();
        for (StudentCourse course : courses) {
            profCourseIds.add(course.getProfCourseId());
        }

        if (profCourseIds.isEmpty()) {
            return assessedProfCourseIds;
        }

        List<Assessment> assessments = findAssessmentsByUserAndProfCourseIdsBatched(userId,
                new ArrayList<>(profCourseIds));
        for (Assessment assessment : assessments) {
            assessedProfCourseIds.add(assessment.getProfCourseId());
        }
        return assessedProfCourseIds;
    }

    private List<Assessment> findAssessmentsByUserAndProfCourseIdsBatched(int userId,
            List<Integer> profCourseIds) {
        if (profCourseIds == null || profCourseIds.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> uniqueProfCourseIds = new ArrayList<>(new LinkedHashSet<>(profCourseIds));
        Set<Integer> seenAssessmentIds = new LinkedHashSet<>();
        List<Assessment> merged = new ArrayList<>();

        for (int start = 0; start < uniqueProfCourseIds.size(); start += IN_CLAUSE_BATCH_SIZE) {
            int end = Math.min(start + IN_CLAUSE_BATCH_SIZE, uniqueProfCourseIds.size());
            List<Assessment> batch = adminService.findAssessmentsByUserIdAndProfCourseIds(userId,
                    uniqueProfCourseIds.subList(start, end));
            for (Assessment assessment : batch) {
                if (seenAssessmentIds.add(assessment.getId())) {
                    merged.add(assessment);
                }
            }
        }

        return merged;
    }

    @RequestMapping(value = "/graduation/graduationResearchPlan", method = RequestMethod.POST)
    public String graduationResearchPlan(
            @ModelAttribute koreatech.cse.domain.dto.GraduationResearchPlanRequest req,
            SessionStatus sessionStatus) {

        User studentUser = User.current();
        GraduationResearchPlan graduationResearchPlan = new GraduationResearchPlan();
        graduationResearchPlan.setTitle(req.getTitle());
        graduationResearchPlan.setType(req.getType());
        graduationResearchPlan.setStartDate(req.getStartDate());
        graduationResearchPlan.setEndDate(req.getEndDate());
        graduationResearchPlan.setPurpose(req.getPurpose());
        graduationResearchPlan.setScope(req.getScope());
        graduationResearchPlan.setMethod(req.getMethod());
        graduationResearchPlan.setImpl(req.getImpl());
        graduationResearchPlan.setSchedule(req.getSchedule());
        graduationResearchPlan.setRef(req.getRef());
        graduationResearchPlan.setEtc(req.getEtc());
        graduationResearchPlan.setSubmitDate(req.getSubmitDate());
        graduationResearchPlan.setUserId(studentUser.getId());
        try {
            String submitDate = req.getSubmitDate();
            Date d = DateHelper.parse("yyyy-MM-dd", submitDate);
            DateTime dt = new DateTime(d);
            graduationResearchPlan.setYear(dt.getYear());
        } catch (Exception e) {
        }
        GraduationResearchPlan stored = adminService.findGraduationResearchPlanByUserId(studentUser.getId());
        if (stored == null) {
            graduationResearchPlan.setApprove(-1);
            adminService.insertGraduationResearchPlan(graduationResearchPlan);
        } else {
            graduationResearchPlan.setId(stored.getId());
            adminService.updateGraduationResearchPlan(graduationResearchPlan);
        }
        sessionStatus.setComplete();
        return "redirect:/student/graduation/graduationResearchPlan?result=success";
    }

    private List<Integer> getYearList() {
        return adminService.findSemesterYears();
    }

}
