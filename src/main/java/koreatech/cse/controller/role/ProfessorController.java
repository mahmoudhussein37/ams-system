package koreatech.cse.controller.role;

import koreatech.cse.domain.Searchable;
import koreatech.cse.domain.UploadedFile;
import koreatech.cse.domain.User;
import koreatech.cse.domain.constant.Designation;
import koreatech.cse.domain.constant.SubjCategory;
import koreatech.cse.domain.dto.GradeUpdateDTO;
import koreatech.cse.domain.role.professor.*;
import koreatech.cse.domain.role.student.GraduationResearchPlan;
import koreatech.cse.domain.role.student.StudentCourse;
import koreatech.cse.domain.univ.*;
import koreatech.cse.service.AdminService;
import koreatech.cse.service.AuthorizationGuardService;
import koreatech.cse.service.FileService;
import koreatech.cse.service.ProfService;
import koreatech.cse.service.UserService;
import koreatech.cse.view.StudentListExcelView;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.http.ResponseEntity;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Objects;

@Controller
@SessionAttributes({})
@PreAuthorize("hasRole('ROLE_PROFESSOR')")
@RequestMapping("/professor")
public class ProfessorController {
    private static final Logger logger = LoggerFactory.getLogger(ProfessorController.class);
    private static final int IN_CLAUSE_BATCH_SIZE = 150;

    @Inject
    private UserService userService;
    @Inject
    private FileService fileService;
    @Inject
    private MessageSource messageSource;
    @Inject
    private ProfService profService;
    @Inject
    private AuthorizationGuardService authorizationGuardService;
    @Inject
    private AdminService adminService;

    @ModelAttribute("currentPageRole")
    public String getCurrentPageRole() {
        return "professor";
    }

    @RequestMapping(value = "/studentGuidance/studentLookup", method = RequestMethod.GET)
    public String studentLookup(Model model) {

        List<Division> divisions = adminService.findAllDivisions();

        model.addAttribute("divisions", divisions);
        return "role/professor/studentLookup/studentLookup";
    }

    @RequestMapping(value = "/studentGuidance/studentLookup/studentTable", method = RequestMethod.GET)
    public String studentTable(Model model, @RequestParam(required = false) String number,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0", required = false) int division) {
        User firstUser = null;
        List<User> userList;
        if (StringUtils.isBlank(number) && StringUtils.isBlank(name) && division == 0) {
            userList = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();
            searchable.setNumber(number);
            searchable.setName(name);
            searchable.setDivision(division);
            searchable.setOrderParam("number");
            searchable.setOrderDir("asc");
            userList = adminService.findStudentBy(searchable);

            for (User user : userList) {
                firstUser = user;
                break;
            }
        }

        model.addAttribute("userList", userList);
        model.addAttribute("firstUser", firstUser);
        return "role/professor/studentLookup/studentTable";
    }

    @RequestMapping(value = "/studentGuidance/studentLookup/studentDetail", method = RequestMethod.GET)
    public String studentDetail(Model model, @RequestParam int studentId,
            @RequestParam(defaultValue = "false", required = false) String print) {
        User studentUser = authorizationGuardService.requireAdvisedStudent(studentId, User.current(),
            "student-lookup-detail");

        User advisor = adminService.findUserById(studentUser.getAdvisorId());
        model.addAttribute("advisor", advisor);
        int admissionYear = studentUser.getContact().getAdmissionYear();
        int divisionId = studentUser.getDivisionId();

        GraduationCriteria graduationCriteria = adminService.findGraduationCriteriaByYearDivision(admissionYear,
                divisionId);
        studentUser.setGraduationCriteria(graduationCriteria == null ? new GraduationCriteria() : graduationCriteria);

        List<StudentCourse> studentCourses = adminService.findStudentCoursesByUserIdValid(studentId);
        studentUser.setStudentCourses(studentCourses);

        model.addAttribute("studentUser", studentUser);

        List<LangCert> langCerts = adminService.findLangCertsByUserId(studentId);
        model.addAttribute("langCerts", langCerts);
        if (print.equals("true"))
            return "role/professor/studentLookup/studentDetailForPrint";
        return "role/professor/studentLookup/studentDetail";
    }

    @RequestMapping(value = "/studentGuidance/counseling", method = RequestMethod.GET)
    public String counseling(Model model, @RequestParam(required = false) String result) {
        model.addAttribute("yearList", getYearList());
        model.addAttribute("result", result);
        return "role/professor/counseling/counseling";
    }

    @RequestMapping(value = "/studentGuidance/counseling/newCounseling", method = RequestMethod.GET)
    public String newCounseling(Model model) {
        List<Division> divisions = adminService.findAllDivisions();

        model.addAttribute("divisions", divisions);
        return "role/professor/counseling/newCounseling";
    }

    @RequestMapping(value = "/studentGuidance/counseling/newCounselingDetail", method = RequestMethod.GET)
    public String newCounselingDetail(Model model, @RequestParam int studentId) {
        User studentUser = authorizationGuardService.requireAdvisedStudent(studentId, User.current(),
            "counseling-new-detail");
        model.addAttribute("yearList", getYearList());
        Counseling counseling = new Counseling();
        counseling.setStudentUser(studentUser);
        counseling.setStudentUserId(studentId);
        model.addAttribute("counseling", counseling);
        model.addAttribute("studentId", studentId);
        DateTime dt = new DateTime();
        model.addAttribute("currentYear", dt.getYear());
        return "role/professor/counseling/newCounselingDetail";
    }

    @RequestMapping(value = "/studentGuidance/counseling/newCounselingDetail", method = RequestMethod.POST)
    public String newCounselingDetail(@ModelAttribute koreatech.cse.domain.dto.CounselingCreateRequest req,
            @RequestParam @SuppressWarnings("unused") int studentId) {
        Objects.toString(studentId);
        authorizationGuardService.requireAdvisedStudent(req.getStudentUserId(), User.current(),
            "counseling-new-submit");
        Counseling counseling = new Counseling();
        counseling.setStudentUserId(req.getStudentUserId());
        counseling.setYear(req.getYear());
        counseling.setPlace(req.getPlace());
        counseling.setDate(req.getDate());
        counseling.setContents(req.getContents());
        counseling.setSuggestions(req.getSuggestions());
        counseling.setNumber(UUID.randomUUID().toString().replace("-", ""));
        User user = User.current();
        counseling.setProfUserId(user.getId());
        adminService.insertCounseling(counseling);
        return "redirect:/professor/studentGuidance/counseling?result=success";
    }

    @RequestMapping(value = "/studentGuidance/counseling/studentTable", method = RequestMethod.GET)
    public String counselingStudentTable(Model model, @RequestParam(required = false) String number,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0", required = false) int division) {
        User firstUser = null;
        List<User> userList;
        if (StringUtils.isBlank(number) && StringUtils.isBlank(name) && division == 0) {
            userList = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();
            searchable.setNumber(number);
            searchable.setName(name);
            searchable.setDivision(division);
            userList = adminService.findStudentBy(searchable);

            for (User user : userList) {
                firstUser = user;
                break;
            }
        }

        model.addAttribute("userList", userList);
        model.addAttribute("firstUser", firstUser);
        return "role/professor/counseling/studentTable";
    }

    @RequestMapping(value = "/studentGuidance/counseling/counselingTable", method = RequestMethod.GET)
    public String counselingStudentTable(Model model, @RequestParam(required = false, defaultValue = "0") int year,
            @RequestParam(required = false) String name) {
        User currentUser = User.current();
        Counseling firstCounseling = null;
        List<Counseling> counselingList;
        if (year == 0 && StringUtils.isBlank(name)) {
            counselingList = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();
            searchable.setYear(year);
            searchable.setName(name);
            searchable.setAdvisor(currentUser.getId());
            counselingList = adminService.findCounselingBy(searchable);

            for (Counseling counseling : counselingList) {
                firstCounseling = counseling;
                break;
            }
        }

        model.addAttribute("counselingList", counselingList);
        model.addAttribute("firstCounseling", firstCounseling);
        return "role/professor/counseling/counselingTable";
    }

    @RequestMapping(value = "/studentGuidance/counseling/counselingDetail", method = RequestMethod.GET)
    public String counselingStudentDetail(Model model, @RequestParam int counselingId,
            @RequestParam(defaultValue = "false", required = false) String print) {
        Counseling counseling = authorizationGuardService.requireOwnedCounseling(counselingId, User.current(),
            "counseling-detail");
        model.addAttribute("counseling", counseling);

        User studentUser = counseling.getStudentUser();

        int studentId = studentUser.getId();
        User advisor = adminService.findUserById(studentUser.getAdvisorId());
        model.addAttribute("advisor", advisor);
        int admissionYear = studentUser.getContact().getAdmissionYear();
        int divisionId = studentUser.getDivisionId();

        GraduationCriteria graduationCriteria = adminService.findGraduationCriteriaByYearDivision(admissionYear,
                divisionId);
        studentUser.setGraduationCriteria(graduationCriteria == null ? new GraduationCriteria() : graduationCriteria);
        List<StudentCourse> studentCourses = adminService.findStudentCoursesByUserIdValid(studentId);
        studentUser.setStudentCourses(studentCourses);
        model.addAttribute("studentUser", studentUser);

        if (print.equals("true"))
            return "role/professor/counseling/counselingDetailForPrint";
        return "role/professor/counseling/counselingDetail";
    }

    @RequestMapping(value = "/classProgress/attendance", method = RequestMethod.GET)
    public String attendance(Model model) {

        List<Division> divisions = adminService.findAllDivisions();

        model.addAttribute("divisions", divisions);
        model.addAttribute("yearList", getYearList());
        model.addAttribute("course", new Course());
        model.addAttribute("subjCategoryList", SubjCategory.values());

        return "role/professor/attendance/attendance";
    }

    @RequestMapping(value = "/classProgress/attendance/studentListExcel", method = RequestMethod.GET)
    public ModelAndView institutionExcel(HttpServletResponse response, @RequestParam int profCourseId, Locale locale) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("Egypt"));
        String todayDateString = sdf.format(new Date());
        Map<String, Object> objectMap = new HashMap<>();
        ProfessorCourse professorCourse = authorizationGuardService.requireOwnedProfessorCourse(profCourseId,
            User.current(), "attendance-student-list-excel");
        List<StudentCourse> studentCourses = adminService.findStudentCoursesByProfCourseId(professorCourse.getId());

        objectMap.put("studentCourses", studentCourses);
        objectMap.put("messageSource", messageSource);
        objectMap.put("locale", locale);
        response.setContentType("application/ms-excel");
        String fileName = "attendance_" + todayDateString;
        response.setHeader("Content-disposition", String.format("attachment; filename=%s.xls", fileName));
        return new ModelAndView(new StudentListExcelView(), objectMap);
    }

    @RequestMapping(value = "/classProgress/attendance/courseTable", method = RequestMethod.GET)
    public String professorCourseTable(Model model,
            @RequestParam(defaultValue = "0", required = false) int year,
            @RequestParam(defaultValue = "0", required = false) int semester) {
        ProfessorCourse firstCourse = null;
        List<ProfessorCourse> courseList;

        if (year == 0 && semester == 0) {
            courseList = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();
            searchable.setYear(year);
            searchable.setSemester(semester);
            searchable.setAdvisor(User.current().getId());

            courseList = adminService.findProfessorCoursesBy(searchable);

            for (ProfessorCourse course : courseList) {
                firstCourse = course;
                break;
            }
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("profCourseList", courseList);
        return "role/professor/attendance/courseTable";
    }

    @RequestMapping(value = "/classProgress/attendance/courseDetail", method = RequestMethod.GET)
    public String attendanceCourseDetail(Model model, @RequestParam int profCourseId) {
        ProfessorCourse pc = authorizationGuardService.requireOwnedProfessorCourse(profCourseId, User.current(),
            "attendance-course-detail");
        model.addAttribute("pc", pc);
        model.addAttribute("uploadedFile", new UploadedFile());
        Semester semester = pc.getSemester();
        model.addAttribute("isEditable", semester.isCurrent());
        return "role/professor/attendance/courseDetail";
    }

    @RequestMapping(value = "/classProgress/attendance/courseDetail", method = RequestMethod.POST)
    public String attendanceCourseDetail(@RequestParam(required = false) MultipartFile attendanceFile,
            @RequestParam int profCourseId) {
        ProfessorCourse pc = authorizationGuardService.requireOwnedProfessorCourse(profCourseId, User.current(),
            "attendance-course-upload");
        User user = User.current();
        Semester semester = pc.getSemester();

        List<UploadedFile> uploadedFiles = adminService.findUploadedFilesByDesignationProfCourseId(Designation.attendance,
                profCourseId);

        for (UploadedFile stored : uploadedFiles) {
            adminService.deleteUploadedFile(stored);
        }
        if (attendanceFile != null) {
            try {
                fileService.processUploadedFile(attendanceFile, user, Designation.attendance,
                        pc.getCourse().getDivisionId(), profCourseId, semester.getYear());
            } catch (Exception e) {
                logger.error("Failed to process attendance file upload for profCourseId={}", profCourseId, e);
            }
        }
        return "redirect:/professor/classProgress/attendance";
    }

    @RequestMapping(value = "/classProgress/inquiryCourse", method = RequestMethod.GET)
    public String inquiryCourse(Model model) {

        model.addAttribute("yearList", getYearList());
        return "role/professor/inquiryCourse/inquiryCourse";
    }

    @RequestMapping(value = "/classProgress/inquiryCourse/courseTable", method = RequestMethod.GET)
    public String inquiryCourseTable(Model model,
            @RequestParam(defaultValue = "0", required = false) int year,
            @RequestParam(defaultValue = "0", required = false) int semester) {
        ProfessorCourse firstCourse = null;

        List<ProfessorCourse> courseList;
        if (semester == 0 && year == 0) {
            courseList = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();
            searchable.setYear(year);
            searchable.setSemester(semester);
            searchable.setAdvisor(User.current().getId());
            courseList = adminService.findProfessorCoursesBy(searchable);

            for (ProfessorCourse course : courseList) {
                firstCourse = course;
                break;
            }
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("profCourseList", courseList);
        return "role/professor/inquiryCourse/courseTable";
    }

    @RequestMapping(value = "/classProgress/inquiryCourse/courseDetail", method = RequestMethod.GET)
    public String inCourseDetail(Model model, @RequestParam int profCourseId,
            @RequestParam(defaultValue = "false", required = false) String print) {
        ProfessorCourse pc = authorizationGuardService.requireOwnedProfessorCourse(profCourseId, User.current(),
            "inquiry-course-detail");
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
        if (print.equals("true"))
            return "role/common/syllabus/courseDetailForPrint";
        return "role/professor/inquiryCourse/courseDetail";
    }

    @RequestMapping(value = "/classProgress/syllabus", method = RequestMethod.GET)
    public String syllabus(Model model, @RequestParam(required = false) String result) {

        model.addAttribute("yearList", getYearList());
        model.addAttribute("result", result);
        return "role/professor/syllabus/syllabus";
    }

    @RequestMapping(value = "/classProgress/syllabus/courseDetail", method = RequestMethod.GET)
    public String courseDetail(Model model, @RequestParam int profCourseId,
            @RequestParam(defaultValue = "false", required = false) String print) {
        ProfessorCourse pc = authorizationGuardService.requireOwnedProfessorCourse(profCourseId, User.current(),
            "syllabus-course-detail");

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
        List<LectureMethod> lectureMethodsEnabled = adminService.findAllEnabledLectureMethods();
        model.addAttribute("lectureMethodsEnabled", lectureMethodsEnabled);

        List<EducationalMedium> educationalMediums = adminService.findAllEducationalMediums();
        model.addAttribute("educationalMediums", educationalMediums);
        List<EducationalMedium> educationalMediumsEnabled = adminService.findAllEnabledEducationalMediums();
        model.addAttribute("educationalMediumsEnabled", educationalMediumsEnabled);

        List<EvaluationMethod> evaluationMethods = adminService.findAllEvaluationMethods();
        model.addAttribute("evaluationMethods", evaluationMethods);
        List<EvaluationMethod> evaluationMethodsEnabled = adminService.findAllEnabledEvaluationMethods();
        model.addAttribute("evaluationMethodsEnabled", evaluationMethodsEnabled);

        List<Equipment> equipments = adminService.findAllEquipments();
        model.addAttribute("equipments", equipments);
        List<Equipment> equipmentsEnabled = adminService.findAllEnabledEquipments();
        model.addAttribute("equipmentsEnabled", equipmentsEnabled);

        MenuAccess menuAccess = adminService.findMenuAccess();
        model.addAttribute("menuAccess", menuAccess);
        Semester semester = pc.getSemester();

        model.addAttribute("isEditable", menuAccess.isSyllabus() && semester.isCurrent());
        if (print.equals("true"))
            return "role/common/syllabus/courseDetailForPrint";
        return "role/professor/syllabus/courseDetail";
    }

    @RequestMapping(value = "/classProgress/syllabus/courseDetail/lectureFundamentals", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> lectureFundamentals(@RequestParam int profCourseId,
            @ModelAttribute koreatech.cse.domain.dto.LectureFundamentalsRequest req) {

        Map<String, Object> response = new HashMap<>();

        try {
            User user = User.current();
            authorizationGuardService.requireOwnedProfessorCourse(profCourseId, user,
                    "syllabus-lecture-fundamentals-save");
            LectureFundamentals lectureFundamentals = req.toEntity();
            lectureFundamentals.setUserId(user.getId());
            lectureFundamentals.setProfCourseId(profCourseId);

            // Server-side grade rate validation
            int total = lectureFundamentals.getRateAssignment() + lectureFundamentals.getRateMid()
                    + lectureFundamentals.getRateFinal() + lectureFundamentals.getRateOptional();
            if (total != 100) {
                response.put("success", false);
                response.put("message", messageSource.getMessage("professor.gradeRates.validation.error",
                        new Object[] { total }, org.springframework.context.i18n.LocaleContextHolder.getLocale()));
                return ResponseEntity.ok(response);
            }

            String[] ref1Checkbox = lectureFundamentals.getRef1Checkbox();
            String ref1CheckboxStr = checkboxToStr(ref1Checkbox);
            lectureFundamentals.setReflect1(ref1CheckboxStr);

            String[] ref2Checkbox = lectureFundamentals.getRef2Checkbox();
            String ref2CheckboxStr = checkboxToStr(ref2Checkbox);
            lectureFundamentals.setReflect2(ref2CheckboxStr);

            String[] ref3Checkbox = lectureFundamentals.getRef3Checkbox();
            String ref3CheckboxStr = checkboxToStr(ref3Checkbox);
            lectureFundamentals.setReflect3(ref3CheckboxStr);

            String[] ref4Checkbox = lectureFundamentals.getRef4Checkbox();
            String ref4CheckboxStr = checkboxToStr(ref4Checkbox);
            lectureFundamentals.setReflect4(ref4CheckboxStr);

            String[] ref5Checkbox = lectureFundamentals.getRef5Checkbox();
            String ref5CheckboxStr = checkboxToStr(ref5Checkbox);
            lectureFundamentals.setReflect5(ref5CheckboxStr);

            String[] ref6Checkbox = lectureFundamentals.getRef6Checkbox();
            String ref6CheckboxStr = checkboxToStr(ref6Checkbox);
            lectureFundamentals.setReflect6(ref6CheckboxStr);

            if (lectureFundamentals.getId() == 0) {
                // Check for existing record to prevent duplicates
                LectureFundamentals existing = adminService.findLectureFundamentalsByProfCourseId(profCourseId);
                if (existing != null) {
                    // Update existing instead of insert
                    lectureFundamentals.setId(existing.getId());
                    adminService.updateLectureFundamentals(lectureFundamentals);
                } else {
                    adminService.insertLectureFundamentals(lectureFundamentals);
                }
            } else {
                authorizationGuardService.requireOwnedLectureFundamentals(lectureFundamentals.getId(),
                        profCourseId, user, "syllabus-lecture-fundamentals-update");
                adminService.updateLectureFundamentals(lectureFundamentals);
            }

            response.put("success", true);
            response.put("message", "Saved successfully");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Failed to save lecture fundamentals for profCourseId={}", profCourseId, e);
            response.put("success", false);
            response.put("message", "An error occurred while saving: " + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    @RequestMapping(value = "/classProgress/syllabus/courseDetail/profLectureMethod", method = RequestMethod.POST)
    @ResponseBody
    public String profLectureMethod(@RequestParam int profCourseId,
            @ModelAttribute koreatech.cse.domain.dto.ProfLectureMethodRequest req) {
        User user = User.current();
        authorizationGuardService.requireOwnedProfessorCourse(profCourseId, user,
            "syllabus-prof-lecture-method-save");
        ProfLectureMethod profLectureMethod = req.toEntity();
        profLectureMethod.setUserId(user.getId());
        profLectureMethod.setProfCourseId(profCourseId);
        String[] lectureMethodCheckbox = profLectureMethod.getLectureMethodCheckbox();
        String lectureMethodCheckboxStr = checkboxToStr(lectureMethodCheckbox);
        profLectureMethod.setLectureMethods(lectureMethodCheckboxStr);

        if (!profLectureMethod.hasValue(profLectureMethod.getLectureMethods(), 100)) {
            profLectureMethod.setLectureMethodOther(null);
        }

        String[] evaluationMethodCheckbox = profLectureMethod.getEvaluationMethodCheckbox();
        String evaluationMethodCheckboxStr = checkboxToStr(evaluationMethodCheckbox);
        profLectureMethod.setEvaluationMethods(evaluationMethodCheckboxStr);

        if (!profLectureMethod.hasValue(profLectureMethod.getEvaluationMethods(), 100)) {
            profLectureMethod.setEvaluationMethodOther(null);
        }

        String[] educationalMediumCheckbox = profLectureMethod.getEducationalMediumCheckbox();
        String educationalMediumCheckboxStr = checkboxToStr(educationalMediumCheckbox);
        profLectureMethod.setEducationalMediums(educationalMediumCheckboxStr);

        if (!profLectureMethod.hasValue(profLectureMethod.getEducationalMediums(), 100)) {
            profLectureMethod.setEducationalMediumOther(null);
        }

        String[] equipmentCheckbox = profLectureMethod.getEquipmentCheckbox();
        String equipmentCheckboxStr = checkboxToStr(equipmentCheckbox);
        profLectureMethod.setEquipments(equipmentCheckboxStr);

        if (!profLectureMethod.hasValue(profLectureMethod.getEquipments(), 100)) {
            profLectureMethod.setEquipmentOther(null);
        }

        if (profLectureMethod.getId() == 0) {
            adminService.insertProfLectureMethod(profLectureMethod);
        } else {
            authorizationGuardService.requireOwnedProfLectureMethod(profLectureMethod.getId(),
                    profCourseId, user, "syllabus-prof-lecture-method-update");
            adminService.updateProfLectureMethod(profLectureMethod);
        }
        return "success";
    }

    @RequestMapping(value = "/classProgress/syllabus/courseDetail/lectureContents", method = RequestMethod.POST)
    @ResponseBody
    public String lectureContents(@RequestParam int profCourseId,
            @ModelAttribute koreatech.cse.domain.dto.LectureContentsRequest req) {
        User user = User.current();
        authorizationGuardService.requireOwnedProfessorCourse(profCourseId, user,
            "syllabus-lecture-contents-save");
        LectureContents lectureContents = req.toEntity();
        lectureContents.setUserId(user.getId());
        lectureContents.setProfCourseId(profCourseId);
        if (lectureContents.getId() == 0) {
            adminService.insertLectureContents(lectureContents);
        } else {
            authorizationGuardService.requireOwnedLectureContents(lectureContents.getId(),
                    profCourseId, user, "syllabus-lecture-contents-update");
            adminService.updateLectureContents(lectureContents);
        }
        return "success";
    }

    @RequestMapping(value = "/classProgress/syllabus/courseTable", method = RequestMethod.GET)
    public String syllabusCourseTable(Model model,
            @RequestParam(defaultValue = "0", required = false) int year,
            @RequestParam(defaultValue = "0", required = false) int semester) {

        ProfessorCourse firstCourse = null;

        List<ProfessorCourse> courseList;
        if (year == 0 && semester == 0) {
            courseList = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();
            searchable.setYear(year);
            searchable.setSemester(semester);
            searchable.setAdvisor(User.current().getId());

            courseList = adminService.findProfessorCoursesBy(searchable);

            for (ProfessorCourse course : courseList) {
                firstCourse = course;
                break;
            }
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("courseList", courseList);
        return "role/professor/syllabus/courseTable";
    }

    @RequestMapping(value = "/classProgress/classAssessment", method = RequestMethod.GET)
    public String classAssessment(Model model) {

        model.addAttribute("yearList", getYearList());

        return "role/professor/classAssessment/classAssessment";
    }

    @RequestMapping(value = "/classProgress/classAssessment/courseTable", method = RequestMethod.GET)
    public String classAssessmentCourseTable(Model model,
            @RequestParam(defaultValue = "0", required = false) int year,
            @RequestParam(defaultValue = "0", required = false) int semester) {

        ProfessorCourse firstCourse = null;
        List<ProfessorCourse> courseList;
        if (year == 0 && semester == 0) {
            courseList = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();
            searchable.setYear(year);
            searchable.setSemester(semester);
            searchable.setAdvisor(User.current().getId());

            courseList = adminService.findProfessorCoursesBy(searchable);

            for (ProfessorCourse course : courseList) {
                firstCourse = course;
                break;
            }
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("courseList", courseList);
        return "role/professor/classAssessment/courseTable";
    }

    @RequestMapping(value = "/classProgress/classAssessment/courseDetail", method = RequestMethod.GET)
    public String classAssessmentCourseDetail(Model model, @RequestParam int profCourseId,
            @RequestParam(defaultValue = "false", required = false) String print) {
        ProfessorCourse pc = authorizationGuardService.requireOwnedProfessorCourse(profCourseId, User.current(),
            "class-assessment-course-detail");
        model.addAttribute("pc", pc);
        List<Assessment> assessments = adminService.findAssessmentsByProfCourseId(profCourseId);
        model.addAttribute("assessments", assessments);
        List<AssessmentFactor> assessmentFactors = adminService.findAssessmentFactorsByCourseId(pc.getCourseId());
        model.addAttribute("assessmentFactors", assessmentFactors);
        MenuAccess menuAccess = adminService.findMenuAccess();
        model.addAttribute("menuAccess", menuAccess);
        Semester semester = pc.getSemester();
        model.addAttribute("isViewable", !semester.isCurrent() || !menuAccess.isAssessment());
        if (print.equals("true"))
            return "role/common/assessment/courseDetailForPrint";

        return "role/professor/classAssessment/courseDetail";
    }

    @RequestMapping(value = "/classProgress/registerGrade", method = RequestMethod.GET)
    public String registerGrade(Model model) {

        model.addAttribute("yearList", getYearList());

        return "role/professor/registerGrade/registerGrade";
    }

    @RequestMapping(value = "/classProgress/registerGrade/gradeEditable", method = RequestMethod.POST)
    @ResponseBody
    public String gradeEditable(@RequestParam int pk, @RequestParam String name, @RequestParam String value) {
        GradeUpdateDTO gradeUpdate = new GradeUpdateDTO();

        try {
            if ("scoreAssignment".equals(name) || "scoreMid".equals(name)
                    || "scoreFinal".equals(name) || "scoreOptions".equals(name)) {
                gradeUpdate.setScore(Integer.parseInt(value));
                gradeUpdate.setGrade(name);
            } else if ("grade".equals(name)) {
                gradeUpdate.setGrade(value);
            } else if ("attendance".equals(name)) {
                gradeUpdate.setAttendance(Integer.parseInt(value));
            } else {
                throw new IllegalArgumentException("Unsupported editable field.");
            }
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid numeric value.", ex);
        }

        authorizationGuardService.updateGrade(pk, gradeUpdate, User.current());

        StudentCourse sc = authorizationGuardService.requireOwnedStudentCourseForProfessor(pk, User.current(),
                "register-grade/read-after-update");
        String result = Integer.toString(pk);

        int total = authorizationGuardService.calculateScoreTotal(sc);
        String grade = authorizationGuardService.calculateSuggestedGrade(sc);

        return result + "_" + total + "_" + grade;
    }

    @RequestMapping(value = "/classProgress/registerGrade/courseTable", method = RequestMethod.GET)
    public String registerGradeCourseTable(Model model,
            @RequestParam(defaultValue = "0", required = false) int year,
            @RequestParam(defaultValue = "0", required = false) int semester) {

        List<ProfessorCourse> courseList;
        ProfessorCourse firstCourse = null;
        if (year == 0 && semester == 0) {
            courseList = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();
            searchable.setYear(year);
            searchable.setSemester(semester);
            searchable.setAdvisor(User.current().getId());

            courseList = adminService.findProfessorCoursesBy(searchable);
            for (ProfessorCourse course : courseList) {
                firstCourse = course;
                break;
            }
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("courseList", courseList);
        return "role/professor/registerGrade/courseTable";
    }

    @RequestMapping(value = "/classProgress/registerGrade/courseDetail", method = RequestMethod.GET)
    public String registerGradeCourseDetail(Model model, @RequestParam int profCourseId) {
        ProfessorCourse pc = authorizationGuardService.requireOwnedProfessorCourse(profCourseId, User.current(),
            "register-grade-course-detail");
        model.addAttribute("pc", pc);

        LectureFundamentals lectureFundamentals = adminService.findLectureFundamentalsByProfCourseId(profCourseId);
        if (lectureFundamentals == null)
            return "role/common/syllabus/requiredSyllabus";

        model.addAttribute("lectureFundamentals", lectureFundamentals);

        List<StudentCourse> studentCourses = adminService.findStudentCoursesByProfCourseId(pc.getId());
        model.addAttribute("studentCourses", studentCourses);
        MenuAccess menuAccess = adminService.findMenuAccess();
        Semester semester = pc.getSemester();
        model.addAttribute("menuAccess", menuAccess);
        model.addAttribute("isEditable", menuAccess.isGrade() && semester.isCurrent());

        return "role/professor/registerGrade/courseDetail";
    }

    @RequestMapping(value = "/classProgress/registerGrade/courseDetailForPrint", method = RequestMethod.GET)
    public String registerGradeCourseDetailForPrint(Model model, @RequestParam int profCourseId) {
        ProfessorCourse pc = authorizationGuardService.requireOwnedProfessorCourse(profCourseId, User.current(),
            "register-grade-course-print");
        model.addAttribute("pc", pc);

        LectureFundamentals lectureFundamentals = adminService.findLectureFundamentalsByProfCourseId(profCourseId);
        if (lectureFundamentals == null) {
            return "redirect:/professor/classProgress/syllabus";
        }
        model.addAttribute("lectureFundamentals", lectureFundamentals);

        List<StudentCourse> studentCourses = adminService.findStudentCoursesByProfCourseId(pc.getId());
        model.addAttribute("studentCourses", studentCourses);

        return "role/common/grade/courseDetailForPrint";
    }

    @RequestMapping(value = "/classProgress/registerGrade/courseDetail", method = RequestMethod.POST)
    @ResponseBody
    public Boolean registerGradeCourseDetail(@RequestParam int profCourseId) {
        ProfessorCourse pc = authorizationGuardService.requireOwnedProfessorCourse(profCourseId, User.current(),
            "register-grade-course-save");
        LectureFundamentals lectureFundamentals = adminService.findLectureFundamentalsByProfCourseId(profCourseId);
        if (lectureFundamentals == null) {
            return false;
        }
        List<StudentCourse> studentCourses = adminService.findStudentCoursesByProfCourseId(pc.getId());

        boolean valid = true;
        for (StudentCourse sc : studentCourses) {
            boolean studentValid = true;

            if (sc.getScoreAssignment() > lectureFundamentals.getRateAssignment())
                studentValid = false;
            if (sc.getScoreMid() > lectureFundamentals.getRateMid())
                studentValid = false;
            if (sc.getScoreFinal() > lectureFundamentals.getRateFinal())
                studentValid = false;
            if (sc.getScoreOptions() > lectureFundamentals.getRateOptional())
                studentValid = false;

            int total = sc.getScoreAssignment() + sc.getScoreMid() + sc.getScoreFinal() + sc.getScoreOptions();
            if (total > 100) {
                studentValid = false;
            }
            if (!studentValid)
                valid = false;

            sc.setValid(studentValid);
            User studentUser = sc.getStudentUser();
            sc.setSchoolYear(studentUser.getSchoolYear());
            adminService.updateStudentCourse(sc);
        }

        return valid;
    }

    @RequestMapping(value = "/classProgress/registerGrade/ratioDetail", method = RequestMethod.GET)
    public String registerGradeRatioDetail(Model model, @RequestParam int profCourseId) {
        ProfessorCourse pc = authorizationGuardService.requireOwnedProfessorCourse(profCourseId, User.current(),
            "register-grade-ratio-detail");
        model.addAttribute("pc", pc);

        LectureFundamentals lectureFundamentals = adminService.findLectureFundamentalsByProfCourseId(profCourseId);
        model.addAttribute("lectureFundamentals", lectureFundamentals);

        List<StudentCourse> studentCourses = adminService.findStudentCoursesByProfCourseId(pc.getId());
        model.addAttribute("studentCourses", studentCourses);

        return "role/common/grade/ratioDetail";
    }

    @RequestMapping(value = "/classProgress/cqiReport", method = RequestMethod.GET)
    public String cqiReport(Model model) {

        model.addAttribute("yearList", getYearList());

        return "role/professor/cqiReport/cqiReport";
    }

    @RequestMapping(value = "/classProgress/cqiReport/courseTable", method = RequestMethod.GET)
    public String cqiReportCourseTable(Model model,
            @RequestParam(defaultValue = "0", required = false) int year,
            @RequestParam(defaultValue = "0", required = false) int semester) {

        List<ProfessorCourse> courseList;
        ProfessorCourse firstCourse = null;

        if (year == 0 && semester == 0) {
            courseList = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();
            searchable.setYear(year);
            searchable.setSemester(semester);
            searchable.setAdvisor(User.current().getId());

            courseList = adminService.findProfessorCoursesBy(searchable);

            for (ProfessorCourse course : courseList) {
                firstCourse = course;
                break;
            }
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("courseList", courseList);
        return "role/professor/cqiReport/courseTable";
    }

    @RequestMapping(value = "/classProgress/cqiReport/courseDetail", method = RequestMethod.GET)
    public String cqiReportCourseDetail(Model model, @RequestParam int profCourseId,
            @RequestParam(defaultValue = "false", required = false) String print) {
        MenuAccess menuAccess = adminService.findMenuAccess();
        model.addAttribute("menuAccess", menuAccess);

        ProfessorCourse pc = authorizationGuardService.requireOwnedProfessorCourse(profCourseId, User.current(),
            "cqi-course-detail");
        Semester semester = pc.getSemester();

        model.addAttribute("isEditable", menuAccess.isCqi() && semester.isCurrent());
        model.addAttribute("myAvg", profService.getAssignedAvg(pc));
        model.addAttribute("pc", pc);
        Searchable s = new Searchable();
        s.setCourseId(pc.getCourseId());
        s.setYear(pc.getSemester().getYear());
        s.setEnabled(true);
        s.setOrderParam("divide");
        s.setOrderDir("asc");
        List<ProfessorCourse> professorCourseList = adminService.findProfessorCoursesByFlat(s);

        Set<Integer> professorCourseIdSet = new LinkedHashSet<>();
        for (ProfessorCourse professorCourse : professorCourseList) {
            professorCourseIdSet.add(professorCourse.getId());
        }

        Map<Integer, List<Assessment>> assessmentsByCourseId = new HashMap<>();
        Map<Integer, List<StudentCourse>> studentCoursesByCourseId = new HashMap<>();
        if (!professorCourseIdSet.isEmpty()) {
            List<Integer> professorCourseIds = new ArrayList<>(professorCourseIdSet);

            List<Assessment> assessmentList = findAssessmentsByProfCourseIdsBatched(professorCourseIds);
            for (Assessment assessment : assessmentList) {
                int courseId = assessment.getProfCourseId();
                List<Assessment> assessments = assessmentsByCourseId.get(courseId);
                if (assessments == null) {
                    assessments = new ArrayList<>();
                    assessmentsByCourseId.put(courseId, assessments);
                }
                assessments.add(assessment);
            }

            List<StudentCourse> studentCourseList = findFlatStudentCoursesByProfCourseIdsBatched(professorCourseIds);
            for (StudentCourse studentCourse : studentCourseList) {
                int courseId = studentCourse.getProfCourseId();
                List<StudentCourse> courses = studentCoursesByCourseId.get(courseId);
                if (courses == null) {
                    courses = new ArrayList<>();
                    studentCoursesByCourseId.put(courseId, courses);
                }
                courses.add(studentCourse);
            }
        }

        for (ProfessorCourse professorCourse : professorCourseList) {
            int professorCourseIdValue = professorCourse.getId();
            List<Assessment> assessments = assessmentsByCourseId.get(professorCourseIdValue);
            professorCourse.setAssessmentList(assessments == null ? new ArrayList<>() : assessments);

            List<StudentCourse> studentCourseList = studentCoursesByCourseId.get(professorCourseIdValue);
            professorCourse.setStudentCourseList(studentCourseList == null ? new ArrayList<>() : studentCourseList);
        }
        model.addAttribute("professorCourseList", professorCourseList);

        int currentYear = pc.getSemester().getYear();
        model.addAttribute("currentYear", currentYear);
        LectureFundamentals lectureFundamentals = adminService.findLectureFundamentalsByProfCourseId(pc.getId());
        model.addAttribute("lectureFundamentals", lectureFundamentals);

        Map<Integer, Double> averageAssignedDivideMap = profService.getAverageAssignedDivideMap(professorCourseList);
        model.addAttribute("averageAssignedDivideMap", averageAssignedDivideMap);

        List<Integer> cqiYears = new ArrayList<>();
        for (int i = (currentYear - 2); i < currentYear; i++) {
            cqiYears.add(i);
        }

        Map<Integer, Cqi> cqiByYear = new HashMap<>();
        List<Cqi> matchedCqis = adminService.findCqisFlatByYearsCourseIdDivide(cqiYears, pc.getCourseId(), pc.getDivide());
        Set<Integer> matchedSemesterIds = new LinkedHashSet<>();
        for (Cqi matchedCqi : matchedCqis) {
            matchedSemesterIds.add(matchedCqi.getSemesterId());
        }

        Map<Integer, Integer> semesterYearById = new HashMap<>();
        if (!matchedSemesterIds.isEmpty()) {
            List<Semester> matchedSemesters = adminService.findSemestersByIds(new ArrayList<>(matchedSemesterIds));
            for (Semester matchedSemester : matchedSemesters) {
                semesterYearById.put(matchedSemester.getId(), matchedSemester.getYear());
            }
        }

        for (Cqi matchedCqi : matchedCqis) {
            Integer year = semesterYearById.get(matchedCqi.getSemesterId());
            if (year != null) {
                cqiByYear.putIfAbsent(year, matchedCqi);
            }
        }

        Map<Integer, Cqi> cqiMap = new LinkedHashMap<>();
        for (Integer year : cqiYears) {
            Cqi cqi = cqiByYear.get(year);
            if (cqi == null) {
                cqi = new Cqi();
            }
            cqiMap.put(year, cqi);
            if (year == currentYear - 1) {
                model.addAttribute("prevCqi", cqi);
            }
        }
        model.addAttribute("cqiMap", cqiMap);

        Cqi cqi = adminService.findCqiByProfCourseId(pc.getId());
        if (cqi == null) {
            cqi = new Cqi();
            cqi.setSemesterId(pc.getSemesterId());
            cqi.setCourseId(pc.getCourseId());
            cqi.setDivide(pc.getDivide());
            cqi.setProfCourseId(pc.getId());
            cqi.setUserId(User.current().getId());
        }
        model.addAttribute("cqi", cqi);
        Map<Integer, Double> averageAssignedMap = profService.getAverageAssignedMap(pc.getCourseId(), currentYear);
        model.addAttribute("averageAssignedMap", averageAssignedMap);
        List<AssessmentFactor> assessmentFactors = adminService.findAssessmentFactorsByCourseId(pc.getCourseId());
        model.addAttribute("assessmentFactors", assessmentFactors);

        if (print.equals("true"))
            return "role/common/cqi/courseDetailForPrint";
        return "role/professor/cqiReport/courseDetail";
    }

    @RequestMapping(value = "/classProgress/cqiReport/courseDetail", method = RequestMethod.POST)
    @ResponseBody
    public String cqiReportCourseDetail(@ModelAttribute koreatech.cse.domain.dto.CqiRequest req,
            @RequestParam int profCourseId) {
        User user = User.current();
        ProfessorCourse ownedCourse = authorizationGuardService.requireOwnedProfessorCourse(profCourseId, user,
                "cqi-course-save");

        if (req.getId() != 0) {
            authorizationGuardService.requireOwnedCqi(req.getId(), profCourseId, user, "cqi-course-update");
        }

        Cqi cqi = new Cqi();
        cqi.setId(req.getId());
        cqi.setScore1(req.getScore1()); cqi.setScore2(req.getScore2()); cqi.setScore3(req.getScore3());
        cqi.setScore4(req.getScore4()); cqi.setScore5(req.getScore5()); cqi.setScore6(req.getScore6());
        cqi.setProblem(req.getProblem());
        cqi.setPlan(req.getPlan());
        cqi.setUserId(user.getId());
        cqi.setProfCourseId(ownedCourse.getId());
        cqi.setCourseId(ownedCourse.getCourseId());
        cqi.setSemesterId(ownedCourse.getSemesterId());
        cqi.setDivide(ownedCourse.getDivide());
        if (cqi.getId() == 0) {
            adminService.insertCqi(cqi);
        } else {
            adminService.updateCqi(cqi);
        }

        return "true";
    }

    @RequestMapping(value = "/classProgress/graduationResearchPlan", method = RequestMethod.GET)
    public String graduationResearchPlan(Model model) {

        model.addAttribute("yearList", getYearList());

        return "role/professor/graduationResearchPlan/graduationResearchPlan";
    }

    @RequestMapping(value = "/classProgress/graduationResearchPlan/studentTable", method = RequestMethod.GET)
    public String graduationResearchPlanStudentTable(Model model,
            @RequestParam(required = false, defaultValue = "0") int year) {

        GraduationResearchPlan firstOne = null;
        List<GraduationResearchPlan> plans;
        if (year == 0) {
            plans = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();

            searchable.setYear(year);
            searchable.setAdvisor(User.current().getId());
            plans = adminService.findGraduationResearchPlansBySearchable(searchable);

            for (GraduationResearchPlan graduationResearchPlan : plans) {
                firstOne = graduationResearchPlan;
                break;
            }
        }

        model.addAttribute("plans", plans);
        model.addAttribute("firstOne", firstOne);
        return "role/professor/graduationResearchPlan/studentTable";
    }

    @RequestMapping(value = "/classProgress/graduationResearchPlan/planDetail", method = RequestMethod.GET)
    public String graduationResearchPlanStudentDetail(Model model, @RequestParam int planId) {
        GraduationResearchPlan graduationResearchPlan = authorizationGuardService
            .requireOwnedGraduationResearchPlanForProfessor(planId, User.current(), "graduation-plan-detail");
        model.addAttribute("stored", graduationResearchPlan);
        model.addAttribute("studentUser", graduationResearchPlan.getUser());
        model.addAttribute("completeSemester",
                userService.getCompleteSemesterCount(graduationResearchPlan.getUser().getId()));
        return "role/professor/graduationResearchPlan/planDetail";
    }

    @RequestMapping(value = "/classProgress/graduationResearchPlan/planDetail", method = RequestMethod.POST)
    @ResponseBody
    public String graduationResearchPlanStudentDetail(@RequestParam int planId, @RequestParam int type) {
        GraduationResearchPlan graduationResearchPlan = authorizationGuardService
                .requireOwnedGraduationResearchPlanForProfessor(planId, User.current(), "graduation-plan-approve");
        graduationResearchPlan.setApprove(type);
        adminService.updateGraduationResearchPlan(graduationResearchPlan);

        return "true";
    }

    private List<Integer> getYearList() {
        return adminService.findSemesterYears();
    }

    private List<Assessment> findAssessmentsByProfCourseIdsBatched(List<Integer> profCourseIds) {
        if (profCourseIds == null || profCourseIds.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> uniqueProfCourseIds = new ArrayList<>(new LinkedHashSet<>(profCourseIds));
        Set<Integer> seenAssessmentIds = new LinkedHashSet<>();
        List<Assessment> merged = new ArrayList<>();

        for (int start = 0; start < uniqueProfCourseIds.size(); start += IN_CLAUSE_BATCH_SIZE) {
            int end = Math.min(start + IN_CLAUSE_BATCH_SIZE, uniqueProfCourseIds.size());
            List<Assessment> batch = adminService.findAssessmentsByProfCourseIds(uniqueProfCourseIds.subList(start, end));
            for (Assessment assessment : batch) {
                if (seenAssessmentIds.add(assessment.getId())) {
                    merged.add(assessment);
                }
            }
        }

        return merged;
    }

    private List<StudentCourse> findFlatStudentCoursesByProfCourseIdsBatched(List<Integer> profCourseIds) {
        if (profCourseIds == null || profCourseIds.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> uniqueProfCourseIds = new ArrayList<>(new LinkedHashSet<>(profCourseIds));
        Set<Integer> seenStudentCourseIds = new LinkedHashSet<>();
        List<StudentCourse> merged = new ArrayList<>();

        for (int start = 0; start < uniqueProfCourseIds.size(); start += IN_CLAUSE_BATCH_SIZE) {
            int end = Math.min(start + IN_CLAUSE_BATCH_SIZE, uniqueProfCourseIds.size());
            List<StudentCourse> batch = adminService.findFlatStudentCoursesByProfCourseIdsValid(
                    uniqueProfCourseIds.subList(start, end));
            for (StudentCourse studentCourse : batch) {
                if (seenStudentCourseIds.add(studentCourse.getId())) {
                    merged.add(studentCourse);
                }
            }
        }

        return merged;
    }

    private String checkboxToStr(String[] sArray) {
        String str = "";
        if (sArray == null)
            return str;

        for (int i = 0; i < sArray.length; i++) {
            str += sArray[i];
            if (i < sArray.length - 1)
                str += ",";
        }
        return str;
    }

}
