package koreatech.cse.controller.role;

import koreatech.cse.domain.Searchable;
import koreatech.cse.domain.User;
import koreatech.cse.domain.role.professor.ProfessorCourse;
import koreatech.cse.domain.univ.Course;
import koreatech.cse.domain.univ.Division;
import koreatech.cse.domain.univ.Major;
import koreatech.cse.repository.*;
import koreatech.cse.service.UserService;
import org.joda.time.DateTime;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Controller
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



    @RequestMapping("/studentGuidance/studentLookup")
    public String studentLookup(Model model) {

        List<Division> divisions = divisionMapper.findAll();
        List<Major> majors = majorMapper.findAll();

        model.addAttribute("divisions", divisions);
        model.addAttribute("majors", majors);
        return "role/professor/student-lookup/student-lookup";
    }

    @RequestMapping("/studentGuidance/studentLookup/studentTable")
    public String studentTable(Model model, @RequestParam(required=false) String number,
                               @RequestParam(required=false) String name,
                               @RequestParam(defaultValue = "0", required=false) int division,
                               @RequestParam(defaultValue = "0", required=false) int major) {

        Searchable searchable = new Searchable();
        searchable.setNumber(number);
        searchable.setName(name);
        searchable.setDivision(division);
        searchable.setMajor(major);
        List<User> userList = userMapper.findByStudentLookup(searchable);
        model.addAttribute("userList", userList);
        User firstUser = null;
        for(User user: userList) {
            firstUser = user;
            break;
        }

        model.addAttribute("firstUser", firstUser);
        return "role/professor/student-lookup/student-table";
    }

    @RequestMapping("/studentGuidance/studentLookup/studentDetail")
    public String studentDetail(Model model, @RequestParam int studentId) {
        User studentUser = userMapper.findOne(studentId);
        model.addAttribute("studentUser", studentUser);
        return "role/professor/student-lookup/student-detail";
    }

    @RequestMapping("/studentGuidance/counseling")
    public String counseling(Model model) {
        List<Division> divisions = divisionMapper.findAll();
        List<Major> majors = majorMapper.findAll();

        model.addAttribute("divisions", divisions);
        model.addAttribute("majors", majors);

        return "role/professor/counseling/counseling";
    }



    @RequestMapping("/classProgress/attendance")
    public String attendance(Model model) {

        DateTime dt = new DateTime();
        int currentYear = dt.getYear();
        List<Integer> yearList = new ArrayList<>();
        for(int i=currentYear; i>=(currentYear - 10); i--) {
            yearList.add(i);
        }

        model.addAttribute("currentYear", currentYear);
        model.addAttribute("yearList", yearList);

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
        return "role/professor/attendance/course-table";
    }

    @RequestMapping("/classProgress/inquiryCourse")
    public String inquiryCourse(Model model) {

        DateTime dt = new DateTime();
        int currentYear = dt.getYear();
        List<Integer> yearList = new ArrayList<>();
        for(int i=currentYear; i>=(currentYear - 10); i--) {
            yearList.add(i);
        }

        model.addAttribute("currentYear", currentYear);
        model.addAttribute("yearList", yearList);
        List<Division> divisions = divisionMapper.findAll();

        model.addAttribute("divisions", divisions);
        return "role/professor/inquiry-course/inquiry-course";
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
        return "role/professor/inquiry-course/course-table";
    }

    @RequestMapping("/classProgress/syllabus")
    public String syllabus(Model model) {

        DateTime dt = new DateTime();
        int currentYear = dt.getYear();
        List<Integer> yearList = new ArrayList<>();
        for(int i=currentYear; i>=(currentYear - 10); i--) {
            yearList.add(i);
        }

        model.addAttribute("currentYear", currentYear);
        model.addAttribute("yearList", yearList);

        return "role/professor/syllabus/syllabus";
    }

    @RequestMapping("/classProgress/syllabus/courseDetail")
    public String courseDetail(Model model, @RequestParam int courseId) {
        Course course = courseMapper.findOne(courseId);
        model.addAttribute("course", course);
        return "role/professor/syllabus/course-detail";
    }

    @RequestMapping("/classProgress/syllabus/courseTable")
    public String syllabusCourseTable(Model model,
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
        return "role/professor/syllabus/course-table";
    }


    @RequestMapping("/classProgress/makeupClass")
    public String makeupClass(Model model) {

        DateTime dt = new DateTime();
        int currentYear = dt.getYear();
        List<Integer> yearList = new ArrayList<>();
        for(int i=currentYear; i>=(currentYear - 10); i--) {
            yearList.add(i);
        }

        model.addAttribute("currentYear", currentYear);
        model.addAttribute("yearList", yearList);

        return "role/professor/makeup-class/makeup-class";
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
        return "role/professor/makeup-class/course-table";
    }

}
