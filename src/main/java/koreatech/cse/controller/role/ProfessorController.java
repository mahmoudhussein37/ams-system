package koreatech.cse.controller.role;

import koreatech.cse.domain.Searchable;
import koreatech.cse.domain.User;
import koreatech.cse.domain.role.professor.*;
import koreatech.cse.domain.role.student.StudentCourse;
import koreatech.cse.domain.univ.*;
import koreatech.cse.repository.*;
import koreatech.cse.service.UserService;
import koreatech.cse.util.SystemUtil;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@SessionAttributes({"lectureFundamentals", "counseling", "lectureContents", "profLectureMethod"})
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
    public String studentDetail(Model model, @RequestParam int studentId) {
        User studentUser = userMapper.findOne(studentId);
        model.addAttribute("studentUser", studentUser);
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
    public String counselingStudentDetail(Model model, @RequestParam int counselingId) {
        Counseling counseling = counselingMapper.findOne(counselingId);
        model.addAttribute("counseling", counseling);
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

        model.addAttribute("yearList", getYearList());

        return "role/professor/attendance/attendance";
    }

    @RequestMapping("/classProgress/attendance/courseTable")
    public String professorCourseTable(Model model,
                              @RequestParam(defaultValue = "0", required=false) int year,
                              @RequestParam(defaultValue = "0", required=false) int semester) {

        Searchable searchable = new Searchable();
        searchable.setYear(year);
        searchable.setSemester(semester);

        List<Course> courseList = courseMapper.findByYearSemesterDivision(searchable);
        Course firstCourse = null;
        for(Course course: courseList) {
            firstCourse = course;
            break;
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("courseList", courseList);
        return "role/professor/attendance/courseTable";
    }

    @RequestMapping("/classProgress/attendance/courseDetail")
    public String attendanceCourseDetail(Model model, @RequestParam int courseId) {
        Course course = courseMapper.findOne(courseId);
        model.addAttribute("course", course);

        return "role/professor/attendance/courseDetail";
    }

    @RequestMapping("/classProgress/inquiryCourse")
    public String inquiryCourse(Model model) {

        model.addAttribute("yearList", getYearList());
        List<Division> divisions = divisionMapper.findAll();

        model.addAttribute("divisions", divisions);
        return "role/professor/inquiryCourse/inquiryCourse";
    }

    @RequestMapping("/classProgress/inquiryCourse/courseTable")
    public String inquiryCourseTable(Model model,
                              @RequestParam(defaultValue = "0", required=false) int year,
                              @RequestParam(defaultValue = "0", required=false) int semester,
                                     @RequestParam(defaultValue = "0", required=false) int division) {

        Searchable searchable = new Searchable();
        searchable.setYear(year);
        searchable.setSemester(semester);
        searchable.setDivision(division);

        List<Course> courseList = courseMapper.findByYearSemesterDivision(searchable);
        Course firstCourse = null;
        for(Course course: courseList) {
            firstCourse = course;
            break;
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("courseList", courseList);
        return "role/professor/inquiryCourse/courseTable";
    }

    @RequestMapping("/classProgress/inquiryCourse/courseDetail")
    public String inCourseDetail(Model model, @RequestParam int courseId) {
        Course course = courseMapper.findOne(courseId);
        model.addAttribute("course", course);
        LectureFundamentals lectureFundamentals = lectureFundamentalsMapper.findByProfCourseId(courseId);
        model.addAttribute("lectureFundamentals", lectureFundamentals == null ? new LectureFundamentals() : lectureFundamentals);
        return "role/professor/inquiryCourse/courseDetail";
    }

    @RequestMapping("/classProgress/syllabus")
    public String syllabus(Model model, @RequestParam(required=false) String result) {

        model.addAttribute("yearList", getYearList());
        model.addAttribute("result", result);
        return "role/professor/syllabus/syllabus";
    }

    @RequestMapping("/classProgress/syllabus/courseDetail")
    public String courseDetail(Model model, @RequestParam int profCourseId) {
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

        List<EducationalMedium> educationalMediums = educationalMediumMapper.findAll();
        model.addAttribute("educationalMediums", educationalMediums);

        List<EvaluationMethod> evaluationMethods = evaluationMethodMapper.findAll();
        model.addAttribute("evaluationMethods", evaluationMethods);

        List<Equipment> equipments = equipmentMapper.findAll();
        model.addAttribute("equipments", equipments);

        MenuAccess menuAccess = menuAccessMapper.findOne();
        model.addAttribute("menuAccess", menuAccess);


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


        Searchable searchable = new Searchable();
        searchable.setYear(year);
        searchable.setSemester(semester);
        searchable.setUserId(User.current().getId());

        List<Course> courseList = courseMapper.findByYearSemesterDivisionProfId(searchable);
        Course firstCourse = null;
        for(Course course: courseList) {
            firstCourse = course;
            break;
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("courseList", courseList);
        return "role/professor/classAssessment/courseTable";
    }

    @RequestMapping("/classProgress/classAssessment/courseDetail")
    public String classAssessmentCourseDetail(Model model, @RequestParam int courseId) {
        Course course = courseMapper.findOne(courseId);
        model.addAttribute("course", course);
        LectureFundamentals lectureFundamentals = lectureFundamentalsMapper.findByProfCourseId(courseId);
        model.addAttribute("lectureFundamentals", lectureFundamentals == null ? new LectureFundamentals() : lectureFundamentals);
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

        return "role/professor/registerGrade/courseDetail";
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
        searchable.setUserId(User.current().getId());

        List<Course> courseList = courseMapper.findByYearSemesterDivisionProfId(searchable);
        Course firstCourse = null;
        for(Course course: courseList) {
            firstCourse = course;
            break;
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("courseList", courseList);
        return "role/professor/cqiReport/courseTable";
    }

    @RequestMapping("/classProgress/cqiReport/courseDetail")
    public String cqiReportCourseDetail(Model model, @RequestParam int courseId) {
        Course course = courseMapper.findOne(courseId);
        model.addAttribute("course", course);

        return "role/professor/cqiReport/courseDetail";
    }

    @RequestMapping("/classProgress/graduationResearchPlan")
    public String graduationResearchPlan(Model model) {

        model.addAttribute("yearList", getYearList());

        return "role/professor/graduationResearchPlan/graduationResearchPlan";
    }

    @RequestMapping("/classProgress/graduationResearchPlan/studentTable")
    public String graduationResearchPlanStudentTable(Model model, @RequestParam(required=false, defaultValue = "0") int year, @RequestParam(defaultValue = "0", required=false) int semester) {
        User firstUser = null;
        List<User> userList;
        System.out.println("year = " + year);
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
        return "role/professor/graduationResearchPlan/studentTable";
    }

    @RequestMapping("/classProgress/graduationResearchPlan/studentDetail")
    public String graduationResearchPlanStudentDetail(Model model, @RequestParam int studentId) {
        User studentUser = userMapper.findOne(studentId);
        model.addAttribute("studentUser", studentUser);
        return "role/professor/graduationResearchPlan/studentDetail";
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
