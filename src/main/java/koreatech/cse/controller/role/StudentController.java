package koreatech.cse.controller.role;

import koreatech.cse.domain.Searchable;
import koreatech.cse.domain.UploadedFile;
import koreatech.cse.domain.User;
import koreatech.cse.domain.constant.Designation;
import koreatech.cse.domain.role.professor.LectureFundamentals;
import koreatech.cse.domain.role.student.GraduationResearchPlan;
import koreatech.cse.domain.univ.Course;
import koreatech.cse.domain.univ.Division;
import koreatech.cse.repository.*;
import koreatech.cse.service.AuthorityService;
import koreatech.cse.service.UserService;
import koreatech.cse.util.DateHelper;
import org.joda.time.DateTime;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@SessionAttributes("studentUser")
@PreAuthorize("hasRole('ROLE_STUDENT')")
@RequestMapping("/student")
public class StudentController {
    @Inject
    private UserMapper userMapper;
    @Inject
    private DivisionMapper divisionMapper;
    @Inject
    private ContactMapper contactMapper;
    @Inject
    private AuthorityService authorityService;
    @Inject
    private UserService userService;
    @Inject
    private CourseMapper courseMapper;
    @Inject
    private ProfessorCourseMapper professorCourseMapper;
    @Inject
    private LectureFundamentalsMapper lectureFundamentalsMapper;
    @Inject
    private SemesterMapper semesterMapper;
    @Inject
    private GraduationResearchPlanMapper graduationResearchPlanMapper;
    @Inject
    private UploadedFileMapper uploadedFileMapper;


    @RequestMapping("/courseGuide/yearlyCurriculum")
    public String yearlyCurriculum(Model model) {

        List<Division> divisions = divisionMapper.findAll();

        model.addAttribute("divisions", divisions);
        model.addAttribute("yearList", getYearList());
        return "role/student/yearlyCurriculum/yearlyCurriculum";
    }

    @RequestMapping("/courseGuide/yearlyCurriculum/courseTable")
    public String curriculumTable(Model model,
                              @RequestParam(defaultValue = "0", required=false) int year,
                              @RequestParam(defaultValue = "0", required=false) int division) {

        List<Division> divisions = divisionMapper.findAll();
        if(division != 0) {
            divisions = divisionMapper.findAll();
            List<Division> filtered = new ArrayList<>();
            for(Division d: divisions) {
                if(d.getId() == division) {
                    filtered.add(d);
                }
            }
            divisions = filtered;
        }


        model.addAttribute("year", year);
        List<UploadedFile> uploadedFiles = uploadedFileMapper.findByDesignationYear(Designation.curriculum, year);
        model.addAttribute("uploadedFiles", uploadedFiles);
        model.addAttribute("divisions", divisions);
        return "role/student/yearlyCurriculum/courseTable";
    }

    @RequestMapping("/courseGuide/courseInfo")
    public String courseInfo(Model model) {


        return "role/student/courseInfo/courseInfo";
    }

    @RequestMapping("/courseGuide/courseInfo/courseTable")
    public String courseInfoCourseTable(Model model,
                                  @RequestParam(required=false) String code,
                                  @RequestParam(required=false) String title) {

        Searchable searchable = new Searchable();
        searchable.setCode(code);
        searchable.setTitle(title);

        List<Course> courseList = courseMapper.findByYearSemester(searchable);
        Course firstCourse = null;
        for(Course course: courseList) {
            firstCourse = course;
            break;
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("courseList", courseList);
        return "role/student/courseInfo/courseTable";
    }

    @RequestMapping("/courseGuide/courseInfo/courseDetail")
    public String courseInfoCourseDetail(Model model, @RequestParam int courseId) {
        Course course = courseMapper.findOne(courseId);
        model.addAttribute("course", course);

        return "role/student/courseInfo/courseDetail";
    }

    @RequestMapping("/courseGuide/alternative")
    public String alternative(Model model) {
        return "role/student/alternative/alternative";
    }

    @RequestMapping("/courseGuide/alternative/courseTable")
    public String alternativeCourseTable(Model model,
                                        @RequestParam(required=false) String code,
                                        @RequestParam(required=false) String title) {

        Searchable searchable = new Searchable();
        searchable.setCode(code);
        searchable.setTitle(title);

        List<Course> courseList = courseMapper.findByYearSemester(searchable);
        Course firstCourse = null;
        for(Course course: courseList) {
            firstCourse = course;
            break;
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("courseList", courseList);
        return "role/student/alternative/courseTable";
    }

    @RequestMapping("/courseGuide/alternative/courseDetail")
    public String alternativeCourseDetail(Model model, @RequestParam int courseId) {
        Course course = courseMapper.findOne(courseId);
        model.addAttribute("course", course);

        return "role/student/alternative/courseDetail";
    }

    @RequestMapping("/register/basic")
    public String basic(Model model) {
        return "role/student/basic/basic";
    }

    @RequestMapping(value = "/register/basic", method = RequestMethod.POST)
    public String basic(@ModelAttribute("studentUser") User studentUser, SessionStatus sessionStatus) {
        System.out.println("studentUser = " + studentUser);


        userMapper.update(studentUser);
        contactMapper.update(studentUser.getContact());
        authorityService.authenticateUserAndSetSession(studentUser);
        sessionStatus.setComplete();

        return "redirect:/student/register/basic";
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

    @RequestMapping("/classInformation/syllabus/courseTable")
    public String syllabusCourseTable(Model model,
                                      @RequestParam(defaultValue = "0", required=false) int year,
                                      @RequestParam(defaultValue = "0", required=false) int semester) {

        Searchable searchable = new Searchable();
        searchable.setYear(year);
        searchable.setSemester(semester);

        List<Course> courseList = courseMapper.findByYearSemesterDivisionProfId(searchable);
        Course firstCourse = null;
        for(Course course: courseList) {
            firstCourse = course;
            break;
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("courseList", courseList);
        return "role/student/syllabus/courseTable";
    }

    @RequestMapping("/classInformation/syllabus/courseDetail")
    public String courseDetail(Model model, @RequestParam int courseId) {
        Course course = courseMapper.findOne(courseId);
        model.addAttribute("course", course);
        LectureFundamentals lectureFundamentals = lectureFundamentalsMapper.findByProfCourseId(courseId);
        model.addAttribute("lectureFundamentals", lectureFundamentals == null ? new LectureFundamentals() : lectureFundamentals);
        return "role/student/syllabus/courseDetail";
    }

    @RequestMapping("/classInformation/enrolment")
    public String enrolment(Model model) {

        List<Division> divisions = divisionMapper.findAll();

        model.addAttribute("divisions", divisions);
        model.addAttribute("yearList", getYearList());

        Searchable searchable = new Searchable();
        List<Course> courseList = courseMapper.findByYearSemesterDivisionProfId(searchable);
        Course firstCourse = null;
        for(Course course: courseList) {
            firstCourse = course;
            break;
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("courseList", courseList);
        return "role/student/enrolment/enrolment";
    }

    @RequestMapping("/classInformation/enrolment/courseTable")
    public String enrolmentCourseTable(Model model,
                                                       @RequestParam(defaultValue = "0", required=false) int year,
                                                       @RequestParam(defaultValue = "0", required=false) int semester) {

        Searchable searchable = new Searchable();
        searchable.setYear(year);
        searchable.setSemester(semester);

        List<Course> courseList = courseMapper.findByYearSemesterDivisionProfId(searchable);
        Course firstCourse = null;
        for(Course course: courseList) {
            firstCourse = course;
            break;
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("courseList", courseList);
        return "role/student/enrolment/courseTable";
    }

    @RequestMapping("/classInformation/counselingCourseEnrolment")
    public String counselingCourseEnrolment(Model model) {
        List<Division> divisions = divisionMapper.findAll();

        model.addAttribute("divisions", divisions);
        model.addAttribute("yearList", getYearList());
        Searchable searchable = new Searchable();
        List<Course> courseList = courseMapper.findByYearSemesterDivisionProfId(searchable);
        Course firstCourse = null;
        for(Course course: courseList) {
            firstCourse = course;
            break;
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("courseList", courseList);
        return "role/student/counselingCourseEnrolment/counselingCourseEnrolment";
    }

    @RequestMapping("/classInformation/counselingCourseEnrolment/courseTable")
    public String counselingCourseEnrolmentCourseTable(Model model,
                                      @RequestParam(defaultValue = "0", required=false) int year,
                                      @RequestParam(defaultValue = "0", required=false) int semester) {

        Searchable searchable = new Searchable();
        searchable.setYear(year);
        searchable.setSemester(semester);

        List<Course> courseList = courseMapper.findByYearSemesterDivisionProfId(searchable);
        Course firstCourse = null;
        for(Course course: courseList) {
            firstCourse = course;
            break;
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("courseList", courseList);
        return "role/student/counselingCourseEnrolment/courseTable";
    }

    @RequestMapping("/classInformation/counselingCourseEnrolment/courseDetail")
    public String counselingCourseEnrolmentCourseDetail(Model model, @RequestParam int courseId) {
        Course course = courseMapper.findOne(courseId);
        model.addAttribute("course", course);

        return "role/student/counselingCourseEnrolment/courseDetail";
    }

    @RequestMapping("/classInformation/classAssessment")
    public String assessment(Model model) {

        model.addAttribute("yearList", getYearList());
        return "role/student/classAssessment/classAssessment";
    }

    @RequestMapping("/classInformation/classAssessment/courseTable")
    public String classAssessmentCourseTable(Model model,
                                             @RequestParam(defaultValue = "0", required=false) int year,
                                             @RequestParam(defaultValue = "0", required=false) int semester) {


        Searchable searchable = new Searchable();
        searchable.setYear(year);
        searchable.setSemester(semester);

        List<Course> courseList = courseMapper.findByYearSemesterDivisionProfId(searchable);
        Course firstCourse = null;
        for(Course course: courseList) {
            firstCourse = course;
            break;
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("courseList", courseList);
        return "role/student/classAssessment/courseTable";
    }

    @RequestMapping("/classInformation/classAssessment/courseDetail")
    public String classAssessmentCourseDetail(Model model, @RequestParam int courseId) {
        Course course = courseMapper.findOne(courseId);
        model.addAttribute("course", course);
        LectureFundamentals lectureFundamentals = lectureFundamentalsMapper.findByProfCourseId(courseId);
        model.addAttribute("lectureFundamentals", lectureFundamentals == null ? new LectureFundamentals() : lectureFundamentals);
        return "role/student/classAssessment/courseDetail";
    }

    @RequestMapping("/grades/inquiryGrade")
    public String inquiryGrade(Model model) {
        User studentUser = User.current();
        model.addAttribute("studentUser", studentUser);
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
    public String graduationResearchPlan(Model model, @RequestParam(required=false) String result) {
        User studentUser = User.current();
        model.addAttribute("studentUser", studentUser);

        GraduationResearchPlan stored = graduationResearchPlanMapper.findByUserId(studentUser.getId());
        model.addAttribute("stored", stored);
        model.addAttribute("graduationResearchPlan", new GraduationResearchPlan());
        model.addAttribute("result", result);
        if (stored == null)
            return "role/student/graduationResearchPlan/newGraduationResearchPlan";
        else
            return "role/student/graduationResearchPlan/graduationResearchPlan";
    }

    @RequestMapping(value = "/graduation/graduationResearchPlan", method = RequestMethod.POST)
    public String graduationResearchPlan(@ModelAttribute("graduationResearchPlan") GraduationResearchPlan graduationResearchPlan, SessionStatus sessionStatus) {

        User studentUser = User.current();
        graduationResearchPlan.setUserId(studentUser.getId());
        try {
            String submitDate = graduationResearchPlan.getSubmitDate();
            Date d = DateHelper.parse("yyyy-MM-dd", submitDate);
            DateTime dt = new DateTime(d);
            graduationResearchPlan.setYear(dt.getYear());

        } catch(Exception e) {

        }

        graduationResearchPlanMapper.insert(graduationResearchPlan);
        return "redirect:/student/graduation/graduationResearchPlan?result=success";
    }


























    private List<Integer> getYearList() {
        return semesterMapper.findYears();
    }

}
