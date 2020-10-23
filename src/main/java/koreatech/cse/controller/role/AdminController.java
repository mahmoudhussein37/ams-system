package koreatech.cse.controller.role;

import koreatech.cse.domain.Contact;
import koreatech.cse.domain.Searchable;
import koreatech.cse.domain.User;
import koreatech.cse.domain.constant.CompCategory;
import koreatech.cse.domain.constant.StudentStatus;
import koreatech.cse.domain.constant.SubjCategory;
import koreatech.cse.domain.role.professor.Counseling;
import koreatech.cse.domain.role.professor.LectureFundamentals;
import koreatech.cse.domain.role.professor.ProfessorCourse;
import koreatech.cse.domain.univ.Course;
import koreatech.cse.domain.univ.Division;
import koreatech.cse.domain.univ.Major;
import koreatech.cse.repository.*;
import koreatech.cse.service.AuthorityService;
import koreatech.cse.service.UserService;
import koreatech.cse.util.SystemUtil;
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
@SessionAttributes({"studentUser", "profUser", "course", "division", "major"})
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
    @Inject
    private CounselingMapper counselingMapper;



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
        model.addAttribute("statusList", StudentStatus.values());
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

        List<Division> divisions = divisionMapper.findAll();
        List<Major> majors = majorMapper.findAll();

        model.addAttribute("divisions", divisions);
        model.addAttribute("majors", majors);
        return "role/admin/studentProfile/studentProfile";
    }

    @RequestMapping("/studentManagement/studentProfile/studentTable")
    public String studentProfileStudentTable(Model model, @RequestParam(required=false) String number,
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
        return "role/admin/studentProfile/studentTable";
    }

    @RequestMapping("/studentManagement/studentProfile/studentDetail")
    public String studentProfileStudentDetail(Model model, @RequestParam int studentId) {
        User studentUser = userMapper.findOne(studentId);
        model.addAttribute("studentUser", studentUser);
        return "role/admin/studentProfile/studentDetail";
    }

    @RequestMapping("/studentManagement/studentCounseling")
    public String studentCounseling(Model model) {

        model.addAttribute("yearList", getYearList());
        return "role/admin/studentCounseling/studentCounseling";
    }

    @RequestMapping("/studentManagement/studentCounseling/counselingTable")
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
        return "role/admin/studentCounseling/counselingTable";
    }

    @RequestMapping("/studentManagement/studentCounseling/counselingDetail")
    public String counselingStudentDetail(Model model, @RequestParam int counselingId) {
        Counseling counseling = counselingMapper.findOne(counselingId);
        model.addAttribute("counseling", counseling);
        return "role/admin/studentCounseling/counselingDetail";
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

        model.addAttribute("yearList", getYearList());
        return "role/admin/graduationResearch/graduationResearch";
    }

    @RequestMapping("/profManagement/graduationResearch/studentTable")
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
        return "role/admin/graduationResearch/studentTable";
    }

    @RequestMapping("/profManagement/graduationResearch/studentDetail")
    public String graduationResearchPlanStudentDetail(Model model, @RequestParam int studentId) {
        User studentUser = userMapper.findOne(studentId);
        model.addAttribute("studentUser", studentUser);
        return "role/admin/graduationResearch/studentDetail";
    }

    @RequestMapping("/profManagement/studentEnrolment")
    public String studentEnrolment(Model model) {

        List<Division> divisions = divisionMapper.findAll();

        model.addAttribute("divisions", divisions);
        model.addAttribute("yearList", getYearList());

        Searchable searchable = new Searchable();
        List<Course> courseList = courseMapper.findBySyllabus(searchable);
        Course firstCourse = null;
        for(Course course: courseList) {
            firstCourse = course;
            break;
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("courseList", courseList);
        return "role/admin/studentEnrolment/studentEnrolment";
    }

    @RequestMapping("/profManagement/studentEnrolment/courseTable")
    public String enrolmentCourseTable(Model model,
                                       @RequestParam(defaultValue = "0", required=false) int year,
                                       @RequestParam(defaultValue = "0", required=false) int semester) {

        Searchable searchable = new Searchable();
        searchable.setYear(year);
        searchable.setSemester(semester);

        List<Course> courseList = courseMapper.findBySyllabus(searchable);
        Course firstCourse = null;
        for(Course course: courseList) {
            firstCourse = course;
            break;
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("courseList", courseList);
        return "role/admin/studentEnrolment/courseTable";
    }

    @RequestMapping("/courseManagement/curriculum")
    public String curriculum(Model model) {

        List<Division> divisions = divisionMapper.findAll();

        model.addAttribute("divisions", divisions);
        model.addAttribute("yearList", getYearList());
        return "role/admin/curriculum/curriculum";
    }

    @RequestMapping("/courseManagement/curriculum/courseTable")
    public String curriculumTable(Model model,
                                  @RequestParam(defaultValue = "0", required=false) int year,
                                  @RequestParam(defaultValue = "0", required=false) int division) {

        Searchable searchable = new Searchable();
        searchable.setYear(year);
        searchable.setDivision(division);

        List<Course> courseList = courseMapper.findByMakeupClass(searchable);
        Course firstCourse = null;
        for(Course course: courseList) {
            firstCourse = course;
            break;
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("courseList", courseList);
        return "role/admin/curriculum/courseTable";
    }

    @RequestMapping("/courseManagement/course")
    public String course(Model model,  @RequestParam(required=false) String result) {
        List<Division> divisions = divisionMapper.findAll();
        List<Major> majors = majorMapper.findAll();

        model.addAttribute("divisions", divisions);
        model.addAttribute("majors", majors);
        model.addAttribute("yearList", getYearList());
        model.addAttribute("course", new Course());
        model.addAttribute("compCategoryList", CompCategory.values());
        model.addAttribute("subjCategoryList", SubjCategory.values());
        model.addAttribute("result", result);
        return "role/admin/course/course";
    }

    @RequestMapping(value = "/courseManagement/course", method = RequestMethod.POST)
    public String course(@ModelAttribute("course") Course course, SessionStatus sessionStatus) {

        courseMapper.insert(course);

        sessionStatus.setComplete();

        return "redirect:/admin/courseManagement/course?result=success";
    }

    @RequestMapping("/courseManagement/course/courseTable")
    public String courseTable(Model model,
                                       @RequestParam(defaultValue = "0", required=false) int year,
                                       @RequestParam(defaultValue = "0", required=false) int semester,
                              @RequestParam(defaultValue = "0", required=false) int division,
                              @RequestParam(defaultValue = "0", required=false) int major) {
        System.out.println("course table");

        Searchable searchable = new Searchable();
        searchable.setYear(year);
        searchable.setSemester(semester);
        searchable.setDivision(division);
        searchable.setMajor(major);

        List<Course> courseList = courseMapper.findByCourseManagement(searchable);

        Course firstCourse = null;
        for(Course course: courseList) {
            firstCourse = course;
            break;
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("courseList", courseList);
        System.out.println("course table end");
        return "role/admin/course/courseTable";
    }

    @RequestMapping(value = "/courseManagement/courseEditable", method = RequestMethod.POST)
    @ResponseBody
    public Boolean courseEditable(@RequestParam int pk, @RequestParam String name, @RequestParam String value) {
        Course course = courseMapper.findOne(pk);
        System.out.println("name = " + name);
        System.out.println("value = " + value);
        switch (name) {
            default:
                SystemUtil.setObjectFieldValue(course, name, value);
        }
        courseMapper.update(course);

        return true;
    }

    @RequestMapping("/courseManagement/alternative")
    public String alternative(Model model, @RequestParam(required=false) String result) {

        List<Division> divisions = divisionMapper.findAll();
        List<Major> majors = majorMapper.findAll();

        model.addAttribute("divisions", divisions);
        model.addAttribute("majors", majors);
        model.addAttribute("yearList", getYearList());
        model.addAttribute("course", new Course());
        model.addAttribute("compCategoryList", CompCategory.values());
        model.addAttribute("subjCategoryList", SubjCategory.values());
        model.addAttribute("result", result);
        return "role/admin/alternative/alternative";
    }

    @RequestMapping("/courseManagement/alternative/courseTable")
    public String alternativeCourseTable(Model model,
                              @RequestParam(defaultValue = "0", required=false) int year,
                              @RequestParam(defaultValue = "0", required=false) int semester,
                              @RequestParam(defaultValue = "0", required=false) int division,
                              @RequestParam(defaultValue = "0", required=false) int major) {
        System.out.println("course table");

        Searchable searchable = new Searchable();
        searchable.setYear(year);
        searchable.setSemester(semester);
        searchable.setDivision(division);
        searchable.setMajor(major);

        List<Course> courseList = courseMapper.findByCourseManagement(searchable);

        Course firstCourse = null;
        for(Course course: courseList) {
            firstCourse = course;
            break;
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("courseList", courseList);
        System.out.println("course table end");
        return "role/admin/alternative/courseTable";
    }

    @RequestMapping("/courseManagement/cOpen")
    public String cOpen(Model model, @RequestParam(required=false) String result) {
        List<Division> divisions = divisionMapper.findAll();
        List<Major> majors = majorMapper.findAll();

        model.addAttribute("divisions", divisions);
        model.addAttribute("majors", majors);
        model.addAttribute("yearList", getYearList());
        model.addAttribute("course", new Course());
        model.addAttribute("compCategoryList", CompCategory.values());
        model.addAttribute("subjCategoryList", SubjCategory.values());
        model.addAttribute("result", result);

        return "role/admin/cOpen/cOpen";
    }

    @RequestMapping("/courseManagement/cOpen/courseTable")
    public String cOpenCourseTable(Model model,
                              @RequestParam(defaultValue = "0", required=false) int year,
                              @RequestParam(defaultValue = "0", required=false) int semester,
                              @RequestParam(defaultValue = "0", required=false) int division,
                              @RequestParam(defaultValue = "0", required=false) int major) {
        Searchable searchable = new Searchable();
        searchable.setYear(year);
        searchable.setSemester(semester);
        searchable.setDivision(division);
        searchable.setMajor(major);

        List<Course> courseList = courseMapper.findByCourseManagement(searchable);

        Course firstCourse = null;
        for(Course course: courseList) {
            firstCourse = course;
            break;
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("courseList", courseList);
        System.out.println("course table end");
        return "role/admin/cOpen/courseTable";
    }

    @RequestMapping("/courseManagement/cOpen/courseDetail")
    public String cOpenCourseDetail(Model model, @RequestParam int courseId) {
        Course course = courseMapper.findOne(courseId);
        model.addAttribute("course", course);
        List<Division> divisions = divisionMapper.findAll();
        List<Major> majors = majorMapper.findAll();
        model.addAttribute("divisions", divisions);
        model.addAttribute("majors", majors);
        model.addAttribute("yearList", getYearList());
        model.addAttribute("compCategoryList", CompCategory.values());
        model.addAttribute("subjCategoryList", SubjCategory.values());
        return "role/admin/cOpen/courseDetail";
    }

    @RequestMapping(value = "/courseManagement/cOpen/courseDetail", method = RequestMethod.POST)
    public String cOpenCourseDetail(@RequestParam int courseId, @ModelAttribute Course course) {

        courseMapper.update(course);
        return "redirect:/admin/courseManagement/cOpen?result=success";
    }

    @RequestMapping("/courseManagement/attendance")
    public String attendance(Model model) {
        return "role/admin/attendance/attendance";
    }

    @RequestMapping("/courseManagement/syllabus")
    public String syllabus(Model model) {
        model.addAttribute("yearList", getYearList());
        return "role/admin/syllabus/syllabus";
    }

    @RequestMapping("/courseManagement/syllabus/courseDetail")
    public String courseDetail(Model model, @RequestParam int courseId) {
        Course course = courseMapper.findOne(courseId);
        model.addAttribute("course", course);
        return "role/admin/syllabus/courseDetail";
    }

    @RequestMapping("/courseManagement/syllabus/courseTable")
    public String syllabusCourseTable(Model model,
                                      @RequestParam(defaultValue = "0", required=false) int year,
                                      @RequestParam(defaultValue = "0", required=false) int semester) {


        Searchable searchable = new Searchable();
        searchable.setYear(year);
        searchable.setSemester(semester);

        List<Course> courseList = courseMapper.findBySyllabus(searchable);
        Course firstCourse = null;
        for(Course course: courseList) {
            firstCourse = course;
            break;
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("courseList", courseList);
        return "role/admin/syllabus/courseTable";
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
    public String divisionMajor(Model model, @RequestParam(required=false) String result) {
        Division division = new Division();
        Major major = new Major();
        List<Division> divisions = divisionMapper.findAll();
        model.addAttribute("divisions", divisions);
        model.addAttribute("division", division);
        model.addAttribute("major", major);
        model.addAttribute("result", result);

        return "role/admin/divisionMajor/divisionMajor";
    }

    @RequestMapping(value = "/systemManagement/createDivision", method = RequestMethod.POST)
    public String createDivision(@ModelAttribute Division division) {
        divisionMapper.insert(division);
        return "redirect:/admin/systemManagement/divisionMajor?result=success";
    }

    @RequestMapping(value = "/systemManagement/createMajor", method = RequestMethod.POST)
    public String createDivision(@ModelAttribute Major major) {
        majorMapper.insert(major);
        return "redirect:/admin/systemManagement/divisionMajor?result=success";
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
