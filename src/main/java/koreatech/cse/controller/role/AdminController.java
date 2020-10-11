package koreatech.cse.controller.role;

import koreatech.cse.domain.Contact;
import koreatech.cse.domain.Searchable;
import koreatech.cse.domain.User;
import koreatech.cse.domain.constant.StudentStatus;
import koreatech.cse.domain.univ.Division;
import koreatech.cse.domain.univ.Major;
import koreatech.cse.repository.*;
import koreatech.cse.service.AuthorityService;
import koreatech.cse.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes({"studentUser", "profUser"})
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
    @Inject
    private ContactMapper contactMapper;
    @Inject
    private AuthorityService authorityService;



    @RequestMapping("/studentManagement/studentRegistration")
    public String studentRegistration(Model model, @RequestParam(required=false) String result) {

        List<Division> divisions = divisionMapper.findAll();
        List<Major> majors = majorMapper.findAll();

        model.addAttribute("divisions", divisions);
        model.addAttribute("majors", majors);
        User studentUser = new User();
        Contact contact = new Contact();
        studentUser.setContact(contact);
        model.addAttribute("studentUser", studentUser);
        model.addAttribute("result", result);
        return "role/admin/studentRegistration/studentRegistration";
    }

    @RequestMapping(value = "/studentManagement/studentRegistration", method = RequestMethod.POST)
    public String basic(@ModelAttribute("studentUser") User studentUser, SessionStatus sessionStatus) {

        studentUser.setEnabled(false);
        studentUser.setConfirm(false);
        userService.register(studentUser);
        sessionStatus.setComplete();

        return "redirect:/admin/studentManagement/studentRegistration?result=success";
    }

    @RequestMapping("/studentManagement/studentInformation/studentTable")
    public String studentTable(Model model, @RequestParam(required=false) String number,
                               @RequestParam(required=false) String name,
                               @RequestParam(defaultValue = "0", required=false) int division,
                               @RequestParam(defaultValue = "0", required=false) int major) {
        User firstUser = null;
        List<User> userList;
        if(StringUtils.isBlank(number) && StringUtils.isBlank(name) && division == 0 && major == 0) {
            userList = new ArrayList<>();
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
        return "role/admin/studentInformation/studentTable";
    }

    @RequestMapping("/studentManagement/studentInformation/studentDetail")
    public String studentDetail(Model model, @RequestParam int studentId) {
        User studentUser = userMapper.findOne(studentId);
        model.addAttribute("studentUser", studentUser);
        model.addAttribute("statusList", StudentStatus.values());
        List<Division> divisions = divisionMapper.findAll();
        List<Major> majors = majorMapper.findAll();

        model.addAttribute("divisions", divisions);
        model.addAttribute("majors", majors);

        return "role/admin/studentInformation/studentDetail";
    }

    @RequestMapping(value = "/studentManagement/studentInformation/studentDetail", method = RequestMethod.POST)
    public String studentDetail(@ModelAttribute("studentUser") User studentUser, SessionStatus sessionStatus) {
        System.out.println("studentUser = " + studentUser);

        userMapper.update(studentUser);
        contactMapper.update(studentUser.getContact());
        sessionStatus.setComplete();

        return "redirect:/admin/studentManagement/studentInformation?result=success";
    }

    @RequestMapping("/studentManagement/studentInformation")
    public String studentInformation(Model model, @RequestParam(required=false) String result) {
        List<Division> divisions = divisionMapper.findAll();
        List<Major> majors = majorMapper.findAll();

        model.addAttribute("divisions", divisions);
        model.addAttribute("majors", majors);
        model.addAttribute("result", result);
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
    public String profRegistration(Model model, @RequestParam(required=false) String result) {
        List<Division> divisions = divisionMapper.findAll();
        List<Major> majors = majorMapper.findAll();
        model.addAttribute("divisions", divisions);
        model.addAttribute("majors", majors);
        User profUser = new User();
        Contact contact = new Contact();
        profUser.setContact(contact);
        model.addAttribute("profUser", profUser);
        model.addAttribute("result", result);
        return "role/admin/profRegistration/profRegistration";
    }

    @RequestMapping(value = "/profManagement/profRegistration", method = RequestMethod.POST)
    public String profRegistration(@ModelAttribute("profUser") User profUser, SessionStatus sessionStatus) {
        profUser.setEnabled(false);
        profUser.setConfirm(false);
        userService.register(profUser);
        sessionStatus.setComplete();
        return "redirect:/admin/profManagement/profRegistration?result=success";
    }

    @RequestMapping("/profManagement/profInformation")
    public String profInformation(Model model, @RequestParam(required=false) String result) {

        List<Division> divisions = divisionMapper.findAll();
        List<Major> majors = majorMapper.findAll();

        model.addAttribute("divisions", divisions);
        model.addAttribute("majors", majors);
        model.addAttribute("result", result);
        return "role/admin/profInformation/profInformation";
    }

    @RequestMapping("/profManagement/profInformation/profTable")
    public String profTable(Model model, @RequestParam(required=false) String number,
                               @RequestParam(required=false) String name,
                               @RequestParam(defaultValue = "0", required=false) int division,
                               @RequestParam(defaultValue = "0", required=false) int major) {
        User firstUser = null;
        List<User> userList;
        System.out.println("division = " + division);
        System.out.println("major = " + major);
        System.out.println("number = " + number);
        System.out.println("name = " + name);;
        if(StringUtils.isBlank(number) && StringUtils.isBlank(name) && division == 0 && major == 0) {
            userList = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();
            searchable.setNumber(number);
            searchable.setName(name);
            searchable.setDivision(division);
            searchable.setMajor(major);
            userList = userMapper.findByProfLookup(searchable);


            for(User user: userList) {
                firstUser = user;
                break;
            }
        }

        model.addAttribute("userList", userList);
        model.addAttribute("firstUser", firstUser);
        return "role/admin/profInformation/profTable";
    }

    @RequestMapping("/profManagement/profInformation/profDetail")
    public String profDetail(Model model, @RequestParam int profId) {
        User profUser = userMapper.findOne(profId);
        model.addAttribute("profUser", profUser);
        model.addAttribute("statusList", StudentStatus.values());
        List<Division> divisions = divisionMapper.findAll();
        List<Major> majors = majorMapper.findAll();

        model.addAttribute("divisions", divisions);
        model.addAttribute("majors", majors);

        return "role/admin/profInformation/profDetail";
    }

    @RequestMapping(value = "/profManagement/profInformation/profDetail", method = RequestMethod.POST)
    public String profDetail(@ModelAttribute("profUser") User profUser, SessionStatus sessionStatus) {

        userMapper.update(profUser);
        contactMapper.update(profUser.getContact());
        sessionStatus.setComplete();

        return "redirect:/admin/profManagement/profInformation?result=success";
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
