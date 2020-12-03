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
import koreatech.cse.service.UserService;
import koreatech.cse.util.SystemUtil;
import koreatech.cse.view.StudentListExcelView;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@SessionAttributes({"lectureFundamentals", "counseling", "lectureContents", "profLectureMethod", "cqi", "uploadedFile"})
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

    @RequestMapping("/studentGuidance/studentLookup")
    public String studentLookup(Model model) {

        List<Division> divisions = divisionMapper.findAll();

        model.addAttribute("divisions", divisions);
        return "role/professor/studentLookup/studentLookup";
    }

    @RequestMapping("/studentGuidance/studentLookup/studentTable")
    public String studentTable(Model model, @RequestParam(required=false) String number,
                               @RequestParam(required=false) String name,
                               @RequestParam(defaultValue = "0", required=false) int division) {
        User firstUser = null;
        List<User> userList;
        if(StringUtils.isBlank(number) && StringUtils.isBlank(name) && division == 0) {
            userList = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();
            searchable.setNumber(number);
            searchable.setName(name);
            searchable.setDivision(division);
            userList = userMapper.findByNameNumberDivision(searchable);


            for(User user: userList) {
                firstUser = user;
                break;
            }
        }



        model.addAttribute("userList", userList);
        model.addAttribute("firstUser", firstUser);
        return "role/professor/studentLookup/studentTable";
    }

    @RequestMapping("/studentGuidance/studentLookup/studentDetail")
    public String studentDetail(Model model, @RequestParam int studentId, @RequestParam(defaultValue = "false", required=false) String print) {
        User studentUser = userMapper.findOne(studentId);


        User advisor = userMapper.findOne(studentUser.getAdvisorId());
        model.addAttribute("advisor", advisor);
        int admissionYear = studentUser.getContact().getAdmissionYear();
        int divisionId = studentUser.getDivisionId();

        GraduationCriteria graduationCriteria = graduationCriteriaMapper.findOneByYearDivision(admissionYear, divisionId);
        studentUser.setGraduationCriteria(graduationCriteria == null ? new GraduationCriteria() : graduationCriteria);


        List<StudentCourse> studentCourses = studentCourseMapper.findByUserIdValid(studentId);
        studentUser.setStudentCourses(studentCourses);

        model.addAttribute("studentUser", studentUser);
        if(print.equals("true"))
            return "role/professor/studentLookup/studentDetailForPrint";
        return "role/professor/studentLookup/studentDetail";
    }


    @RequestMapping("/studentGuidance/counseling")
    public String counseling(Model model, @RequestParam(required=false) String result) {
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
        //generate number
        String number = UUID.randomUUID().toString().replace("-", "");
        counseling.setNumber(number);
        User user = User.current();
        counseling.setProfUserId(user.getId());
        counselingMapper.insert(counseling);
        return "redirect:/professor/studentGuidance/counseling?result=success";
    }

    @RequestMapping("/studentGuidance/counseling/studentTable")
    public String counselingStudentTable(Model model, @RequestParam(required=false) String number,
                               @RequestParam(required=false) String name,
                               @RequestParam(defaultValue = "0", required=false) int division) {
        User firstUser = null;
        List<User> userList;
        if(StringUtils.isBlank(number) && StringUtils.isBlank(name) && division == 0) {
            userList = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();
            searchable.setNumber(number);
            searchable.setName(name);
            searchable.setDivision(division);
            userList = userMapper.findByNameNumberDivision(searchable);


            for(User user: userList) {
                firstUser = user;
                break;
            }
        }

        model.addAttribute("userList", userList);
        model.addAttribute("firstUser", firstUser);
        return "role/professor/counseling/studentTable";
    }

    @RequestMapping("/studentGuidance/counseling/counselingTable")
    public String counselingStudentTable(Model model, @RequestParam(required=false, defaultValue = "0") int year,
                               @RequestParam(required=false) String name) {
        Counseling firstCounseling = null;
        List<Counseling> counselingList;
        if(year == 0 && StringUtils.isBlank(name)) {
            counselingList = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();
            searchable.setYear(year);
            searchable.setName(name);
            counselingList = counselingMapper.findByCounseling(searchable);

            for(Counseling counseling: counselingList) {
                firstCounseling = counseling;
                break;
            }
        }

        model.addAttribute("counselingList", counselingList);
        model.addAttribute("firstCounseling", firstCounseling);
        return "role/professor/counseling/counselingTable";
    }

    @RequestMapping("/studentGuidance/counseling/counselingDetail")
    public String counselingStudentDetail(Model model, @RequestParam int counselingId, @RequestParam(defaultValue = "false", required=false) String print) {
        Counseling counseling = counselingMapper.findOne(counselingId);
        model.addAttribute("counseling", counseling);

        User studentUser = counseling.getStudentUser();

        int studentId = studentUser.getId();
        User advisor = userMapper.findOne(studentUser.getAdvisorId());
        model.addAttribute("advisor", advisor);
        int admissionYear = studentUser.getContact().getAdmissionYear();
        int divisionId = studentUser.getDivisionId();

        GraduationCriteria graduationCriteria = graduationCriteriaMapper.findOneByYearDivision(admissionYear, divisionId);
        studentUser.setGraduationCriteria(graduationCriteria == null ? new GraduationCriteria() : graduationCriteria);
        List<StudentCourse> studentCourses = studentCourseMapper.findByUserIdValid(studentId);
        studentUser.setStudentCourses(studentCourses);
        model.addAttribute("studentUser", studentUser);

        if(print.equals("true"))
            return "role/professor/counseling/counselingDetailForPrint";
        return "role/professor/counseling/counselingDetail";
    }

    @RequestMapping("/studentGuidance/coCourseEnrolment")
    public String counselingEnrolment(Model model) {

        model.addAttribute("yearList", getYearList());

        return "role/professor/coCourseEnrolment/coCourseEnrolment";
    }

    @RequestMapping("/studentGuidance/coCourseEnrolment/studentTable")
    public String coCourseEnrolmentStudentTable(Model model, @RequestParam(required=false, defaultValue = "0") int year, @RequestParam(defaultValue = "0", required=false) int semester) {
        User firstUser = null;
        List<User> userList;
        if(year == 0) {
            userList = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();

            searchable.setYear(year);
            userList = userMapper.findByNameNumberDivision(searchable);


            for(User user: userList) {
                firstUser = user;
                break;
            }
        }
        model.addAttribute("userList", userList);
        model.addAttribute("firstUser", firstUser);
        return "role/professor/coCourseEnrolment/studentTable";
    }

    @RequestMapping("/studentGuidance/coCourseEnrolment/studentDetail")
    public String coCourseEnrolmentStudentDetail(Model model, @RequestParam int studentId) {
        User studentUser = userMapper.findOne(studentId);
        model.addAttribute("studentUser", studentUser);
        return "role/professor/coCourseEnrolment/studentDetail";
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
    public ModelAndView institutionExcel(HttpServletResponse response, @RequestParam int courseId, Locale locale) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("Egypt"));
        String todayDateString = sdf.format(new Date());
        Map<String, Object> objectMap = new HashMap<>();
        List<StudentCourse> studentCourses = studentCourseMapper.findByProfCourseId(courseId);

        objectMap.put("studentCourses", studentCourses);
        objectMap.put("messageSource", messageSource);
        objectMap.put("locale", locale);
        response.setContentType( "application/ms-excel" );
        String fileName = "attendance_" + todayDateString;
        response.setHeader( "Content-disposition", String.format("attachment; filename=%s.xls", fileName));
        return new ModelAndView(new StudentListExcelView(), objectMap);
    }


    @RequestMapping("/classProgress/attendance/courseTable")
    public String professorCourseTable(Model model,
                              @RequestParam(defaultValue = "0", required=false) int year,
                              @RequestParam(defaultValue = "0", required=false) int semester) {

        Searchable searchable = new Searchable();
        searchable.setYear(year);
        searchable.setSemester(semester);

        List<ProfessorCourse> courseList = professorCourseMapper.findByYearSemesterDivision(searchable);

        ProfessorCourse firstCourse = null;
        for(ProfessorCourse course: courseList) {
            firstCourse = course;
            break;
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("profCourseList", courseList);
        return "role/professor/attendance/courseTable";
    }

    @RequestMapping("/classProgress/attendance/courseDetail")
    public String attendanceCourseDetail(Model model, @RequestParam int courseId) {
        ProfessorCourse pc = professorCourseMapper.findOne(courseId);
        model.addAttribute("pc", pc);
        model.addAttribute("uploadedFile", new UploadedFile());


        return "role/professor/attendance/courseDetail";
    }

    @RequestMapping(value = "/classProgress/attendance/courseDetail", method = RequestMethod.POST)
    public String attendanceCourseDetail(@ModelAttribute UploadedFile uploadedFile, @RequestParam int courseId) {
        ProfessorCourse pc = professorCourseMapper.findOne(courseId);
        User user = User.current();
        Semester semester = pc.getSemester();

        List<UploadedFile> uploadedFiles = uploadedFileMapper.findByDesignationProfCourseId(Designation.attendance, courseId);

        for(UploadedFile stored: uploadedFiles) {
            uploadedFileMapper.delete(stored);
        }
        MultipartFile multipartFile = uploadedFile.getFile();
        if(multipartFile != null) {
            try {
                fileService.processUploadedFile(multipartFile, user, Designation.attendance, pc.getCourse().getDivisionId(), courseId, semester.getYear());
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
                              @RequestParam(defaultValue = "0", required=false) int year,
                              @RequestParam(defaultValue = "0", required=false) int semester) {
        ProfessorCourse firstCourse = null;


        List<ProfessorCourse> courseList;
        if(semester == 0 && year == 0) {
            courseList = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();
            searchable.setYear(year);
            searchable.setSemester(semester);
            courseList = professorCourseMapper.findByYearSemesterDivision(searchable);


            for(ProfessorCourse course: courseList) {
                firstCourse = course;
                break;
            }
        }


        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("profCourseList", courseList);
        return "role/professor/inquiryCourse/courseTable";
    }

    @RequestMapping("/classProgress/inquiryCourse/courseDetail")
    public String inCourseDetail(Model model, @RequestParam int courseId, @RequestParam(defaultValue = "false", required=false) String print) {
        ProfessorCourse pc = professorCourseMapper.findOne(courseId);
        model.addAttribute("pc", pc);
        LectureFundamentals lectureFundamentals = lectureFundamentalsMapper.findByProfCourseId(pc.getId());
        model.addAttribute("lectureFundamentals", lectureFundamentals == null ? new LectureFundamentals() : lectureFundamentals);
        ProfLectureMethod profLectureMethod = profLectureMethodMapper.findByProfCourseId(pc.getId());
        model.addAttribute("profLectureMethod", profLectureMethod == null ? new ProfLectureMethod() : profLectureMethod);
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
        if(print.equals("true"))
            return "role/common/syllabus/courseDetailForPrint";
        return "role/professor/inquiryCourse/courseDetail";
    }

    @RequestMapping("/classProgress/syllabus")
    public String syllabus(Model model, @RequestParam(required=false) String result) {

        model.addAttribute("yearList", getYearList());
        model.addAttribute("result", result);
        return "role/professor/syllabus/syllabus";
    }

    @RequestMapping("/classProgress/syllabus/courseDetail")
    public String courseDetail(Model model, @RequestParam int profCourseId, @RequestParam(defaultValue = "false", required=false) String print) {
        ProfessorCourse pc = professorCourseMapper.findOne(profCourseId);
        model.addAttribute("pc", pc);
        LectureFundamentals lectureFundamentals = lectureFundamentalsMapper.findByProfCourseId(pc.getId());
        model.addAttribute("lectureFundamentals", lectureFundamentals == null ? new LectureFundamentals() : lectureFundamentals);
        ProfLectureMethod profLectureMethod = profLectureMethodMapper.findByProfCourseId(pc.getId());
        model.addAttribute("profLectureMethod", profLectureMethod == null ? new ProfLectureMethod() : profLectureMethod);
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

        if(print.equals("true"))
            return "role/common/syllabus/courseDetailForPrint";
        return "role/professor/syllabus/courseDetail";
    }

    @RequestMapping(value = "/classProgress/syllabus/courseDetail/lectureFundamentals", method = RequestMethod.POST)
    @ResponseBody
    public String lectureFundamentals(@RequestParam int profCourseId, @ModelAttribute LectureFundamentals lectureFundamentals) {

        User user = User.current();
        lectureFundamentals.setUserId(user.getId());


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
            lectureFundamentalsMapper.insert(lectureFundamentals);
        } else {
            lectureFundamentalsMapper.update(lectureFundamentals);
        }
        return "success";
    }

    @RequestMapping(value = "/classProgress/syllabus/courseDetail/profLectureMethod", method = RequestMethod.POST)
    @ResponseBody
    public String profLectureMethod(@RequestParam int profCourseId, @ModelAttribute ProfLectureMethod profLectureMethod) {
        User user = User.current();
        profLectureMethod.setUserId(user.getId());
        String[] lectureMethodCheckbox = profLectureMethod.getLectureMethodCheckbox();
        String lectureMethodCheckboxStr = checkboxToStr(lectureMethodCheckbox);
        profLectureMethod.setLectureMethods(lectureMethodCheckboxStr);

        String[] evaluationMethodCheckbox = profLectureMethod.getEvaluationMethodCheckbox();
        String evaluationMethodCheckboxStr = checkboxToStr(evaluationMethodCheckbox);
        profLectureMethod.setEvaluationMethods(evaluationMethodCheckboxStr);

        String[] educationalMediumCheckbox = profLectureMethod.getEducationalMediumCheckbox();
        String educationalMediumCheckboxStr = checkboxToStr(educationalMediumCheckbox);
        profLectureMethod.setEducationalMediums(educationalMediumCheckboxStr);

        String[] equipmentCheckbox = profLectureMethod.getEquipmentCheckbox();
        String equipmentCheckboxStr = checkboxToStr(equipmentCheckbox);
        profLectureMethod.setEquipments(equipmentCheckboxStr);



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
        if (lectureContents.getId() == 0) {
            lectureContentsMapper.insert(lectureContents);
        } else {
            lectureContentsMapper.update(lectureContents);
        }
        return "success";
    }

    @RequestMapping("/classProgress/syllabus/courseTable")
    public String syllabusCourseTable(Model model,
                                     @RequestParam(defaultValue = "0", required=false) int year,
                                     @RequestParam(defaultValue = "0", required=false) int semester) {

        ProfessorCourse firstCourse = null;

        List<ProfessorCourse> courseList;
        if(year == 0 && semester == 0) {
            courseList = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();
            searchable.setYear(year);
            searchable.setSemester(semester);
            searchable.setAdvisor(User.current().getId());
            System.out.println("searchable = " + searchable);

            courseList = professorCourseMapper.findByYearSemesterDivisionProfId(searchable);

            for(ProfessorCourse course: courseList) {
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
                                      @RequestParam(defaultValue = "0", required=false) int year,
                                      @RequestParam(defaultValue = "0", required=false) int semester) {

        ProfessorCourse firstCourse = null;
        List<ProfessorCourse> courseList;
        if(year == 0 && semester == 0) {
            courseList = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();
            searchable.setYear(year);
            searchable.setSemester(semester);
            searchable.setAdvisor(User.current().getId());

            courseList = professorCourseMapper.findByYearSemesterDivisionProfId(searchable);

            for(ProfessorCourse course: courseList) {
                firstCourse = course;
                break;
            }
        }



        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("courseList", courseList);
        return "role/professor/classAssessment/courseTable";
    }

    @RequestMapping("/classProgress/classAssessment/courseDetail")
    public String classAssessmentCourseDetail(Model model, @RequestParam int courseId, @RequestParam(defaultValue = "false", required=false) String print) {
        ProfessorCourse pc = professorCourseMapper.findOne(courseId);
        model.addAttribute("pc", pc);
        List<Assessment> assessments = assessmentMapper.findByProfCourseId(courseId);
        model.addAttribute("assessments", assessments);
        List<AssessmentFactor> assessmentFactors = assessmentFactorMapper.findByCourseId(pc.getCourseId());
        model.addAttribute("assessmentFactors", assessmentFactors);
        MenuAccess menuAccess = menuAccessMapper.findOne();
        model.addAttribute("menuAccess", menuAccess);
        if(print.equals("true"))
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
        StudentCourse sc = studentCourseMapper.findOne(pk);
        String result = Integer.toString(pk);

        switch (name) {
            default:
                SystemUtil.setObjectFieldValue(sc, name, value);
        }
        int total = sc.getScoreAssignment() + sc.getScoreAttendance() + sc.getScoreMid() + sc.getScoreFinal() + sc.getScoreOptions();
        sc.setScoreTotal(total);
        studentCourseMapper.update(sc);

        return result + "_" + total;
    }

    @RequestMapping("/classProgress/registerGrade/courseTable")
    public String registerGradeCourseTable(Model model,
                                      @RequestParam(defaultValue = "0", required=false) int year,
                                      @RequestParam(defaultValue = "0", required=false) int semester) {


        Searchable searchable = new Searchable();
        searchable.setYear(year);
        searchable.setSemester(semester);
        searchable.setAdvisor(User.current().getId());
        System.out.println("searchable = " + searchable);

        List<ProfessorCourse> courseList = professorCourseMapper.findByYearSemesterDivisionProfId(searchable);
        ProfessorCourse firstCourse = null;
        for(ProfessorCourse course: courseList) {
            firstCourse = course;
            break;
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("courseList", courseList);
        return "role/professor/registerGrade/courseTable";
    }

    @RequestMapping("/classProgress/registerGrade/courseDetail")
    public String registerGradeCourseDetail(Model model, @RequestParam int courseId) {
        ProfessorCourse pc = professorCourseMapper.findOne(courseId);
        model.addAttribute("pc", pc);

        LectureFundamentals lectureFundamentals = lectureFundamentalsMapper.findByProfCourseId(courseId);
        model.addAttribute("lectureFundamentals", lectureFundamentals);

        List<StudentCourse> studentCourses = studentCourseMapper.findByProfCourseId(pc.getId());
        model.addAttribute("studentCourses", studentCourses);
        MenuAccess menuAccess = menuAccessMapper.findOne();
        model.addAttribute("menuAccess", menuAccess);
        return "role/professor/registerGrade/courseDetail";
    }

    @RequestMapping("/classProgress/registerGrade/courseDetailForPrint")
    public String registerGradeCourseDetailForPrint(Model model, @RequestParam int courseId) {
        ProfessorCourse pc = professorCourseMapper.findOne(courseId);
        model.addAttribute("pc", pc);

        LectureFundamentals lectureFundamentals = lectureFundamentalsMapper.findByProfCourseId(courseId);
        model.addAttribute("lectureFundamentals", lectureFundamentals);

        List<StudentCourse> studentCourses = studentCourseMapper.findByProfCourseId(pc.getId());
        model.addAttribute("studentCourses", studentCourses);

        return "role/professor/registerGrade/courseDetailForPrint";
    }

    @RequestMapping(value = "/classProgress/registerGrade/courseDetail", method = RequestMethod.POST)
    @ResponseBody
    public Boolean registerGradeCourseDetail(@RequestParam int courseId) {
        ProfessorCourse pc = professorCourseMapper.findOne(courseId);
        LectureFundamentals lectureFundamentals = lectureFundamentalsMapper.findByProfCourseId(courseId);

        List<StudentCourse> studentCourses = studentCourseMapper.findByProfCourseId(pc.getId());

        boolean valid = true;
        for(StudentCourse sc: studentCourses) {
            boolean studentValid = true;

            if (sc.getScoreAttendance() > lectureFundamentals.getRateAttendance())
                studentValid = false;
            if (sc.getScoreAssignment() > lectureFundamentals.getRateAssignment())
                studentValid = false;
            if (sc.getScoreMid() > lectureFundamentals.getRateMid())
                studentValid = false;
            if (sc.getScoreFinal() > lectureFundamentals.getRateFinal())
                studentValid = false;
            if (sc.getScoreOptions() > lectureFundamentals.getRateOptional())
                studentValid = false;


            int total = sc.getScoreAssignment() + sc.getScoreAttendance() + sc.getScoreMid() + sc.getScoreFinal() + sc.getScoreOptions();
            if(total > 100) {
                studentValid = false;
            }
            if(!studentValid)
                valid = false;

            sc.setValid(studentValid);
            studentCourseMapper.update(sc);
        }

        return valid;
    }

    @RequestMapping("/classProgress/registerGrade/ratioDetail")
    public String registerGradeRatioDetail(Model model, @RequestParam int courseId) {
        ProfessorCourse pc = professorCourseMapper.findOne(courseId);
        model.addAttribute("pc", pc);

        LectureFundamentals lectureFundamentals = lectureFundamentalsMapper.findByProfCourseId(courseId);
        model.addAttribute("lectureFundamentals", lectureFundamentals);

        List<StudentCourse> studentCourses = studentCourseMapper.findByProfCourseId(pc.getId());
        model.addAttribute("studentCourses", studentCourses);

        return "role/professor/registerGrade/ratioDetail";
    }

    @RequestMapping("/classProgress/cqiReport")
    public String cqiReport(Model model) {

        model.addAttribute("yearList", getYearList());

        return "role/professor/cqiReport/cqiReport";
    }

    @RequestMapping("/classProgress/cqiReport/courseTable")
    public String cqiReportCourseTable(Model model,
                                      @RequestParam(defaultValue = "0", required=false) int year,
                                      @RequestParam(defaultValue = "0", required=false) int semester) {


        Searchable searchable = new Searchable();
        searchable.setYear(year);
        searchable.setSemester(semester);
        searchable.setAdvisor(User.current().getId());

        List<ProfessorCourse> courseList = professorCourseMapper.findByYearSemesterDivisionProfId(searchable);
        ProfessorCourse firstCourse = null;
        for(ProfessorCourse course: courseList) {
            firstCourse = course;
            break;
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("courseList", courseList);
        return "role/professor/cqiReport/courseTable";
    }



    @RequestMapping("/classProgress/cqiReport/courseDetail")
    public String cqiReportCourseDetail(Model model, @RequestParam int courseId, @RequestParam(defaultValue = "false", required=false) String print) {
        MenuAccess menuAccess = menuAccessMapper.findOne();
        model.addAttribute("menuAccess", menuAccess);

        ProfessorCourse pc = professorCourseMapper.findOne(courseId);
        List<Assessment> assessmentList = assessmentMapper.findByProfCourseId(pc.getId());
        pc.setAssessmentList(assessmentList);

        model.addAttribute("pc", pc);
        Searchable s = new Searchable();
        s.setCourseId(pc.getCourseId());
        s.setYear(pc.getSemester().getYear());
        List<ProfessorCourse> professorCourseList = professorCourseMapper.findByYearSemesterCourseId(s);
        for(ProfessorCourse professorCourse: professorCourseList) {
            List<Assessment> assessments = assessmentMapper.findByProfCourseId(professorCourse.getId());
            professorCourse.setAssessmentList(assessments);
            List<StudentCourse> studentCourseList = studentCourseMapper.findByProfCourseIdValid(professorCourse.getId());
            professorCourse.setStudentCourseList(studentCourseList);
        }
        model.addAttribute("professorCourseList", professorCourseList);

        int currentYear = pc.getSemester().getYear();
        model.addAttribute("currentYear", currentYear);
        LectureFundamentals lectureFundamentals = lectureFundamentalsMapper.findByProfCourseId(pc.getId());
        model.addAttribute("lectureFundamentals", lectureFundamentals);
        Map<Integer, Double> averageAssignedMap = new LinkedHashMap<>();
        Map<Integer, Cqi> cqiMap = new LinkedHashMap<>();
        for(int i=(currentYear - 2); i<currentYear; i++) {
            Cqi cqi = cqiMapper.findByYearCourseIdDivide(i, pc.getCourseId(), pc.getDivide());

            if (cqi == null) {
                cqi = new Cqi();
            }
            cqiMap.put(i, cqi);
            if(i == currentYear - 1) {
                model.addAttribute("prevCqi", cqi);
            }
        }
        model.addAttribute("cqiMap", cqiMap);

        Cqi cqi = cqiMapper.findByProfCourseId(pc.getId());
        if(cqi == null) {
            cqi = new Cqi();
            cqi.setSemesterId(pc.getSemesterId());
            cqi.setCourseId(pc.getCourseId());
            cqi.setUserId(User.current().getId());
            cqi.setDivide(pc.getDivide());
            cqi.setProfCourseId(pc.getId());
        }
        model.addAttribute("cqi", cqi);

        for(int i=(currentYear - 2); i<=currentYear; i++) {

            Searchable searchable = new Searchable();
            searchable.setCourseId(pc.getCourseId());
            searchable.setYear(i);
            List<ProfessorCourse> professorCourses = professorCourseMapper.findByYearSemesterCourseId(searchable);

            double avg;
            int total = 0;

            for(ProfessorCourse p: professorCourses) {
                total += p.getNumStudent();
            }
            if(total == 0 || professorCourses.size() == 0)
                avg = 0.0;
            else {
                avg = (double)total / (double)professorCourses.size();
            }

            averageAssignedMap.put(i, avg);
        }
        model.addAttribute("averageAssignedMap", averageAssignedMap);
        List<AssessmentFactor> assessmentFactors = assessmentFactorMapper.findByCourseId(courseId);
        model.addAttribute("assessmentFactors", assessmentFactors);

        if(print.equals("true"))
            return "role/common/cqi/courseDetailForPrint";
        return "role/professor/cqiReport/courseDetail";
    }

    @RequestMapping(value = "/classProgress/cqiReport/courseDetail", method = RequestMethod.POST)
    @ResponseBody
    public String cqiReportCourseDetail(@ModelAttribute("cqi") Cqi cqi, @RequestParam int courseId) {

        System.out.println("cqi = " + cqi);
        if(cqi.getId() == 0) {
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
    public String graduationResearchPlanStudentTable(Model model, @RequestParam(required=false, defaultValue = "0") int year) {


        GraduationResearchPlan firstOne = null;
        List<GraduationResearchPlan> plans;
        if(year == 0) {
            plans = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();

            searchable.setYear(year);
            searchable.setAdvisor(User.current().getId());
            plans = graduationResearchPlanMapper.findBySearchable(searchable);


            for(GraduationResearchPlan graduationResearchPlan : plans) {
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
        if(sArray == null)
            return str;

        for(int i=0; i<sArray.length; i++) {
            str += sArray[i];
            if (i < sArray.length - 1)
                str += ",";
        }
        return str;
    }



}
