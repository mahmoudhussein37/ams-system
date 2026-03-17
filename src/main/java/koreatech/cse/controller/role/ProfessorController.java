package koreatech.cse.controller.role;

import koreatech.cse.domain.Searchable;
import koreatech.cse.domain.UploadedFile;
import koreatech.cse.domain.User;
import koreatech.cse.domain.constant.Designation;
import koreatech.cse.domain.constant.SubjCategory;
import koreatech.cse.domain.role.professor.*;
import koreatech.cse.domain.role.student.GraduationResearchPlan;
import koreatech.cse.domain.role.student.StudentCourse;
import koreatech.cse.domain.univ.*;
import koreatech.cse.repository.*;
import koreatech.cse.repository.provider.CqiMapper;
import koreatech.cse.service.FileService;
import koreatech.cse.service.ProfService;
import koreatech.cse.service.UserService;
import koreatech.cse.view.StudentListExcelView;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.context.MessageSource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.http.ResponseEntity;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@SessionAttributes({ "lectureFundamentals", "counseling", "lectureContents", "profLectureMethod", "cqi",
        "uploadedFile" })
@PreAuthorize("hasRole('ROLE_PROFESSOR')")
@RequestMapping("/professor")
public class ProfessorController {
    @Inject
    private UserMapper userMapper;
    @Inject
    private DivisionMapper divisionMapper;
    @Inject
    private UserService userService;
    @Inject
    private CourseMapper courseMapper;
    @Inject
    private ProfessorCourseMapper professorCourseMapper;
    @Inject
    private LectureFundamentalsMapper lectureFundamentalsMapper;
    @Inject
    private CounselingMapper counselingMapper;
    @Inject
    private SemesterMapper semesterMapper;
    @Inject
    private ProfLectureMethodMapper profLectureMethodMapper;
    @Inject
    private LectureContentsMapper lectureContentsMapper;
    @Inject
    private LectureMethodMapper lectureMethodMapper;
    @Inject
    private EvaluationMethodMapper evaluationMethodMapper;
    @Inject
    private EducationalMediumMapper educationalMediumMapper;
    @Inject
    private EquipmentMapper equipmentMapper;
    @Inject
    private MenuAccessMapper menuAccessMapper;
    @Inject
    private StudentCourseMapper studentCourseMapper;
    @Inject
    private AssessmentFactorMapper assessmentFactorMapper;
    @Inject
    private AssessmentMapper assessmentMapper;
    @Inject
    private CqiMapper cqiMapper;
    @Inject
    private GraduationCriteriaMapper graduationCriteriaMapper;
    @Inject
    private GraduationResearchPlanMapper graduationResearchPlanMapper;
    @Inject
    private UploadedFileMapper uploadedFileMapper;
    @Inject
    private FileService fileService;
    @Inject
    private MessageSource messageSource;
    @Inject
    private ProfService profService;
    @Inject
    private LangCertMapper langCertMapper;

    @ModelAttribute("currentPageRole")
    public String getCurrentPageRole() {
        return "professor";
    }

    @RequestMapping("/studentGuidance/studentLookup")
    public String studentLookup(Model model) {

        List<Division> divisions = divisionMapper.findAll();

        model.addAttribute("divisions", divisions);
        return "role/professor/studentLookup/studentLookup";
    }

    @RequestMapping("/studentGuidance/studentLookup/studentTable")
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
            userList = userMapper.findStudentBy(searchable);

            for (User user : userList) {
                firstUser = user;
                break;
            }
        }

        model.addAttribute("userList", userList);
        model.addAttribute("firstUser", firstUser);
        return "role/professor/studentLookup/studentTable";
    }

    @RequestMapping("/studentGuidance/studentLookup/studentDetail")
    public String studentDetail(Model model, @RequestParam int studentId,
            @RequestParam(defaultValue = "false", required = false) String print) {
        User studentUser = userMapper.findOne(studentId);

        User advisor = userMapper.findOne(studentUser.getAdvisorId());
        model.addAttribute("advisor", advisor);
        int admissionYear = studentUser.getContact().getAdmissionYear();
        int divisionId = studentUser.getDivisionId();

        GraduationCriteria graduationCriteria = graduationCriteriaMapper.findOneByYearDivision(admissionYear,
                divisionId);
        studentUser.setGraduationCriteria(graduationCriteria == null ? new GraduationCriteria() : graduationCriteria);

        List<StudentCourse> studentCourses = studentCourseMapper.findByUserIdValid(studentId);
        studentUser.setStudentCourses(studentCourses);

        model.addAttribute("studentUser", studentUser);

        List<LangCert> langCerts = langCertMapper.findByUserId(studentId);
        model.addAttribute("langCerts", langCerts);
        if (print.equals("true"))
            return "role/professor/studentLookup/studentDetailForPrint";
        return "role/professor/studentLookup/studentDetail";
    }

    @RequestMapping("/studentGuidance/counseling")
    public String counseling(Model model, @RequestParam(required = false) String result) {
        model.addAttribute("yearList", getYearList());
        model.addAttribute("result", result);
        return "role/professor/counseling/counseling";
    }

    @RequestMapping("/studentGuidance/counseling/newCounseling")
    public String newCounseling(Model model) {
        List<Division> divisions = divisionMapper.findAll();

        model.addAttribute("divisions", divisions);
        return "role/professor/counseling/newCounseling";
    }

    @RequestMapping("/studentGuidance/counseling/newCounselingDetail")
    public String newCounselingDetail(Model model, @RequestParam int studentId) {
        User studentUser = userMapper.findOne(studentId);
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
    public String newCounselingDetail(@ModelAttribute Counseling counseling, @RequestParam int studentId) {
        // generate number
        String number = UUID.randomUUID().toString().replace("-", "");
        counseling.setNumber(number);
        User user = User.current();
        counseling.setProfUserId(user.getId());
        counselingMapper.insert(counseling);
        return "redirect:/professor/studentGuidance/counseling?result=success";
    }

    @RequestMapping("/studentGuidance/counseling/studentTable")
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
            userList = userMapper.findStudentBy(searchable);

            for (User user : userList) {
                firstUser = user;
                break;
            }
        }

        model.addAttribute("userList", userList);
        model.addAttribute("firstUser", firstUser);
        return "role/professor/counseling/studentTable";
    }

    @RequestMapping("/studentGuidance/counseling/counselingTable")
    public String counselingStudentTable(Model model, @RequestParam(required = false, defaultValue = "0") int year,
            @RequestParam(required = false) String name) {
        Counseling firstCounseling = null;
        List<Counseling> counselingList;
        if (year == 0 && StringUtils.isBlank(name)) {
            counselingList = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();
            searchable.setYear(year);
            searchable.setName(name);
            counselingList = counselingMapper.findByCounseling(searchable);

            for (Counseling counseling : counselingList) {
                firstCounseling = counseling;
                break;
            }
        }

        model.addAttribute("counselingList", counselingList);
        model.addAttribute("firstCounseling", firstCounseling);
        return "role/professor/counseling/counselingTable";
    }

    @RequestMapping("/studentGuidance/counseling/counselingDetail")
    public String counselingStudentDetail(Model model, @RequestParam int counselingId,
            @RequestParam(defaultValue = "false", required = false) String print) {
        Counseling counseling = counselingMapper.findOne(counselingId);
        model.addAttribute("counseling", counseling);

        User studentUser = counseling.getStudentUser();

        int studentId = studentUser.getId();
        User advisor = userMapper.findOne(studentUser.getAdvisorId());
        model.addAttribute("advisor", advisor);
        int admissionYear = studentUser.getContact().getAdmissionYear();
        int divisionId = studentUser.getDivisionId();

        GraduationCriteria graduationCriteria = graduationCriteriaMapper.findOneByYearDivision(admissionYear,
                divisionId);
        studentUser.setGraduationCriteria(graduationCriteria == null ? new GraduationCriteria() : graduationCriteria);
        List<StudentCourse> studentCourses = studentCourseMapper.findByUserIdValid(studentId);
        studentUser.setStudentCourses(studentCourses);
        model.addAttribute("studentUser", studentUser);

        if (print.equals("true"))
            return "role/professor/counseling/counselingDetailForPrint";
        return "role/professor/counseling/counselingDetail";
    }

    @RequestMapping("/classProgress/attendance")
    public String attendance(Model model) {

        List<Division> divisions = divisionMapper.findAll();

        model.addAttribute("divisions", divisions);
        model.addAttribute("yearList", getYearList());
        model.addAttribute("course", new Course());
        model.addAttribute("subjCategoryList", SubjCategory.values());

        return "role/professor/attendance/attendance";
    }

    @RequestMapping("/classProgress/attendance/studentListExcel")
    public ModelAndView institutionExcel(HttpServletResponse response, @RequestParam int profCourseId, Locale locale) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("Egypt"));
        String todayDateString = sdf.format(new Date());
        Map<String, Object> objectMap = new HashMap<>();
        List<StudentCourse> studentCourses = studentCourseMapper.findByProfCourseId(profCourseId);

        objectMap.put("studentCourses", studentCourses);
        objectMap.put("messageSource", messageSource);
        objectMap.put("locale", locale);
        response.setContentType("application/ms-excel");
        String fileName = "attendance_" + todayDateString;
        response.setHeader("Content-disposition", String.format("attachment; filename=%s.xls", fileName));
        return new ModelAndView(new StudentListExcelView(), objectMap);
    }

    @RequestMapping("/classProgress/attendance/courseTable")
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

            courseList = professorCourseMapper.findBy(searchable);

            for (ProfessorCourse course : courseList) {
                firstCourse = course;
                break;
            }
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("profCourseList", courseList);
        return "role/professor/attendance/courseTable";
    }

    @RequestMapping("/classProgress/attendance/courseDetail")
    public String attendanceCourseDetail(Model model, @RequestParam int profCourseId) {
        ProfessorCourse pc = professorCourseMapper.findOne(profCourseId);
        model.addAttribute("pc", pc);
        model.addAttribute("uploadedFile", new UploadedFile());
        Semester semester = pc.getSemester();
        model.addAttribute("isEditable", semester.isCurrent());
        return "role/professor/attendance/courseDetail";
    }

    @RequestMapping(value = "/classProgress/attendance/courseDetail", method = RequestMethod.POST)
    public String attendanceCourseDetail(@ModelAttribute UploadedFile uploadedFile, @RequestParam int profCourseId) {
        ProfessorCourse pc = professorCourseMapper.findOne(profCourseId);
        User user = User.current();
        Semester semester = pc.getSemester();

        List<UploadedFile> uploadedFiles = uploadedFileMapper.findByDesignationProfCourseId(Designation.attendance,
                profCourseId);

        for (UploadedFile stored : uploadedFiles) {
            uploadedFileMapper.delete(stored);
        }
        MultipartFile multipartFile = uploadedFile.getFile();
        if (multipartFile != null) {
            try {
                fileService.processUploadedFile(multipartFile, user, Designation.attendance,
                        pc.getCourse().getDivisionId(), profCourseId, semester.getYear());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "redirect:/professor/classProgress/attendance";
    }

    @RequestMapping("/classProgress/inquiryCourse")
    public String inquiryCourse(Model model) {

        model.addAttribute("yearList", getYearList());
        return "role/professor/inquiryCourse/inquiryCourse";
    }

    @RequestMapping("/classProgress/inquiryCourse/courseTable")
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
            courseList = professorCourseMapper.findBy(searchable);

            for (ProfessorCourse course : courseList) {
                firstCourse = course;
                break;
            }
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("profCourseList", courseList);
        return "role/professor/inquiryCourse/courseTable";
    }

    @RequestMapping("/classProgress/inquiryCourse/courseDetail")
    public String inCourseDetail(Model model, @RequestParam int profCourseId,
            @RequestParam(defaultValue = "false", required = false) String print) {
        ProfessorCourse pc = professorCourseMapper.findOne(profCourseId);
        model.addAttribute("pc", pc);
        LectureFundamentals lectureFundamentals = lectureFundamentalsMapper.findByProfCourseId(pc.getId());
        model.addAttribute("lectureFundamentals",
                lectureFundamentals == null ? new LectureFundamentals() : lectureFundamentals);
        ProfLectureMethod profLectureMethod = profLectureMethodMapper.findByProfCourseId(pc.getId());
        model.addAttribute("profLectureMethod",
                profLectureMethod == null ? new ProfLectureMethod() : profLectureMethod);
        LectureContents lectureContents = lectureContentsMapper.findByProfCourseId(pc.getId());
        model.addAttribute("lectureContents", lectureContents == null ? new LectureContents() : lectureContents);

        List<LectureMethod> lectureMethods = lectureMethodMapper.findAll();
        model.addAttribute("lectureMethods", lectureMethods);

        List<EducationalMedium> educationalMediums = educationalMediumMapper.findAll();
        model.addAttribute("educationalMediums", educationalMediums);

        List<EvaluationMethod> evaluationMethods = evaluationMethodMapper.findAll();
        model.addAttribute("evaluationMethods", evaluationMethods);

        List<Equipment> equipments = equipmentMapper.findAll();
        model.addAttribute("equipments", equipments);

        MenuAccess menuAccess = menuAccessMapper.findOne();
        model.addAttribute("menuAccess", menuAccess);
        if (print.equals("true"))
            return "role/common/syllabus/courseDetailForPrint";
        return "role/professor/inquiryCourse/courseDetail";
    }

    @RequestMapping("/classProgress/syllabus")
    public String syllabus(Model model, @RequestParam(required = false) String result) {

        model.addAttribute("yearList", getYearList());
        model.addAttribute("result", result);
        return "role/professor/syllabus/syllabus";
    }

    @RequestMapping("/classProgress/syllabus/courseDetail")
    public String courseDetail(Model model, @RequestParam int profCourseId,
            @RequestParam(defaultValue = "false", required = false) String print) {
        ProfessorCourse pc = professorCourseMapper.findOne(profCourseId);

        model.addAttribute("pc", pc);
        LectureFundamentals lectureFundamentals = lectureFundamentalsMapper.findByProfCourseId(pc.getId());
        model.addAttribute("lectureFundamentals",
                lectureFundamentals == null ? new LectureFundamentals() : lectureFundamentals);
        ProfLectureMethod profLectureMethod = profLectureMethodMapper.findByProfCourseId(pc.getId());
        model.addAttribute("profLectureMethod",
                profLectureMethod == null ? new ProfLectureMethod() : profLectureMethod);
        LectureContents lectureContents = lectureContentsMapper.findByProfCourseId(pc.getId());
        model.addAttribute("lectureContents", lectureContents == null ? new LectureContents() : lectureContents);

        List<LectureMethod> lectureMethods = lectureMethodMapper.findAll();
        model.addAttribute("lectureMethods", lectureMethods);
        List<LectureMethod> lectureMethodsEnabled = lectureMethodMapper.findAllEnabled();
        model.addAttribute("lectureMethodsEnabled", lectureMethodsEnabled);

        List<EducationalMedium> educationalMediums = educationalMediumMapper.findAll();
        model.addAttribute("educationalMediums", educationalMediums);
        List<EducationalMedium> educationalMediumsEnabled = educationalMediumMapper.findAllEnabled();
        model.addAttribute("educationalMediumsEnabled", educationalMediumsEnabled);

        List<EvaluationMethod> evaluationMethods = evaluationMethodMapper.findAll();
        model.addAttribute("evaluationMethods", evaluationMethods);
        List<EvaluationMethod> evaluationMethodsEnabled = evaluationMethodMapper.findAllEnabled();
        model.addAttribute("evaluationMethodsEnabled", evaluationMethodsEnabled);

        List<Equipment> equipments = equipmentMapper.findAll();
        model.addAttribute("equipments", equipments);
        List<Equipment> equipmentsEnabled = equipmentMapper.findAllEnabled();
        model.addAttribute("equipmentsEnabled", equipmentsEnabled);

        MenuAccess menuAccess = menuAccessMapper.findOne();
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
            @ModelAttribute LectureFundamentals lectureFundamentals) {

        Map<String, Object> response = new HashMap<>();

        try {
            User user = User.current();
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
                LectureFundamentals existing = lectureFundamentalsMapper.findByProfCourseId(profCourseId);
                if (existing != null) {
                    // Update existing instead of insert
                    lectureFundamentals.setId(existing.getId());
                    lectureFundamentalsMapper.update(lectureFundamentals);
                } else {
                    lectureFundamentalsMapper.insert(lectureFundamentals);
                }
            } else {
                lectureFundamentalsMapper.update(lectureFundamentals);
            }

            response.put("success", true);
            response.put("message", "Saved successfully");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "An error occurred while saving: " + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    @RequestMapping(value = "/classProgress/syllabus/courseDetail/profLectureMethod", method = RequestMethod.POST)
    @ResponseBody
    public String profLectureMethod(@RequestParam int profCourseId,
            @ModelAttribute ProfLectureMethod profLectureMethod) {
        User user = User.current();
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
            profLectureMethodMapper.insert(profLectureMethod);
        } else {
            profLectureMethodMapper.update(profLectureMethod);
        }
        return "success";
    }

    @RequestMapping(value = "/classProgress/syllabus/courseDetail/lectureContents", method = RequestMethod.POST)
    @ResponseBody
    public String lectureContents(@RequestParam int profCourseId, @ModelAttribute LectureContents lectureContents) {
        User user = User.current();
        lectureContents.setUserId(user.getId());
        lectureContents.setProfCourseId(profCourseId);
        if (lectureContents.getId() == 0) {
            lectureContentsMapper.insert(lectureContents);
        } else {
            lectureContentsMapper.update(lectureContents);
        }
        return "success";
    }

    @RequestMapping("/classProgress/syllabus/courseTable")
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
            System.out.println("searchable = " + searchable);

            courseList = professorCourseMapper.findBy(searchable);

            for (ProfessorCourse course : courseList) {
                firstCourse = course;
                break;
            }
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("courseList", courseList);
        return "role/professor/syllabus/courseTable";
    }

    @RequestMapping("/classProgress/classAssessment")
    public String classAssessment(Model model) {

        model.addAttribute("yearList", getYearList());

        return "role/professor/classAssessment/classAssessment";
    }

    @RequestMapping("/classProgress/classAssessment/courseTable")
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

            courseList = professorCourseMapper.findBy(searchable);

            for (ProfessorCourse course : courseList) {
                firstCourse = course;
                break;
            }
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("courseList", courseList);
        return "role/professor/classAssessment/courseTable";
    }

    @RequestMapping("/classProgress/classAssessment/courseDetail")
    public String classAssessmentCourseDetail(Model model, @RequestParam int profCourseId,
            @RequestParam(defaultValue = "false", required = false) String print) {
        ProfessorCourse pc = professorCourseMapper.findOne(profCourseId);
        model.addAttribute("pc", pc);
        List<Assessment> assessments = assessmentMapper.findByProfCourseId(profCourseId);
        model.addAttribute("assessments", assessments);
        List<AssessmentFactor> assessmentFactors = assessmentFactorMapper.findByCourseId(pc.getCourseId());
        model.addAttribute("assessmentFactors", assessmentFactors);
        MenuAccess menuAccess = menuAccessMapper.findOne();
        model.addAttribute("menuAccess", menuAccess);
        Semester semester = pc.getSemester();
        model.addAttribute("isViewable", !semester.isCurrent() || !menuAccess.isAssessment());
        if (print.equals("true"))
            return "role/common/assessment/courseDetailForPrint";

        return "role/professor/classAssessment/courseDetail";
    }

    @RequestMapping("/classProgress/registerGrade")
    public String registerGrade(Model model) {

        model.addAttribute("yearList", getYearList());

        return "role/professor/registerGrade/registerGrade";
    }

    @RequestMapping(value = "/classProgress/registerGrade/gradeEditable", method = RequestMethod.POST)
    @ResponseBody
    public String gradeEditable(@RequestParam int pk, @RequestParam String name, @RequestParam String value) {
        User currentUser = User.current();
        int currentProfessorId = currentUser.getId();

        StudentCourse sc = studentCourseMapper.findOne(pk);
        if (sc == null || sc.getProfessorCourse() == null
                || sc.getProfessorCourse().getUserId() != currentProfessorId) {
            throw new AccessDeniedException("Professor does not own this course");
        }

        String result = Integer.toString(pk);
        applyEditableGradeField(sc, name, value);

        int total = sc.getScoreAssignment() + sc.getScoreMid() + sc.getScoreFinal() + sc.getScoreOptions();
        sc.setScoreTotal(total);

        LectureFundamentals lectureFundamentals = lectureFundamentalsMapper.findByProfCourseId(sc.getProfCourseId());
        ProfessorCourse pc = professorCourseMapper.findOne(sc.getProfCourseId());

        String grade = "";
        if (pc.isSecondTest()) {
            if (total >= 85)
                grade = "A";
            else if (total >= 75)
                grade = "B";
            else if (total >= 65)
                grade = "C";
            else if (total >= 50)
                grade = "D";
            else if (total >= 40)
                grade = "E";
            else
                grade = "F";
        } else {

            double finalRatio;
            if (sc.getScoreFinal() == 0 || lectureFundamentals.getRateFinal() == 0.0)
                finalRatio = 0.0;
            else
                finalRatio = (double) sc.getScoreFinal() / lectureFundamentals.getRateFinal();

            if (finalRatio < 0.4)
                grade = "G";
            else if (total >= 85)
                grade = "A";
            else if (total >= 75)
                grade = "B";
            else if (total >= 65)
                grade = "C";
            else if (total >= 60)
                grade = "D";
            else
                grade = "F";
        }

        studentCourseMapper.update(sc);

        return result + "_" + total + "_" + grade;
    }

    private void applyEditableGradeField(StudentCourse sc, String name, String value) {
        int scoreValue;
        try {
            scoreValue = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid grade value");
        }

        if ("scoreAssignment".equals(name)) {
            sc.setScoreAssignment(scoreValue);
        } else if ("scoreMid".equals(name)) {
            sc.setScoreMid(scoreValue);
        } else if ("scoreFinal".equals(name)) {
            sc.setScoreFinal(scoreValue);
        } else if ("scoreOptions".equals(name)) {
            sc.setScoreOptions(scoreValue);
        } else {
            throw new IllegalArgumentException("Invalid grade field");
        }
    }

    @RequestMapping("/classProgress/registerGrade/courseTable")
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

            courseList = professorCourseMapper.findBy(searchable);
            for (ProfessorCourse course : courseList) {
                firstCourse = course;
                break;
            }
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("courseList", courseList);
        return "role/professor/registerGrade/courseTable";
    }

    @RequestMapping("/classProgress/registerGrade/courseDetail")
    public String registerGradeCourseDetail(Model model, @RequestParam int profCourseId) {
        ProfessorCourse pc = professorCourseMapper.findOne(profCourseId);
        model.addAttribute("pc", pc);

        LectureFundamentals lectureFundamentals = lectureFundamentalsMapper.findByProfCourseId(profCourseId);
        if (lectureFundamentals == null)
            return "role/common/syllabus/requiredSyllabus";

        model.addAttribute("lectureFundamentals", lectureFundamentals);

        List<StudentCourse> studentCourses = studentCourseMapper.findByProfCourseId(pc.getId());
        model.addAttribute("studentCourses", studentCourses);
        MenuAccess menuAccess = menuAccessMapper.findOne();
        Semester semester = pc.getSemester();
        model.addAttribute("menuAccess", menuAccess);
        model.addAttribute("isEditable", menuAccess.isGrade() && semester.isCurrent());

        return "role/professor/registerGrade/courseDetail";
    }

    @RequestMapping("/classProgress/registerGrade/courseDetailForPrint")
    public String registerGradeCourseDetailForPrint(Model model, @RequestParam int profCourseId) {
        ProfessorCourse pc = professorCourseMapper.findOne(profCourseId);
        model.addAttribute("pc", pc);

        LectureFundamentals lectureFundamentals = lectureFundamentalsMapper.findByProfCourseId(profCourseId);
        if (lectureFundamentals == null) {
            return "redirect:/professor/classProgress/syllabus";
        }
        model.addAttribute("lectureFundamentals", lectureFundamentals);

        List<StudentCourse> studentCourses = studentCourseMapper.findByProfCourseId(pc.getId());
        model.addAttribute("studentCourses", studentCourses);

        return "role/common/grade/courseDetailForPrint";
    }

    @RequestMapping(value = "/classProgress/registerGrade/courseDetail", method = RequestMethod.POST)
    @ResponseBody
    public Boolean registerGradeCourseDetail(@RequestParam int profCourseId) {
        ProfessorCourse pc = professorCourseMapper.findOne(profCourseId);
        LectureFundamentals lectureFundamentals = lectureFundamentalsMapper.findByProfCourseId(profCourseId);
        if (lectureFundamentals == null) {
            return false;
        }
        List<StudentCourse> studentCourses = studentCourseMapper.findByProfCourseId(pc.getId());

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
            studentCourseMapper.update(sc);
        }

        return valid;
    }

    @RequestMapping("/classProgress/registerGrade/ratioDetail")
    public String registerGradeRatioDetail(Model model, @RequestParam int profCourseId) {
        ProfessorCourse pc = professorCourseMapper.findOne(profCourseId);
        model.addAttribute("pc", pc);

        LectureFundamentals lectureFundamentals = lectureFundamentalsMapper.findByProfCourseId(profCourseId);
        model.addAttribute("lectureFundamentals", lectureFundamentals);

        List<StudentCourse> studentCourses = studentCourseMapper.findByProfCourseId(pc.getId());
        model.addAttribute("studentCourses", studentCourses);

        return "role/common/grade/ratioDetail";
    }

    @RequestMapping("/classProgress/cqiReport")
    public String cqiReport(Model model) {

        model.addAttribute("yearList", getYearList());

        return "role/professor/cqiReport/cqiReport";
    }

    @RequestMapping("/classProgress/cqiReport/courseTable")
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

            courseList = professorCourseMapper.findBy(searchable);

            for (ProfessorCourse course : courseList) {
                firstCourse = course;
                break;
            }
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("courseList", courseList);
        return "role/professor/cqiReport/courseTable";
    }

    @RequestMapping("/classProgress/cqiReport/courseDetail")
    public String cqiReportCourseDetail(Model model, @RequestParam int profCourseId,
            @RequestParam(defaultValue = "false", required = false) String print) {
        MenuAccess menuAccess = menuAccessMapper.findOne();
        model.addAttribute("menuAccess", menuAccess);

        ProfessorCourse pc = professorCourseMapper.findOne(profCourseId);
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
        List<ProfessorCourse> professorCourseList = professorCourseMapper.findBy(s);
        for (ProfessorCourse professorCourse : professorCourseList) {
            List<Assessment> assessments = assessmentMapper.findByProfCourseId(professorCourse.getId());
            professorCourse.setAssessmentList(assessments);
            List<StudentCourse> studentCourseList = studentCourseMapper
                    .findByProfCourseIdValid(professorCourse.getId());
            professorCourse.setStudentCourseList(studentCourseList);
        }
        model.addAttribute("professorCourseList", professorCourseList);

        int currentYear = pc.getSemester().getYear();
        model.addAttribute("currentYear", currentYear);
        LectureFundamentals lectureFundamentals = lectureFundamentalsMapper.findByProfCourseId(pc.getId());
        model.addAttribute("lectureFundamentals", lectureFundamentals);

        Map<Integer, Double> averageAssignedDivideMap = profService.getAverageAssignedDivideMap(professorCourseList);
        model.addAttribute("averageAssignedDivideMap", averageAssignedDivideMap);

        Map<Integer, Cqi> cqiMap = new LinkedHashMap<>();
        for (int i = (currentYear - 2); i < currentYear; i++) {
            Cqi cqi = cqiMapper.findByYearCourseIdDivide(i, pc.getCourseId(), pc.getDivide());

            if (cqi == null) {
                cqi = new Cqi();
            }
            cqiMap.put(i, cqi);
            if (i == currentYear - 1) {
                model.addAttribute("prevCqi", cqi);
            }
        }
        model.addAttribute("cqiMap", cqiMap);

        Cqi cqi = cqiMapper.findByProfCourseId(pc.getId());
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
        List<AssessmentFactor> assessmentFactors = assessmentFactorMapper.findByCourseId(pc.getCourseId());
        model.addAttribute("assessmentFactors", assessmentFactors);

        if (print.equals("true"))
            return "role/common/cqi/courseDetailForPrint";
        return "role/professor/cqiReport/courseDetail";
    }

    @RequestMapping(value = "/classProgress/cqiReport/courseDetail", method = RequestMethod.POST)
    @ResponseBody
    public String cqiReportCourseDetail(@ModelAttribute("cqi") Cqi cqi, @RequestParam int profCourseId) {

        User user = User.current();
        cqi.setUserId(user.getId());
        System.out.println("cqi = " + cqi);
        if (cqi.getId() == 0) {
            cqiMapper.insert(cqi);
        } else {
            cqiMapper.update(cqi);
        }

        return "true";
    }

    @RequestMapping("/classProgress/graduationResearchPlan")
    public String graduationResearchPlan(Model model) {

        model.addAttribute("yearList", getYearList());

        return "role/professor/graduationResearchPlan/graduationResearchPlan";
    }

    @RequestMapping("/classProgress/graduationResearchPlan/studentTable")
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
            plans = graduationResearchPlanMapper.findBySearchable(searchable);

            for (GraduationResearchPlan graduationResearchPlan : plans) {
                firstOne = graduationResearchPlan;
                break;
            }
        }

        model.addAttribute("plans", plans);
        model.addAttribute("firstOne", firstOne);
        return "role/professor/graduationResearchPlan/studentTable";
    }

    @RequestMapping("/classProgress/graduationResearchPlan/planDetail")
    public String graduationResearchPlanStudentDetail(Model model, @RequestParam int planId) {
        GraduationResearchPlan graduationResearchPlan = graduationResearchPlanMapper.findOne(planId);
        model.addAttribute("stored", graduationResearchPlan);
        model.addAttribute("studentUser", graduationResearchPlan.getUser());
        model.addAttribute("completeSemester",
                userService.getCompleteSemesterCount(graduationResearchPlan.getUser().getId()));
        return "role/professor/graduationResearchPlan/planDetail";
    }

    @RequestMapping(value = "/classProgress/graduationResearchPlan/planDetail", method = RequestMethod.POST)
    @ResponseBody
    public String graduationResearchPlanStudentDetail(@RequestParam int planId, @RequestParam int type) {
        GraduationResearchPlan graduationResearchPlan = graduationResearchPlanMapper.findOne(planId);
        graduationResearchPlan.setApprove(type);
        graduationResearchPlanMapper.update(graduationResearchPlan);
        System.out.println("graduationResearchPlan = " + graduationResearchPlan);

        return "true";
    }

    private List<Integer> getYearList() {
        return semesterMapper.findYears();
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
