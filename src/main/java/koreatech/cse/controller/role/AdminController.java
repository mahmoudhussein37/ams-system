package koreatech.cse.controller.role;

import koreatech.cse.domain.Contact;
import koreatech.cse.domain.User;
import koreatech.cse.domain.univ.Division;
import koreatech.cse.domain.univ.Major;
import koreatech.cse.repository.*;
import koreatech.cse.service.UserService;
import org.joda.time.DateTime;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("studentUser")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/admin")
public class AdminController {
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



    @RequestMapping("/studentManagement/studentRegistration")
    public String studentRegistration(Model model) {

        List<Division> divisions = divisionMapper.findAll();
        List<Major> majors = majorMapper.findAll();

        model.addAttribute("divisions", divisions);
        model.addAttribute("majors", majors);
        User studentUser = new User();
        Contact contact = new Contact();
        studentUser.setContact(contact);
        model.addAttribute("studentUser", studentUser);
        return "role/admin/studentRegistration/studentRegistration";
    }

    @RequestMapping(value = "/studentManagement/studentRegistration", method = RequestMethod.POST)
    public String basic(@ModelAttribute("studentUser") User studentUser, SessionStatus sessionStatus) {

        studentUser.setEnabled(false);
        studentUser.setConfirm(false);
        userService.register(studentUser);
        sessionStatus.setComplete();

        return "redirect:/admin/studentManagement/studentRegistration";
    }


    @RequestMapping("/studentManagement/studentInformation")
    public String studentInformation(Model model) {
        return "role/admin/studentInformation/studentInformation";
    }

    @RequestMapping("/studentManagement/studentProfile")
    public String studentProfile(Model model) {
        return "role/admin/studentProfile/studentProfile";
    }

    @RequestMapping("/studentManagement/studentCounseling")
    public String studentCounseling(Model model) {
        return "role/admin/studentCounseling/studentCounseling";
    }

    @RequestMapping("/profManagement/profRegistration")
    public String profRegistration(Model model) {
        return "role/admin/profRegistration/profRegistration";
    }

    @RequestMapping("/profManagement/profInformation")
    public String profInformation(Model model) {
        return "role/admin/profInformation/profInformation";
    }

    @RequestMapping("/profManagement/graduationResearch")
    public String graduationResearch(Model model) {
        return "role/admin/graduationResearch/graduationResearch";
    }

    @RequestMapping("/profManagement/studentEnrolment")
    public String studentEnrolment(Model model) {
        return "role/admin/studentEnrolment/studentEnrolment";
    }

    @RequestMapping("/courseManagement/curriculum")
    public String curriculum(Model model) {
        return "role/admin/curriculum/curriculum";
    }

    @RequestMapping("/courseManagement/course")
    public String course(Model model) {
        return "role/admin/course/course";
    }

    @RequestMapping("/courseManagement/alternative")
    public String alternative(Model model) {
        return "role/admin/alternative/alternative";
    }

    @RequestMapping("/courseManagement/cOpen")
    public String cOpen(Model model) {
        return "role/admin/cOpen/cOpen";
    }

    @RequestMapping("/courseManagement/attendance")
    public String attendance(Model model) {
        return "role/admin/attendance/attendance";
    }

    @RequestMapping("/courseManagement/syllabus")
    public String syllabus(Model model) {
        return "role/admin/syllabus/syllabus";
    }

    @RequestMapping("/courseManagement/makeupClass")
    public String makeupClass(Model model) {
        return "role/admin/makeupClass/makeupClass";
    }

    @RequestMapping("/academicManagement/studentGrade")
    public String studentGrade(Model model) {
        return "role/admin/studentGrade/studentGrade";
    }

    @RequestMapping("/academicManagement/graduationCriteria")
    public String graduationCriteria(Model model) {
        return "role/admin/graduationCriteria/graduationCriteria";
    }

    @RequestMapping("/academicManagement/assessmentFactor")
    public String assessmentFactor(Model model) {
        return "role/admin/assessmentFactor/assessmentFactor";
    }

    @RequestMapping("/academicManagement/assessmentResult")
    public String assessmentResult(Model model) {
        return "role/admin/assessmentResult/assessmentResult";
    }

    @RequestMapping("/academicManagement/cqi")
    public String cqi(Model model) {
        return "role/admin/cqi/cqi";
    }

    @RequestMapping("/systemManagement/yearSemester")
    public String yearSemester(Model model) {
        return "role/admin/yearSemester/yearSemester";
    }

    @RequestMapping("/systemManagement/divisionMajor")
    public String divisionMajor(Model model) {
        return "role/admin/divisionMajor/divisionMajor";
    }

    @RequestMapping("/systemManagement/lectureMethod")
    public String lectureMethod(Model model) {
        return "role/admin/lectureMethod/lectureMethod";
    }

    @RequestMapping("/systemManagement/evaluationMethod")
    public String evaluationMethod(Model model) {
        return "role/admin/evaluationMethod/evaluationMethod";
    }

    @RequestMapping("/systemManagement/eduType")
    public String eduType(Model model) {
        return "role/admin/eduType/eduType";
    }

    @RequestMapping("/systemManagement/equipment")
    public String equipment(Model model) {
        return "role/admin/equipment/equipment";
    }

    @RequestMapping("/systemManagement/classroom")
    public String classroom(Model model) {
        return "role/admin/classroom/classroom";
    }

    @RequestMapping("/systemManagement/menu")
    public String menu(Model model) {
        return "role/admin/menu/menu";
    }

    @RequestMapping("/systemManagement/addAdmin")
    public String addAdmin(Model model) {
        return "role/admin/addAdmin/addAdmin";
    }

    @RequestMapping("/systemManagement/errorReport")
    public String errorReport(Model model) {
        return "role/admin/errorReport/errorReport";
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
