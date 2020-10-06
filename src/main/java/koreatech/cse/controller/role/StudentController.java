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
@PreAuthorize("hasRole('ROLE_STUDENT')")
@RequestMapping("/student")
public class StudentController {
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



    @RequestMapping("/courseGuide/yearlyCurriculum")
    public String yearlyCurriculum(Model model) {

        List<Division> divisions = divisionMapper.findAll();

        model.addAttribute("divisions", divisions);
        model.addAttribute("yearList", getYearList());
        return "role/student/yearlyCurriculum/yearlyCurriculum";
    }

    @RequestMapping("/courseGuide/courseInfo")
    public String courseInfo(Model model) {


        return "role/student/courseInfo/courseInfo";
    }

    @RequestMapping("/courseGuide/alternative")
    public String alternative(Model model) {
        return "role/student/alternative/alternative";
    }

    @RequestMapping("/register/basic")
    public String basic(Model model) {
        return "role/student/basic/basic";
    }

    @RequestMapping("/register/basic/studentDetail")
    public String studentDetail(Model model) {
        User studentUser = User.current();
        model.addAttribute("studentUser", studentUser);
        return "role/student/basic/studentDetail";
    }


    @RequestMapping("/classInformation/syllabus")
    public String syllabus(Model model) {

        List<Division> divisions = divisionMapper.findAll();

        model.addAttribute("divisions", divisions);
        model.addAttribute("yearList", getYearList());
        return "role/student/syllabus/syllabus";
    }

    @RequestMapping("/classInformation/enrolment")
    public String enrolment(Model model) {

        List<Division> divisions = divisionMapper.findAll();

        model.addAttribute("divisions", divisions);
        model.addAttribute("yearList", getYearList());
        return "role/student/enrolment/enrolment";
    }

    @RequestMapping("/classInformation/counselingCourseEnrolment")
    public String counselingCourseEnrolment(Model model) {
        List<Division> divisions = divisionMapper.findAll();

        model.addAttribute("divisions", divisions);
        model.addAttribute("yearList", getYearList());
        return "role/student/counselingCourseEnrolment/counselingCourseEnrolment";
    }

    @RequestMapping("/classInformation/assessment")
    public String assessment(Model model) {

        model.addAttribute("yearList", getYearList());
        return "role/student/assessment/assessment";
    }

    @RequestMapping("/grades/inquiryGrade")
    public String inquiryGrade(Model model) {

        return "role/student/inquiryGrade/inquiryGrade";
    }

    @RequestMapping("/grades/inquiryGrade/gradeDetail")
    public String gradeDetail(Model model) {
        User studentUser = User.current();
        model.addAttribute("studentUser", studentUser);
        return "role/student/inquiryGrade/gradeDetail";
    }

    @RequestMapping("/graduation/graduationRequirements")
    public String graduationRequirements(Model model) {

        return "role/student/graduationRequirements/graduationRequirements";
    }

    @RequestMapping("/graduation/graduationRequirements/gradeDetail")
    public String requiredGradeDetail(Model model) {
        User studentUser = User.current();
        model.addAttribute("studentUser", studentUser);
        return "role/student/graduationRequirements/gradeDetail";
    }

    @RequestMapping("/graduation/graduationResearchPlan")
    public String graduationResearchPlan(Model model) {
        User studentUser = User.current();
        model.addAttribute("studentUser", studentUser);
        return "role/student/graduationResearchPlan/graduationResearchPlan";
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
