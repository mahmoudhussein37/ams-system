package koreatech.cse.controller.role;

import koreatech.cse.domain.Searchable;
import koreatech.cse.domain.UploadedFile;
import koreatech.cse.domain.User;
import koreatech.cse.domain.constant.Designation;
import koreatech.cse.domain.role.professor.*;
import koreatech.cse.domain.role.student.GraduationResearchPlan;
import koreatech.cse.domain.role.student.StudentCourse;
import koreatech.cse.domain.univ.*;
import koreatech.cse.repository.*;
import koreatech.cse.service.AuthorityService;
import koreatech.cse.service.FileService;
import koreatech.cse.service.ProfService;
import koreatech.cse.service.UserService;
import koreatech.cse.util.DateHelper;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Controller
@SessionAttributes({"studentUser", "assessment", "graduationResearchPlan"})
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
    private GraduationCriteriaMapper graduationCriteriaMapper;
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
    @Inject
    private AltCourseMapper altCourseMapper;
    @Inject
    private FileService fileService;
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
    private StudentCourseMapper studentCourseMapper;
    @Inject
    private MenuAccessMapper menuAccessMapper;
    @Inject
    private AssessmentFactorMapper assessmentFactorMapper;
    @Inject
    private AssessmentMapper assessmentMapper;
    @Inject
    private CertificateMapper certificateMapper;
    @Inject
    private ProfService profService;
    @Inject
    private LangCertMapper langCertMapper;

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

        Course firstCourse = null;
        List<Course> courseList;
        if(StringUtils.isBlank(code) && StringUtils.isBlank(title)) {
            courseList = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();
            searchable.setCode(code);
            searchable.setTitle(title);
            searchable.setEnabled(true);
            courseList = courseMapper.findBy(searchable);

            for(Course course: courseList) {
                firstCourse = course;
                break;
            }
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

        Course firstCourse = null;
        List<Course> courseList;
        if(StringUtils.isBlank(code) && StringUtils.isBlank(title)) {
            courseList = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();
            searchable.setCode(code);
            searchable.setTitle(title);
            courseList = courseMapper.findBy(searchable);

            for(Course course: courseList) {
                firstCourse = course;
                break;
            }
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("courseList", courseList);
        return "role/student/alternative/courseTable";
    }

    @RequestMapping("/courseGuide/alternative/courseDetail")
    public String alternativeCourseDetail(Model model, @RequestParam int courseId) {
        Course course = courseMapper.findOne(courseId);
        model.addAttribute("course", course);
        List<AltCourse> altCourses = altCourseMapper.findByTargetCourseId(courseId);
        model.addAttribute("altCourses", altCourses);
        return "role/student/alternative/courseDetail";
    }

    @RequestMapping("/register/basic")
    public String basic(Model model) {

        User studentUser = userMapper.findOne(User.current().getId());
        model.addAttribute("studentUser", studentUser);
        List<LangCert> langCerts = langCertMapper.findByUserId(User.current().getId());
        model.addAttribute("langCerts", langCerts);

        return "role/student/basic/basic";
    }

    @RequestMapping(value = "/register/basic", method = RequestMethod.POST)
    public String basic(@ModelAttribute("studentUser") User studentUser, HttpServletRequest request, SessionStatus sessionStatus) throws IOException {
        User user = User.current();
        if (request instanceof MultipartHttpServletRequest) {
            MultipartFile f = ((MultipartHttpServletRequest) request).getFile("file");
            System.out.println("f = " + f.getSize());
            if(f == null || f.getSize() == 0) {

            } else {

                String fileName = f.getOriginalFilename();
                String ext = fileService.getExtension(fileName);
                if(ext.equalsIgnoreCase("png") || ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg") || ext.equalsIgnoreCase("gif")) {
                    uploadedFileMapper.deleteProfileByUser(user);
                    DateTime dt = new DateTime();
                    fileService.processUploadedFile(f, user, Designation.profile, 0, 0, dt.getYear());
                }

            }

        }


        userMapper.update(studentUser);
        contactMapper.update(studentUser.getContact());
        authorityService.authenticateUserAndSetSession(studentUser);



        sessionStatus.setComplete();

        return "redirect:/student/register/basic";
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
                                      @RequestParam(defaultValue = "0", required=false) int semester,
                                      @RequestParam(defaultValue = "0", required=false) int division,
                                      @RequestParam(required=false) String title) {
        ProfessorCourse firstCourse = null;
        List<ProfessorCourse> courseList;
        if(year == 0 && semester == 0 && division == 0 && StringUtils.isBlank(title)) {
            courseList = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();
            searchable.setYear(year);
            searchable.setSemester(semester);
            searchable.setDivision(division);
            searchable.setTitle(title);
            searchable.setEnabled(true);

            courseList = professorCourseMapper.findBy(searchable);

            for(ProfessorCourse course: courseList) {
                firstCourse = course;
                break;
            }
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("profCourseList", courseList);
        return "role/student/syllabus/courseTable";
    }

    @RequestMapping("/classInformation/syllabus/courseDetail")
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
        return "role/student/syllabus/courseDetail";
    }

    @RequestMapping("/classInformation/classAssessment")
    public String assessment(Model model, @RequestParam(required=false) String result) {

        model.addAttribute("yearList", getYearList());
        model.addAttribute("result", result);
        return "role/student/classAssessment/classAssessment";
    }

    @RequestMapping("/classInformation/classAssessment/courseTable")
    public String classAssessmentCourseTable(Model model,
                                             @RequestParam(defaultValue = "0", required=false) int year,
                                             @RequestParam(defaultValue = "0", required=false) int semester) {


        User user = User.current();
        StudentCourse firstCourse = null;
        List<StudentCourse> studentCourseList;
        if(year == 0 && semester == 0) {
            studentCourseList = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();
            searchable.setYear(year);
            searchable.setSemester(semester);
            searchable.setUserId(user.getId());

            studentCourseList = studentCourseMapper.findByUserIdYearSemester(searchable);

            for(StudentCourse sc: studentCourseList) {
                firstCourse = sc;
                break;
            }
        }



        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("courseList", studentCourseList);
        return "role/student/classAssessment/courseTable";
    }

    @RequestMapping("/classInformation/classAssessment/courseDetail")
    public String classAssessmentCourseDetail(Model model, @RequestParam int studentCourseId) {
        User studentUser = User.current();
        StudentCourse sc = studentCourseMapper.findOne(studentCourseId);
        model.addAttribute("sc", sc);

        Assessment assessment = assessmentMapper.findByUserIdProfCourseId(studentUser.getId(), sc.getProfCourseId());
        if(assessment == null) {
            assessment = new Assessment();
            assessment.setUserId(studentUser.getId());
            assessment.setProfCourseId(sc.getProfCourseId());
        }

        model.addAttribute("assessment", assessment);
        List<AssessmentFactor> assessmentFactors = assessmentFactorMapper.findByCourseIdEnabled(sc.getCourseId());
        model.addAttribute("assessmentFactors", assessmentFactors);
        List<AssessmentFactor> assessmentFactorsAll = assessmentFactorMapper.findByCourseId(sc.getCourseId());
        model.addAttribute("assessmentFactorsAll", assessmentFactorsAll);
        MenuAccess menuAccess = menuAccessMapper.findOne();
        model.addAttribute("menuAccess", menuAccess);
        ProfessorCourse pc = professorCourseMapper.findOne(sc.getProfCourseId());
        Semester semester = pc.getSemester();
        model.addAttribute("isEditable", menuAccess.isAssessment() && semester.isCurrent());

        return "role/student/classAssessment/courseDetail";
    }

    @RequestMapping(value = "/classInformation/classAssessment/courseDetail", method = RequestMethod.POST)
    public String classAssessmentCourseDetail(@ModelAttribute("assessment") Assessment assessment, @RequestParam int studentCourseId) {
        assessmentMapper.insert(assessment);
        return "redirect:/student/classInformation/classAssessment?result=success";
    }
    @RequestMapping("/grades/inquiryGrade")
    public String inquiryGrade(Model model) {
        User studentUser = User.current();
        model.addAttribute("studentUser", studentUser);
        Semester firstSemester = null;
        LinkedHashSet<Integer> semesterSet = studentCourseMapper.findSemesterIdByUserIdValid(studentUser.getId());
        for(Integer semesterId: semesterSet) {
            firstSemester = semesterMapper.findOne(semesterId);
            break;
        }
        model.addAttribute("firstSemester", firstSemester);
        Map<Semester, List<StudentCourse>> map = new HashMap<>();
        for(Integer semesterId: semesterSet) {
            Semester semester = semesterMapper.findOne(semesterId);
            Searchable searchable = new Searchable();
            searchable.setYear(semester.getYear());
            searchable.setSemester(semester.getSemester());
            searchable.setUserId(studentUser.getId());
            List<StudentCourse> courses = studentCourseMapper.findByUserIdYearSemester(searchable);
            List<StudentCourse> filtered = new ArrayList<>();
            for(StudentCourse sc: courses) {
                Assessment assessment = assessmentMapper.findByUserIdProfCourseId(studentUser.getId(), sc.getProfCourseId());
                if(assessment != null) {
                    filtered.add(sc);
                }
            }




            map.put(semester, filtered);
        }

        model.addAttribute("courseMap", map);


        return "role/student/inquiryGrade/inquiryGrade";
    }

    @RequestMapping("/grades/inquiryGrade/gradeDetail")
    public String inquiryGradeDetail(Model model, @RequestParam int semesterId) {
        User studentUser = User.current();

        List<StudentCourse> studentCourses = studentCourseMapper.findByUserIdSemesterIdValid(studentUser.getId(), semesterId);

        List<StudentCourse> filtered = new ArrayList<>();
        for(StudentCourse sc: studentCourses) {
            Assessment assessment = assessmentMapper.findByUserIdProfCourseId(studentUser.getId(), sc.getProfCourseId());
            if(assessment != null) {
                filtered.add(sc);
            }
        }


        model.addAttribute("studentUser", studentUser);
        model.addAttribute("studentCourses", filtered);
        return "role/student/inquiryGrade/gradeDetail";
    }

    @RequestMapping("/grades/inquiryGrade/gradeDetailForPrint")
    public String inquiryGradeDetailPrint(Model model) {
        User studentUser = User.current();
        model.addAttribute("studentUser", studentUser);

        Certificate certificate = certificateMapper.findByUserId(studentUser.getId());
        if(certificate == null) {
            certificate = new Certificate();
            certificate.setRequestId(User.current().getId());
            certificate.setUserId(studentUser.getId());
            certificateMapper.insert(certificate);
        }
        model.addAttribute("certificate", certificate);
        model.addAttribute("today", DateHelper.format(new Date()));
        Map<Semester, List<StudentCourse>> map = profService.getStudentSemesterCourseMap(studentUser.getId());
        model.addAttribute("finalScore", profService.getStudentTotalScore(studentUser.getId()));
        model.addAttribute("courseMap", map);
        return "role/common/grade/gradeCertForPrint";
    }

    @RequestMapping("/graduation/graduationRequirements")
    public String graduationRequirements(Model model) {
        User studentUser = User.current();
        User advisor = userMapper.findOne(studentUser.getAdvisorId());
        model.addAttribute("advisor", advisor);
        int admissionYear = studentUser.getContact().getAdmissionYear();
        int divisionId = studentUser.getDivisionId();

        GraduationCriteria graduationCriteria = graduationCriteriaMapper.findOneByYearDivision(admissionYear, divisionId);
        studentUser.setGraduationCriteria(graduationCriteria == null ? new GraduationCriteria() : graduationCriteria);


        List<StudentCourse> studentCourses = studentCourseMapper.findByUserIdValid(studentUser.getId());
        studentUser.setStudentCourses(studentCourses);
        model.addAttribute("studentUser", studentUser);


        return "role/student/graduationRequirements/graduationRequirements";
    }

    @RequestMapping("/graduation/graduationRequirements/gradeDetail")
    public String requiredGradeDetail(Model model) {
        User studentUser = User.current();
        model.addAttribute("studentUser", studentUser);
        model.addAttribute("completeSemester", userService.getCompleteSemesterCount(studentUser.getId()));
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

        model.addAttribute("completeSemester", userService.getCompleteSemesterCount(studentUser.getId()));

        if (stored == null || stored.getApprove() == -1)
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
        GraduationResearchPlan stored = graduationResearchPlanMapper.findByUserId(studentUser.getId());
        if (stored == null) {
            graduationResearchPlan.setApprove(-1);
            graduationResearchPlanMapper.insert(graduationResearchPlan);
        } else {
            graduationResearchPlan.setId(stored.getId());
            graduationResearchPlanMapper.update(graduationResearchPlan);
        }
        sessionStatus.setComplete();
        return "redirect:/student/graduation/graduationResearchPlan?result=success";
    }


    private List<Integer> getYearList() {
        return semesterMapper.findYears();
    }

}
