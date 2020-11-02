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
import koreatech.cse.domain.univ.*;
import koreatech.cse.repository.*;
import koreatech.cse.service.AuthorityService;
import koreatech.cse.service.UserService;
import koreatech.cse.util.SystemUtil;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes({"studentUser", "profUser", "course", "division", "semester", "menuAccess"})
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
    @Inject
    private SemesterMapper semesterMapper;
    @Inject
    private LectureMethodMapper lectureMethodMapper;
    @Inject
    private LectureFundamentalsMapper lectureFundamentalsMapper;
    @Inject
    private EvaluationMethodMapper evaluationMethodMapper;
    @Inject
    private EducationalMediumMapper educationalMediumMapper;
    @Inject
    private EquipmentMapper equipmentMapper;
    @Inject
    private ClassroomMapper classroomMapper;
    @Inject
    private MenuAccessMapper menuAccessMapper;




    @RequestMapping("/studentManagement/studentRegistration")
    public String studentRegistration(Model model, @RequestParam(required=false) String result) {

        List<Division> divisions = divisionMapper.findAll();

        model.addAttribute("divisions", divisions);
        User studentUser = new User();
        Contact contact = new Contact();
        studentUser.setContact(contact);
        model.addAttribute("studentUser", studentUser);
        model.addAttribute("result", result);
        model.addAttribute("statusList", StudentStatus.values());

        List<User> professors = userMapper.findAllProfessors();
        model.addAttribute("professors", professors);
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
        return "role/admin/studentInformation/studentTable";
    }

    @RequestMapping("/studentManagement/studentInformation/studentDetail")
    public String studentDetail(Model model, @RequestParam int studentId) {
        User studentUser = userMapper.findOne(studentId);
        model.addAttribute("studentUser", studentUser);
        model.addAttribute("statusList", StudentStatus.values());
        List<Division> divisions = divisionMapper.findAll();

        model.addAttribute("divisions", divisions);

        User advisor = userMapper.findOne(studentUser.getAdvisorId());
        model.addAttribute("advisor", advisor);
        List<User> professors = userMapper.findAllProfessors();
        model.addAttribute("professors", professors);
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

        model.addAttribute("divisions", divisions);
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
        User advisor = userMapper.findOne(studentUser.getAdvisorId());
        model.addAttribute("advisor", advisor);

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
        ;
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
    public String attendance(Model model, @RequestParam(required=false) String result) {

        List<Division> divisions = divisionMapper.findAll();
        List<Major> majors = majorMapper.findAll();

        model.addAttribute("divisions", divisions);
        model.addAttribute("majors", majors);
        model.addAttribute("yearList", getYearList());
        model.addAttribute("course", new Course());
        model.addAttribute("compCategoryList", CompCategory.values());
        model.addAttribute("subjCategoryList", SubjCategory.values());
        model.addAttribute("result", result);
        return "role/admin/attendance/attendance";
    }

    @RequestMapping("/courseManagement/attendance/courseTable")
    public String attendanceCourseTable(Model model,
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
        return "role/admin/attendance/courseTable";
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
    public String makeupClass(Model model, @RequestParam(required=false) String result) {

        List<Division> divisions = divisionMapper.findAll();
        List<Major> majors = majorMapper.findAll();

        model.addAttribute("divisions", divisions);
        model.addAttribute("majors", majors);
        model.addAttribute("yearList", getYearList());
        model.addAttribute("course", new Course());
        model.addAttribute("compCategoryList", CompCategory.values());
        model.addAttribute("subjCategoryList", SubjCategory.values());
        model.addAttribute("result", result);
        return "role/admin/makeupClass/makeupClass";
    }

    @RequestMapping("/courseManagement/makeupClass/courseTable")
    public String makeupClassCourseTable(Model model,
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
        return "role/admin/makeupClass/courseTable";
    }

    @RequestMapping("/academicManagement/studentGrade")
    public String studentGrade(Model model) {
        model.addAttribute("yearList", getYearList());
        return "role/admin/studentGrade/studentGrade";
    }

    @RequestMapping("/classProgress/registerGrade/courseTable")
    public String academicManagementCourseTable(Model model,
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
        return "role/admin/studentGrade/courseTable";
    }

    @RequestMapping("/classProgress/registerGrade/courseDetail")
    public String academicManagementCourseDetail(Model model, @RequestParam int courseId) {
        Course course = courseMapper.findOne(courseId);
        model.addAttribute("course", course);

        return "role/admin/studentGrade/courseDetail";
    }

    @RequestMapping("/academicManagement/graduationCriteria")
    public String graduationCriteria(Model model) {
        List<Division> divisions = divisionMapper.findAll();
        List<Major> majors = majorMapper.findAll();

        model.addAttribute("divisions", divisions);
        model.addAttribute("majors", majors);
        return "role/admin/graduationCriteria/graduationCriteria";
    }

    @RequestMapping("/academicManagement/graduationCriteria/studentTable")
    public String graduationCriteriaStudentTable(Model model, @RequestParam(required=false) String number,
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
        return "role/admin/graduationCriteria/studentTable";
    }

    @RequestMapping("/academicManagement/graduationCriteria/studentDetail")
    public String graduationCriteriaStudentDetail(Model model, @RequestParam int studentId) {
        User studentUser = userMapper.findOne(studentId);
        model.addAttribute("studentUser", studentUser);
        model.addAttribute("statusList", StudentStatus.values());
        List<Division> divisions = divisionMapper.findAll();
        List<Major> majors = majorMapper.findAll();

        model.addAttribute("divisions", divisions);
        model.addAttribute("majors", majors);

        return "role/admin/graduationCriteria/studentDetail";
    }

    @RequestMapping("/academicManagement/assessmentFactor")
    public String assessmentFactor(Model model) {
        List<Division> divisions = divisionMapper.findAll();
        List<Major> majors = majorMapper.findAll();

        model.addAttribute("divisions", divisions);
        model.addAttribute("majors", majors);
        model.addAttribute("yearList", getYearList());
        return "role/admin/assessmentFactor/assessmentFactor";
    }
    @RequestMapping("/academicManagement/assessmentFactor/courseTable")
    public String assessmentFactorCourseTable(Model model,
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
        return "role/admin/assessmentFactor/courseTable";
    }

    @RequestMapping("/academicManagement/assessmentFactor/courseDetail")
    public String assessmentFactorCourseDetail(Model model, @RequestParam int courseId) {
        Course course = courseMapper.findOne(courseId);
        model.addAttribute("course", course);

        return "role/admin/assessmentFactor/courseDetail";
    }

    @RequestMapping("/academicManagement/assessmentResult")
    public String assessmentResult(Model model) {
        List<Division> divisions = divisionMapper.findAll();
        List<Major> majors = majorMapper.findAll();

        model.addAttribute("divisions", divisions);
        model.addAttribute("majors", majors);
        model.addAttribute("yearList", getYearList());
        return "role/admin/assessmentResult/assessmentResult";
    }

    @RequestMapping("/academicManagement/assessmentResult/courseTable")
    public String assessmentResultCourseTable(Model model,
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
        return "role/admin/assessmentResult/courseTable";
    }

    @RequestMapping("/academicManagement/assessmentResult/courseDetail")
    public String assessmentResultCourseDetail(Model model, @RequestParam int courseId) {
        Course course = courseMapper.findOne(courseId);
        model.addAttribute("course", course);

        return "role/admin/assessmentResult/courseDetail";
    }

    @RequestMapping("/academicManagement/cqi")
    public String cqi(Model model) {
        model.addAttribute("yearList", getYearList());
        return "role/admin/cqi/cqi";
    }

    @RequestMapping("/academicManagement/cqi/courseTable")
    public String cqiReportCourseTable(Model model,
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
        return "role/admin/cqi/courseTable";
    }

    @RequestMapping("/academicManagement/cqi/courseDetail")
    public String cqiReportCourseDetail(Model model, @RequestParam int courseId) {
        Course course = courseMapper.findOne(courseId);
        model.addAttribute("course", course);

        return "role/admin/cqi/courseDetail";
    }

    @RequestMapping("/systemManagement/yearSemester")
    public String yearSemester(Model model, @RequestParam(required=false) String result) {
        Semester semester = new Semester();
        model.addAttribute("semester", semester);
        model.addAttribute("result", result);
        return "role/admin/yearSemester/yearSemester";
    }

    @RequestMapping(value = "/systemManagement/yearSemester", method = RequestMethod.POST)
    public String yearSemester(@ModelAttribute("semester") Semester semester, SessionStatus sessionStatus) {


        Semester exist = semesterMapper.findByYearAndSemester(semester.getYear(), semester.getSemester());
        if(exist == null)
            semesterMapper.insert(semester);
        sessionStatus.setComplete();

        return "redirect:/admin/systemManagement/yearSemester?result=success";
    }

    @RequestMapping(value = "/systemManagement/yearSemester/deleteSemester", method = RequestMethod.POST)
    @ResponseBody
    public Boolean deleteSemester(@RequestParam int id) {
        Semester exist = semesterMapper.findOne(id);
        semesterMapper.delete(exist);

        return true;
    }

    @RequestMapping(value = "/systemManagement/yearSemester/semesterEditable", method = RequestMethod.POST)
    @ResponseBody
    public Boolean semesterEditable(@RequestParam int pk, @RequestParam String name, @RequestParam String value) {
        Semester semester = semesterMapper.findOne(pk);
        
        
        switch (name) {
            default:
                SystemUtil.setObjectFieldValue(semester, name, value);
        }
        semesterMapper.update(semester);
        return true;
    }

    @RequestMapping("/systemManagement/yearSemester/semesterTable")
    public String semesterTable(Model model) {


        List<Semester> semesterList = semesterMapper.findAll();

        model.addAttribute("semesterList", semesterList);
        return "role/admin/yearSemester/semesterTable";
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

    @RequestMapping("/systemManagement/divisionMajor/divisionTable")
    public String divisionTable(Model model) {


        List<Division> divisionList = divisionMapper.findAll();

        model.addAttribute("divisionList", divisionList);
        return "role/admin/divisionMajor/divisionTable";
    }

    @RequestMapping(value = "/systemManagement/divisionMajor/divisionEditable", method = RequestMethod.POST)
    @ResponseBody
    public Boolean divisionEditable(@RequestParam int pk, @RequestParam String name, @RequestParam String value) {
        Division division = divisionMapper.findOne(pk);
        
        
        switch (name) {
            default:
                SystemUtil.setObjectFieldValue(division, name, value);
        }
        divisionMapper.update(division);
        return true;
    }

    @RequestMapping(value = "/systemManagement/divisionMajor/deleteDivision", method = RequestMethod.POST)
    @ResponseBody
    public Boolean deleteDivision(@RequestParam int id) {
        Division division = divisionMapper.findOne(id);
        List<Course> courses = courseMapper.findByDivision(id);
        if(CollectionUtils.isEmpty(courses)) {
            divisionMapper.delete(division);
            return true;
        } else {
            division.setEnabled(false);
            divisionMapper.update(division);
            return false;
        }
    }

    @RequestMapping(value = "/systemManagement/divisionMajor/enableDivision", method = RequestMethod.POST)
    @ResponseBody
    public Boolean enableDivision(@RequestParam int id) {
        Division division = divisionMapper.findOne(id);
        division.setEnabled(true);
        divisionMapper.update(division);
        return true;
    }

    @RequestMapping(value = "/systemManagement/createDivision", method = RequestMethod.POST)
    public String createDivision(@ModelAttribute Division division) {
        divisionMapper.insert(division);
        return "redirect:/admin/systemManagement/divisionMajor?result=success";
    }

    @RequestMapping("/systemManagement/lectureMethod")
    public String lectureMethod(Model model, @RequestParam(required=false) String result) {
        LectureMethod lectureMethod = new LectureMethod();
        model.addAttribute("lectureMethod", lectureMethod);
        model.addAttribute("result", result);
        return "role/admin/lectureMethod/lectureMethod";
    }

    @RequestMapping(value = "/systemManagement/lectureMethod", method = RequestMethod.POST)
    public String lectureMethod(@ModelAttribute LectureMethod lectureMethod) {
        lectureMethodMapper.insert(lectureMethod);
        return "redirect:/admin/systemManagement/lectureMethod?result=success";
    }

    @RequestMapping("/systemManagement/lectureMethod/lectureMethodTable")
    public String lectureMethodTable(Model model) {


        List<LectureMethod> lectureMethodList = lectureMethodMapper.findAll();

        model.addAttribute("lectureMethodList", lectureMethodList);
        return "role/admin/lectureMethod/lectureMethodTable";
    }

    @RequestMapping(value = "/systemManagement/lectureMethod/lectureMethodEditable", method = RequestMethod.POST)
    @ResponseBody
    public Boolean lectureMethodEditable(@RequestParam int pk, @RequestParam String name, @RequestParam String value) {
        LectureMethod lectureMethod = lectureMethodMapper.findOne(pk);
        
        
        switch (name) {
            default:
                SystemUtil.setObjectFieldValue(lectureMethod, name, value);
        }
        lectureMethodMapper.update(lectureMethod);
        return true;
    }

    @RequestMapping(value = "/systemManagement/lectureMethod/deleteLectureMethod", method = RequestMethod.POST)
    @ResponseBody
    public Boolean deleteLectureMethod(@RequestParam int id) {
        LectureMethod lectureMethod = lectureMethodMapper.findOne(id);

        //TODO:
        lectureMethodMapper.delete(lectureMethod);

        /*List<Course> courses = courseMapper.findByLectureMethod(id);
        if(CollectionUtils.isEmpty(courses)) {
            lectureMethodMapper.delete(lectureMethod);
            return true;
        } else {
            lectureMethod.setEnabled(false);
            lectureMethodMapper.update(lectureMethod);
            return false;
        }*/
        return true;
    }

    @RequestMapping(value = "/systemManagement/lectureMethod/enableLectureMethod", method = RequestMethod.POST)
    @ResponseBody
    public Boolean enableLectureMethod(@RequestParam int id) {
        LectureMethod lectureMethod = lectureMethodMapper.findOne(id);
        lectureMethod.setEnabled(true);
        lectureMethodMapper.update(lectureMethod);
        return true;
    }

    @RequestMapping("/systemManagement/evaluationMethod")
    public String evaluationMethod(Model model, @RequestParam(required=false) String result) {
        EvaluationMethod evaluationMethod = new EvaluationMethod();
        model.addAttribute("evaluationMethod", evaluationMethod);
        model.addAttribute("result", result);
        return "role/admin/evaluationMethod/evaluationMethod";
    }

    @RequestMapping(value = "/systemManagement/evaluationMethod", method = RequestMethod.POST)
    public String evaluationMethod(@ModelAttribute EvaluationMethod evaluationMethod) {
        evaluationMethodMapper.insert(evaluationMethod);
        return "redirect:/admin/systemManagement/evaluationMethod?result=success";
    }

    @RequestMapping("/systemManagement/evaluationMethod/evaluationMethodTable")
    public String evaluationMethodTable(Model model) {


        List<EvaluationMethod> evaluationMethodList = evaluationMethodMapper.findAll();

        model.addAttribute("evaluationMethodList", evaluationMethodList);
        return "role/admin/evaluationMethod/evaluationMethodTable";
    }

    @RequestMapping(value = "/systemManagement/evaluationMethod/evaluationMethodEditable", method = RequestMethod.POST)
    @ResponseBody
    public Boolean evaluationMethodEditable(@RequestParam int pk, @RequestParam String name, @RequestParam String value) {
        EvaluationMethod evaluationMethod = evaluationMethodMapper.findOne(pk);


        switch (name) {
            default:
                SystemUtil.setObjectFieldValue(evaluationMethod, name, value);
        }
        evaluationMethodMapper.update(evaluationMethod);
        return true;
    }

    @RequestMapping(value = "/systemManagement/evaluationMethod/deleteEvaluationMethod", method = RequestMethod.POST)
    @ResponseBody
    public Boolean deleteEvaluationMethod(@RequestParam int id) {
        EvaluationMethod evaluationMethod = evaluationMethodMapper.findOne(id);

        //TODO:
        evaluationMethodMapper.delete(evaluationMethod);

        /*List<Course> courses = courseMapper.findByEvaluationMethod(id);
        if(CollectionUtils.isEmpty(courses)) {
            evaluationMethodMapper.delete(evaluationMethod);
            return true;
        } else {
            evaluationMethod.setEnabled(false);
            evaluationMethodMapper.update(evaluationMethod);
            return false;
        }*/
        return true;
    }

    @RequestMapping(value = "/systemManagement/evaluationMethod/enableEvaluationMethod", method = RequestMethod.POST)
    @ResponseBody
    public Boolean enableEvaluationMethod(@RequestParam int id) {
        EvaluationMethod evaluationMethod = evaluationMethodMapper.findOne(id);
        evaluationMethod.setEnabled(true);
        evaluationMethodMapper.update(evaluationMethod);
        return true;
    }

    @RequestMapping("/systemManagement/educationalMedium")
    public String educationalMedium(Model model) {
        EducationalMedium educationalMedium = new EducationalMedium();
        model.addAttribute("educationalMedium", educationalMedium);
        return "role/admin/educationalMedium/educationalMedium";
    }

    @RequestMapping(value = "/systemManagement/educationalMedium", method = RequestMethod.POST)
    public String educationalMedium(@ModelAttribute EducationalMedium educationalMedium) {
        educationalMediumMapper.insert(educationalMedium);
        return "redirect:/admin/systemManagement/educationalMedium?result=success";
    }

    @RequestMapping("/systemManagement/educationalMedium/educationalMediumTable")
    public String educationalMediumTable(Model model) {


        List<EducationalMedium> educationalMediumList = educationalMediumMapper.findAll();

        model.addAttribute("educationalMediumList", educationalMediumList);
        return "role/admin/educationalMedium/educationalMediumTable";
    }

    @RequestMapping(value = "/systemManagement/educationalMedium/educationalMediumEditable", method = RequestMethod.POST)
    @ResponseBody
    public Boolean educationalMediumEditable(@RequestParam int pk, @RequestParam String name, @RequestParam String value) {
        EducationalMedium educationalMedium = educationalMediumMapper.findOne(pk);


        switch (name) {
            default:
                SystemUtil.setObjectFieldValue(educationalMedium, name, value);
        }
        educationalMediumMapper.update(educationalMedium);
        return true;
    }

    @RequestMapping(value = "/systemManagement/educationalMedium/deleteEducationalMedium", method = RequestMethod.POST)
    @ResponseBody
    public Boolean deleteEducationalMedium(@RequestParam int id) {
        EducationalMedium educationalMedium = educationalMediumMapper.findOne(id);

        //TODO:
        educationalMediumMapper.delete(educationalMedium);

        /*List<Course> courses = courseMapper.findByEducationalMedium(id);
        if(CollectionUtils.isEmpty(courses)) {
            educationalMediumMapper.delete(educationalMedium);
            return true;
        } else {
            educationalMedium.setEnabled(false);
            educationalMediumMapper.update(educationalMedium);
            return false;
        }*/
        return true;
    }

    @RequestMapping(value = "/systemManagement/educationalMedium/enableEducationalMedium", method = RequestMethod.POST)
    @ResponseBody
    public Boolean enableEducationalMedium(@RequestParam int id) {
        EducationalMedium educationalMedium = educationalMediumMapper.findOne(id);
        educationalMedium.setEnabled(true);
        educationalMediumMapper.update(educationalMedium);
        return true;
    }

    @RequestMapping("/systemManagement/equipment")
    public String equipment(Model model, @RequestParam(required=false) String result) {
        Equipment equipment = new Equipment();
        model.addAttribute("equipment", equipment);
        model.addAttribute("result", result);
        return "role/admin/equipment/equipment";
    }

    @RequestMapping(value = "/systemManagement/equipment", method = RequestMethod.POST)
    public String equipment(@ModelAttribute Equipment equipment) {
        equipmentMapper.insert(equipment);
        return "redirect:/admin/systemManagement/equipment?result=success";
    }

    @RequestMapping("/systemManagement/equipment/equipmentTable")
    public String equipmentTable(Model model) {


        List<Equipment> equipmentList = equipmentMapper.findAll();

        model.addAttribute("equipmentList", equipmentList);
        return "role/admin/equipment/equipmentTable";
    }

    @RequestMapping(value = "/systemManagement/equipment/equipmentEditable", method = RequestMethod.POST)
    @ResponseBody
    public Boolean equipmentEditable(@RequestParam int pk, @RequestParam String name, @RequestParam String value) {
        Equipment equipment = equipmentMapper.findOne(pk);


        switch (name) {
            default:
                SystemUtil.setObjectFieldValue(equipment, name, value);
        }
        equipmentMapper.update(equipment);
        return true;
    }

    @RequestMapping(value = "/systemManagement/equipment/deleteEquipment", method = RequestMethod.POST)
    @ResponseBody
    public Boolean deleteEquipment(@RequestParam int id) {
        Equipment equipment = equipmentMapper.findOne(id);

        //TODO:
        equipmentMapper.delete(equipment);

        /*List<Course> courses = courseMapper.findByEquipment(id);
        if(CollectionUtils.isEmpty(courses)) {
            equipmentMapper.delete(equipment);
            return true;
        } else {
            equipment.setEnabled(false);
            equipmentMapper.update(equipment);
            return false;
        }*/
        return true;
    }

    @RequestMapping(value = "/systemManagement/equipment/enableEquipment", method = RequestMethod.POST)
    @ResponseBody
    public Boolean enableEquipment(@RequestParam int id) {
        Equipment equipment = equipmentMapper.findOne(id);
        equipment.setEnabled(true);
        equipmentMapper.update(equipment);
        return true;
    }

    @RequestMapping("/systemManagement/classroom")
    public String classroom(Model model, @RequestParam(required=false) String result) {
        Classroom classroom = new Classroom();
        model.addAttribute("classroom", classroom);
        model.addAttribute("result", result);
        return "role/admin/classroom/classroom";
    }

    @RequestMapping(value = "/systemManagement/classroom", method = RequestMethod.POST)
    public String classroom(@ModelAttribute Classroom classroom) {
        classroomMapper.insert(classroom);
        return "redirect:/admin/systemManagement/classroom?result=success";
    }

    @RequestMapping("/systemManagement/classroom/classroomTable")
    public String classroomTable(Model model) {


        List<Classroom> classroomList = classroomMapper.findAll();

        model.addAttribute("classroomList", classroomList);
        return "role/admin/classroom/classroomTable";
    }

    @RequestMapping(value = "/systemManagement/classroom/classroomEditable", method = RequestMethod.POST)
    @ResponseBody
    public Boolean classroomEditable(@RequestParam int pk, @RequestParam String name, @RequestParam String value) {
        Classroom classroom = classroomMapper.findOne(pk);


        switch (name) {
            default:
                SystemUtil.setObjectFieldValue(classroom, name, value);
        }
        classroomMapper.update(classroom);
        return true;
    }

    @RequestMapping(value = "/systemManagement/classroom/deleteClassroom", method = RequestMethod.POST)
    @ResponseBody
    public Boolean deleteClassroom(@RequestParam int id) {
        Classroom classroom = classroomMapper.findOne(id);

        //TODO:
        classroomMapper.delete(classroom);

        /*List<Course> courses = courseMapper.findByClassroom(id);
        if(CollectionUtils.isEmpty(courses)) {
            classroomMapper.delete(classroom);
            return true;
        } else {
            classroom.setEnabled(false);
            classroomMapper.update(classroom);
            return false;
        }*/
        return true;
    }

    @RequestMapping(value = "/systemManagement/classroom/enableClassroom", method = RequestMethod.POST)
    @ResponseBody
    public Boolean enableClassroom(@RequestParam int id) {
        Classroom classroom = classroomMapper.findOne(id);
        classroom.setEnabled(true);
        classroomMapper.update(classroom);
        return true;
    }

    @RequestMapping(value = "/systemManagement/classroom/disableClassroom", method = RequestMethod.POST)
    @ResponseBody
    public Boolean disableClassroom(@RequestParam int id) {
        Classroom classroom = classroomMapper.findOne(id);
        classroom.setEnabled(false);
        classroomMapper.update(classroom);
        return true;
    }

    @RequestMapping("/systemManagement/menu")
    public String menu(Model model, @RequestParam(required=false) String result) {
        MenuAccess menuAccess = menuAccessMapper.findOne();
        if(menuAccess == null) {
            menuAccess = new MenuAccess();
            menuAccessMapper.insert(menuAccess);
        }

        model.addAttribute("menuAccess", menuAccess);
        model.addAttribute("result", result);
        return "role/admin/menu/menu";
    }

    @RequestMapping(value = "/systemManagement/menu", method = RequestMethod.POST)
    public String menu(@ModelAttribute MenuAccess menuAccess) {
        menuAccessMapper.update(menuAccess);
        return "redirect:/admin/systemManagement/menu?result=success";
    }



    @RequestMapping("/systemManagement/addAdmin")
    public String addAdmin(Model model) {

        User adminUser = new User();
        Contact contact = new Contact();
        adminUser.setContact(contact);
        model.addAttribute("adminUser", adminUser);
        return "role/admin/addAdmin/addAdmin";
    }

    @RequestMapping("/systemManagement/errorReport")
    public String errorReport(Model model) {
        return "role/admin/errorReport/errorReport";
    }





    
    private List<Integer> getYearList() {
        return semesterMapper.findYears();
    }

}
