package koreatech.cse.controller.role;

import koreatech.cse.domain.Searchable;
import koreatech.cse.domain.User;
import koreatech.cse.domain.role.professor.LectureFundamentals;
import koreatech.cse.domain.role.professor.ProfessorCourse;
import koreatech.cse.domain.univ.Course;
import koreatech.cse.domain.univ.Division;
import koreatech.cse.domain.univ.Major;
import koreatech.cse.repository.*;
import koreatech.cse.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
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
                               @RequestParam(defaultValue = "0", required=false) int division,
                               @RequestParam(defaultValue = "0", required=false) int major) {
        User firstUser = null;
        List<User> userList;
        if(StringUtils.isBlank(number) && StringUtils.isBlank(name) && division == 0 && major == 0) {
            userList = new ArrayList<User>();
        } else {
            Searchable searchable = new Searchable();
            searchable.setNumber(number);
            searchable.setName(name);
            searchable.setDivision(division);
            searchable.setMajor(major);
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
        List<Division> divisions = divisionMapper.findAll();
        List<Major> majors = majorMapper.findAll();

        model.addAttribute("divisions", divisions);
        model.addAttribute("majors", majors);

        return "role/professor/counseling/counseling";
    }

    @RequestMapping("/studentGuidance/coCourseEnrolment")
    public String counselingEnrolment(Model model) {

        model.addAttribute("yearList", getYearList());

        return "role/professor/counselingEnrolment/counselingEnrolment";
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

        List<ProfessorCourse> courseList = professorCourseMapper.findByAttendance(searchable);
        ProfessorCourse firstCourse = null;
        for(ProfessorCourse course: courseList) {
            firstCourse = course;
            break;
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("courseList", courseList);
        return "role/professor/attendance/courseTable";
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

        List<Course> courseList = courseMapper.findByInquiryCourse(searchable);
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

        List<Course> courseList = courseMapper.findBySyllabus(searchable);
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

    @RequestMapping("/classProgress/registerGrade")
    public String registerGrade(Model model) {

        model.addAttribute("yearList", getYearList());

        return "role/professor/registerGrade/registerGrade";
    }

    @RequestMapping("/classProgress/cqiReport")
    public String cqiReport(Model model) {

        model.addAttribute("yearList", getYearList());

        return "role/professor/cqiReport/cqiReport";
    }

    @RequestMapping("/classProgress/graduationResearchPlan")
    public String graduationResearchPlan(Model model) {

        model.addAttribute("yearList", getYearList());

        return "role/professor/graduationResearchPlan/graduationResearchPlan";
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
    
    private List<Integer> getYearList() {
        DateTime dt = new DateTime();
        int currentYear = dt.getYear();
        List<Integer> yearList = new ArrayList<>();
        for(int i=currentYear; i>=(currentYear - 10); i--) {
            yearList.add(i);
        }
        return yearList;
    }

}
