package koreatech.cse.controller.role;

import koreatech.cse.domain.Searchable;
import koreatech.cse.domain.User;
import koreatech.cse.domain.role.professor.Counseling;
import koreatech.cse.domain.role.professor.LectureFundamentals;
import koreatech.cse.domain.univ.Course;
import koreatech.cse.domain.univ.Division;
import koreatech.cse.domain.univ.Major;
import koreatech.cse.repository.*;
import koreatech.cse.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes({"lectureFundamentals"})
@PreAuthorize("hasRole('ROLE_PROFESSOR')")
@RequestMapping("/professor")
public class ProfessorController {
    @Inject
    private UserMapper userMapper;
    @Inject
    private DivisionMapper divisionMapper;
    @Inject
    private MajorMapper majorMapper;
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




    @RequestMapping("/studentGuidance/studentLookup")
    public String studentLookup(Model model) {

        List<Division> divisions = divisionMapper.findAll();
        List<Major> majors = majorMapper.findAll();

        model.addAttribute("divisions", divisions);
        model.addAttribute("majors", majors);
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
            userList = userMapper.findByStudentLookup(searchable);


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
    public String counseling(Model model) {
        model.addAttribute("yearList", getYearList());

        return "role/professor/counseling/counseling";
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
                System.out.println("counseling = " + counseling);
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
        System.out.println("year = " + year);
        if(year == 0) {
            userList = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();

            searchable.setYear(year);
            userList = userMapper.findByStudentLookup(searchable);


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
        LectureFundamentals lectureFundamentals = lectureFundamentalsMapper.findByCourseId(courseId);
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
    public String courseDetail(Model model, @RequestParam int courseId) {
        Course course = courseMapper.findOne(courseId);
        model.addAttribute("course", course);
        LectureFundamentals lectureFundamentals = lectureFundamentalsMapper.findByCourseId(courseId);
        model.addAttribute("lectureFundamentals", lectureFundamentals == null ? new LectureFundamentals() : lectureFundamentals);
        return "role/professor/syllabus/courseDetail";
    }

    @RequestMapping(value = "/classProgress/syllabus/courseDetail", method = RequestMethod.POST)
    @ResponseBody
    public String syllabusCourseDetail(@RequestParam int courseId, @ModelAttribute LectureFundamentals lectureFundamentals) {

        if (lectureFundamentals.getId() == 0) {
            lectureFundamentalsMapper.insert(lectureFundamentals);
        } else {
            lectureFundamentalsMapper.update(lectureFundamentals);
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
        searchable.setUserId(User.current().getId());

        List<Course> courseList = courseMapper.findByYearSemesterDivisionProfId(searchable);
        Course firstCourse = null;
        for(Course course: courseList) {
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
        LectureFundamentals lectureFundamentals = lectureFundamentalsMapper.findByCourseId(courseId);
        model.addAttribute("lectureFundamentals", lectureFundamentals == null ? new LectureFundamentals() : lectureFundamentals);
        return "role/professor/classAssessment/courseDetail";
    }

    @RequestMapping("/classProgress/registerGrade")
    public String registerGrade(Model model) {

        model.addAttribute("yearList", getYearList());

        return "role/professor/registerGrade/registerGrade";
    }

    @RequestMapping("/classProgress/registerGrade/courseTable")
    public String registerGradeCourseTable(Model model,
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
        return "role/professor/registerGrade/courseTable";
    }

    @RequestMapping("/classProgress/registerGrade/courseDetail")
    public String registerGradeCourseDetail(Model model, @RequestParam int courseId) {
        Course course = courseMapper.findOne(courseId);
        model.addAttribute("course", course);

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
            userList = userMapper.findByStudentLookup(searchable);


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

    @RequestMapping("/classProgress/makeupClass")
    public String makeupClass(Model model) {

        model.addAttribute("yearList", getYearList());

        return "role/professor/makeupClass/makeupClass";
    }

    @RequestMapping("/classProgress/makeupClass/courseTable")
    public String courseTable(Model model,
                               @RequestParam(defaultValue = "0", required=false) int year,
                               @RequestParam(defaultValue = "0", required=false) int semester) {

        Searchable searchable = new Searchable();
        searchable.setYear(year);
        searchable.setSemester(semester);

        List<Course> courseList = courseMapper.findByMakeupClass(searchable);
        Course firstCourse = null;
        for(Course course: courseList) {
            firstCourse = course;
            break;
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("courseList", courseList);
        return "role/professor/makeupClass/courseTable";
    }

    @RequestMapping("/classProgress/makeupClass/courseDetail")
    public String makeupClassCourseDetail(Model model, @RequestParam int courseId) {
        Course course = courseMapper.findOne(courseId);
        model.addAttribute("course", course);

        return "role/professor/makeupClass/courseDetail";
    }

    private List<Integer> getYearList() {
        return semesterMapper.findYears();
    }

}
