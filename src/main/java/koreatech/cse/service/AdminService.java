package koreatech.cse.service;

import koreatech.cse.domain.*;
import koreatech.cse.domain.constant.Designation;
import koreatech.cse.domain.role.professor.*;
import koreatech.cse.domain.role.student.GraduationResearchPlan;
import koreatech.cse.domain.role.student.StudentCourse;
import koreatech.cse.domain.univ.*;
import koreatech.cse.repository.*;
import koreatech.cse.repository.provider.CqiMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.LinkedHashSet;
import java.util.List;

@Service
public class AdminService {

    // ===== Existing mappers =====
    @Inject private CourseMapper courseMapper;
    @Inject private GraduationCriteriaMapper graduationCriteriaMapper;
    @Inject private SemesterMapper semesterMapper;
    @Inject private DivisionMapper divisionMapper;
    @Inject private LectureMethodMapper lectureMethodMapper;
    @Inject private EvaluationMethodMapper evaluationMethodMapper;
    @Inject private EducationalMediumMapper educationalMediumMapper;
    @Inject private EquipmentMapper equipmentMapper;
    @Inject private ClassroomMapper classroomMapper;

    // ===== New mappers =====
    @Inject private UserMapper userMapper;
    @Inject private AuthorityMapper authorityMapper;
    @Inject private ContactMapper contactMapper;
    @Inject private UploadedFileMapper uploadedFileMapper;
    @Inject private CounselingMapper counselingMapper;
    @Inject private ProfessorCourseMapper professorCourseMapper;
    @Inject private StudentCourseMapper studentCourseMapper;
    @Inject private AssessmentFactorMapper assessmentFactorMapper;
    @Inject private AssessmentMapper assessmentMapper;
    @Inject private AltCourseMapper altCourseMapper;
    @Inject private ClassTimeMapper classTimeMapper;
    @Inject private LangCertMapper langCertMapper;
    @Inject private CertificateMapper certificateMapper;
    @Inject private MenuAccessMapper menuAccessMapper;
    @Inject private FeedbackMapper feedbackMapper;
    @Inject private GraduationResearchPlanMapper graduationResearchPlanMapper;
    @Inject private LectureFundamentalsMapper lectureFundamentalsMapper;
    @Inject private ProfLectureMethodMapper profLectureMethodMapper;
    @Inject private LectureContentsMapper lectureContentsMapper;
    @Inject private CqiMapper cqiMapper;

    // ===== User =====

    public User findUserById(int id) { return userMapper.findOne(id); }
    public User findUserByNumber(String number) { return userMapper.findByNumber(number); }
    public User findStudentByNumber(String number) { return userMapper.findStudentByNumber(number); }
    public List<User> findStudentBy(Searchable s) { return userMapper.findStudentBy(s); }
    public List<User> findProfessorBy(Searchable s) { return userMapper.findProfessorBy(s); }
    public List<User> findAllProfessors() { return userMapper.findAllProfessors(); }
    public List<User> findAllAdmins() { return userMapper.findAllAdmins(); }
    public List<User> findUsersByIds(List<Integer> ids) { return userMapper.findByUserIds(ids); }
    public List<User> findProfessorsByDivision(int divisionId) { return userMapper.findProfessorsByDivision(divisionId); }
    public List<User> findEligibleStudentsForCourseRegistration(int divisionId, int schoolYear, int profCourseId) {
        return userMapper.findEligibleStudentsForCourseRegistration(divisionId, schoolYear, profCourseId);
    }
    public List<User> findStudentsByAdvisorSchoolYearDivisionExceptRegistered(
            List<Integer> userIds, String number, String name, int division) {
        return userMapper.findStudentsByAdvisorSchoolYearDivisionExceptRegistered(userIds, number, name, division);
    }

    @Transactional public void insertUser(User user) { userMapper.insert(user); }
    @Transactional public void updateUser(User user) { userMapper.update(user); }
    @Transactional public void updateUserFromSignup(User user) { userMapper.updateFromSignup(user); }
    @Transactional public void updateUserAccountState(User user) { userMapper.updateAccountState(user); }
    @Transactional public void deleteUser(User user) { userMapper.delete(user); }

    // ===== Authority =====

    public Authority findAuthorityByUserIdAndRole(int userId, String role) {
        return authorityMapper.findByUserIdAndRole(userId, role);
    }

    // ===== Contact =====

    public Contact findContactByUserId(int userId) { return contactMapper.findByUserId(userId); }
    @Transactional public void insertContact(Contact contact) { contactMapper.insert(contact); }
    @Transactional public void updateContact(Contact contact) { contactMapper.update(contact); }

    // ===== UploadedFile =====

    public UploadedFile findUploadedFileById(int id) { return uploadedFileMapper.findOne(id); }
    public List<UploadedFile> findUploadedFilesByDesignationProfCourseId(Designation designation, int profCourseId) {
        return uploadedFileMapper.findByDesignationProfCourseId(designation, profCourseId);
    }
    @Transactional public void deleteUploadedFile(UploadedFile uploadedFile) { uploadedFileMapper.delete(uploadedFile); }
    @Transactional public void deleteProfileByUser(User user) { uploadedFileMapper.deleteProfileByUser(user); }

    // ===== Counseling =====

    public Counseling findCounselingById(int id) { return counselingMapper.findOne(id); }
    public List<Counseling> findCounselingBy(Searchable s) { return counselingMapper.findByCounseling(s); }
    public List<Counseling> findCounselingsByIds(List<Integer> ids) { return counselingMapper.findByIds(ids); }
    @Transactional public void insertCounseling(Counseling counseling) { counselingMapper.insert(counseling); }

    // ===== ProfessorCourse =====

    public ProfessorCourse findProfessorCourseById(int id) { return professorCourseMapper.findOne(id); }
    public List<ProfessorCourse> findProfessorCoursesByCourseId(int courseId) { return professorCourseMapper.findByCourseId(courseId); }
    public List<ProfessorCourse> findProfessorCoursesByClassroomId(int id) { return professorCourseMapper.findByClassroomId(id); }
    public List<ProfessorCourse> findProfessorCoursesBy(Searchable s) { return professorCourseMapper.findBy(s); }
    public List<ProfessorCourse> findProfessorCoursesByFlat(Searchable s) { return professorCourseMapper.findByFlat(s); }
    public Integer countProfessorCoursesByCourseId(int courseId) { return professorCourseMapper.countByCourseId(courseId); }
    @Transactional public void insertProfessorCourse(ProfessorCourse pc) { professorCourseMapper.insert(pc); }
    @Transactional public void updateProfessorCourse(ProfessorCourse pc) { professorCourseMapper.update(pc); }
    @Transactional public void deleteProfessorCourse(ProfessorCourse pc) { professorCourseMapper.delete(pc); }

    // ===== StudentCourse =====

    public List<StudentCourse> findStudentCoursesByProfCourseId(int profCourseId) { return studentCourseMapper.findByProfCourseId(profCourseId); }
    public List<StudentCourse> findStudentCoursesByUserId(int userId) { return studentCourseMapper.findByUserId(userId); }
    public List<StudentCourse> findStudentCoursesByUserIdValid(int userId) { return studentCourseMapper.findByUserIdValid(userId); }
    public List<StudentCourse> findStudentCoursesByUserIdYearSemester(Searchable s) { return studentCourseMapper.findByUserIdYearSemester(s); }
    public List<StudentCourse> findStudentCoursesByUserIdSemesterIdValid(int userId, int semesterId) { return studentCourseMapper.findByUserIdSemesterIdValid(userId, semesterId); }
    public List<StudentCourse> findStudentClassAssessmentByUserIdYearSemester(Searchable s) { return studentCourseMapper.findClassAssessmentByUserIdYearSemester(s); }
    public List<StudentCourse> findStudentInquiryCoursesByUserIdValid(int userId) { return studentCourseMapper.findInquiryByUserIdValid(userId); }
    public List<StudentCourse> findStudentInquiryCoursesByUserIdSemesterIdValid(int userId, int semesterId) { return studentCourseMapper.findInquiryByUserIdSemesterIdValid(userId, semesterId); }
    public LinkedHashSet<Integer> findSemesterIdsByUserIdValid(int userId) { return studentCourseMapper.findSemesterIdByUserIdValid(userId); }
    public List<StudentCourse> findFlatStudentCoursesByProfCourseIdsValid(List<Integer> ids) { return studentCourseMapper.findFlatByProfCourseIdsValid(ids); }
    public StudentCourse findStudentCourseByUserIdProfCourseId(int userId, int profCourseId) { return studentCourseMapper.findByUserIdProfCourseId(userId, profCourseId); }
    public List<Integer> findStudentUserIdsByCourseId(int courseId) { return studentCourseMapper.findUserIdsByCourseId(courseId); }
    public List<StudentCourse> findStudentCoursesByProfCourseIdValid(int profCourseId) { return studentCourseMapper.findByProfCourseIdValid(profCourseId); }
    public boolean existsStudentCourseByUserIdAndProfCourseId(int userId, int profCourseId) { return studentCourseMapper.existsByUserIdAndProfCourseId(userId, profCourseId); }
    public Integer countStudentCoursesByProfCourseId(int profCourseId) { return studentCourseMapper.countByProfCourseId(profCourseId); }
    @Transactional public void insertStudentCourse(StudentCourse sc) { studentCourseMapper.insert(sc); }
    @Transactional public void updateStudentCourse(StudentCourse sc) { studentCourseMapper.update(sc); }
    @Transactional public void deleteStudentCourse(StudentCourse sc) { studentCourseMapper.delete(sc); }

    // ===== AssessmentFactor =====

    public List<AssessmentFactor> findAssessmentFactorsByCourseId(int courseId) { return assessmentFactorMapper.findByCourseId(courseId); }
    public List<AssessmentFactor> findAssessmentFactorsByCourseIdEnabled(int courseId) { return assessmentFactorMapper.findByCourseIdEnabled(courseId); }
    public AssessmentFactor findAssessmentFactorById(int id) { return assessmentFactorMapper.findOne(id); }
    @Transactional public void insertAssessmentFactor(AssessmentFactor af) { assessmentFactorMapper.insert(af); }
    @Transactional public void updateAssessmentFactor(AssessmentFactor af) { assessmentFactorMapper.update(af); }
    @Transactional public void deleteAssessmentFactor(AssessmentFactor af) { assessmentFactorMapper.delete(af); }

    // ===== Assessment =====

    public List<Assessment> findAssessmentsByProfCourseId(int profCourseId) { return assessmentMapper.findByProfCourseId(profCourseId); }
    public List<Assessment> findAssessmentsByAssessmentFactorId(int id) { return assessmentMapper.findByAssessmentFactorId(id); }
    public Assessment findAssessmentByUserIdProfCourseId(int userId, int profCourseId) { return assessmentMapper.findByUserIdProfCourseId(userId, profCourseId); }
    public List<Assessment> findAssessmentsByUserIdAndProfCourseIds(int userId, List<Integer> ids) { return assessmentMapper.findByUserIdAndProfCourseIds(userId, ids); }
    public List<Assessment> findAssessmentsByProfCourseIds(List<Integer> ids) { return assessmentMapper.findByProfCourseIds(ids); }

    // ===== AltCourse =====

    public List<AltCourse> findAltCoursesByTargetCourseId(int id) { return altCourseMapper.findByTargetCourseId(id); }
    public AltCourse findAltCourseByCourseIdTargetCourseIdType(int id, int targetId, String type) { return altCourseMapper.findByCourseIdTargetCourseIdType(id, targetId, type); }
    public AltCourse findAltCourseById(int id) { return altCourseMapper.findOne(id); }
    @Transactional public void insertAltCourse(AltCourse altCourse) { altCourseMapper.insert(altCourse); }
    @Transactional public void deleteAltCourse(AltCourse altCourse) { altCourseMapper.delete(altCourse); }

    // ===== ClassTime =====

    public List<ClassTime> findClassTimesByProfCourseId(int profCourseId) { return classTimeMapper.findByProfCourseId(profCourseId); }
    public ClassTime findClassTimeById(int id) { return classTimeMapper.findOne(id); }
    @Transactional public void insertClassTime(ClassTime classTime) { classTimeMapper.insert(classTime); }
    @Transactional public void deleteClassTime(ClassTime classTime) { classTimeMapper.delete(classTime); }

    // ===== LangCert =====

    public List<LangCert> findLangCertsByUserId(int userId) { return langCertMapper.findByUserId(userId); }
    public LangCert findLangCertById(int id) { return langCertMapper.findOne(id); }
    @Transactional public void insertLangCert(LangCert langCert) { langCertMapper.insert(langCert); }
    @Transactional public void deleteLangCert(LangCert langCert) { langCertMapper.delete(langCert); }

    // ===== Certificate =====

    public Certificate findCertificateByUserId(int userId) { return certificateMapper.findByUserId(userId); }

    // ===== MenuAccess =====

    public MenuAccess findMenuAccess() { return menuAccessMapper.findOne(); }
    @Transactional public void updateMenuAccess(MenuAccess menuAccess) { menuAccessMapper.update(menuAccess); }

    // ===== Feedback =====

    public List<Feedback> findRecentFeedback() { return feedbackMapper.findRecent(); }
    public Feedback findFeedbackById(int id) { return feedbackMapper.findOne(id); }
    @Transactional public void insertFeedback(Feedback feedback) { feedbackMapper.insert(feedback); }
    @Transactional public void deleteFeedback(Feedback feedback) { feedbackMapper.delete(feedback); }

    // ===== GraduationResearchPlan =====

    public GraduationResearchPlan findGraduationResearchPlanByUserId(int userId) { return graduationResearchPlanMapper.findByUserId(userId); }
    public GraduationResearchPlan findGraduationResearchPlanById(int id) { return graduationResearchPlanMapper.findOne(id); }
    public List<GraduationResearchPlan> findGraduationResearchPlansBySearchable(Searchable s) { return graduationResearchPlanMapper.findBySearchable(s); }
    public List<GraduationResearchPlan> findGraduationResearchPlansByIds(List<Integer> ids) { return graduationResearchPlanMapper.findByIds(ids); }
    @Transactional public void insertGraduationResearchPlan(GraduationResearchPlan plan) { graduationResearchPlanMapper.insert(plan); }
    @Transactional public void updateGraduationResearchPlan(GraduationResearchPlan plan) { graduationResearchPlanMapper.update(plan); }
    @Transactional public void deleteGraduationResearchPlan(GraduationResearchPlan plan) { graduationResearchPlanMapper.delete(plan); }

    // ===== LectureFundamentals =====

    public LectureFundamentals findLectureFundamentalsByProfCourseId(int profCourseId) { return lectureFundamentalsMapper.findByProfCourseId(profCourseId); }
    @Transactional public void insertLectureFundamentals(LectureFundamentals lf) { lectureFundamentalsMapper.insert(lf); }
    @Transactional public void updateLectureFundamentals(LectureFundamentals lf) { lectureFundamentalsMapper.update(lf); }

    // ===== ProfLectureMethod =====

    public ProfLectureMethod findProfLectureMethodByProfCourseId(int profCourseId) { return profLectureMethodMapper.findByProfCourseId(profCourseId); }
    public ProfLectureMethod findProfLectureMethodByLectureMethodId(String id) { return profLectureMethodMapper.findByLectureMethodId(id); }
    public ProfLectureMethod findProfLectureMethodByEvaluationMethodId(String id) { return profLectureMethodMapper.findByEvaluationMethodId(id); }
    public ProfLectureMethod findProfLectureMethodByEducationalMediumId(String id) { return profLectureMethodMapper.findByEducationalMediumId(id); }
    public ProfLectureMethod findProfLectureMethodByEquipmentId(String id) { return profLectureMethodMapper.findByEquipmentId(id); }
    @Transactional public void insertProfLectureMethod(ProfLectureMethod plm) { profLectureMethodMapper.insert(plm); }
    @Transactional public void updateProfLectureMethod(ProfLectureMethod plm) { profLectureMethodMapper.update(plm); }

    // ===== LectureContents =====

    public LectureContents findLectureContentsByProfCourseId(int profCourseId) { return lectureContentsMapper.findByProfCourseId(profCourseId); }
    @Transactional public void insertLectureContents(LectureContents lc) { lectureContentsMapper.insert(lc); }
    @Transactional public void updateLectureContents(LectureContents lc) { lectureContentsMapper.update(lc); }

    // ===== Cqi =====

    public Cqi findCqiByProfCourseId(int profCourseId) { return cqiMapper.findByProfCourseId(profCourseId); }
    public Cqi findCqiByYearCourseIdDivide(int year, int courseId, int divide) { return cqiMapper.findByYearCourseIdDivide(year, courseId, divide); }
    public List<Cqi> findCqisFlatByYearsCourseIdDivide(List<Integer> years, int courseId, int divide) { return cqiMapper.findFlatByYearsCourseIdDivide(years, courseId, divide); }
    @Transactional public void insertCqi(Cqi cqi) { cqiMapper.insert(cqi); }
    @Transactional public void updateCqi(Cqi cqi) { cqiMapper.update(cqi); }

    // ===== Course (read + write delegates) =====

    public Course findCourseById(int id) { return courseMapper.findOne(id); }
    public List<Course> findAllCourses() { return courseMapper.findAll(); }
    public List<Course> findAllEnabledCourses() { return courseMapper.findAllEnabled(); }
    public List<Course> findCoursesByDivision(int divisionId) { return courseMapper.findByDivision(divisionId); }
    public List<Course> findCoursesBy(Searchable s) { return courseMapper.findBy(s); }
    @Transactional public void insertCourse(Course course) { courseMapper.insert(course); }
    @Transactional public void updateCourse(Course course) { courseMapper.update(course); }
    @Transactional public void deleteCourse(Course course) { courseMapper.delete(course); }

    // ===== GraduationCriteria (read + write delegates) =====

    public GraduationCriteria findGraduationCriteriaById(int id) { return graduationCriteriaMapper.findOne(id); }
    public GraduationCriteria findGraduationCriteriaByYearDivision(int year, int divisionId) { return graduationCriteriaMapper.findOneByYearDivision(year, divisionId); }
    public List<GraduationCriteria> findGraduationCriteriaListByYearDivision(Searchable s) { return graduationCriteriaMapper.findByYearDivision(s); }
    @Transactional public void insertGraduationCriteria(GraduationCriteria gc) { graduationCriteriaMapper.insert(gc); }
    @Transactional public void updateGraduationCriteria(GraduationCriteria gc) { graduationCriteriaMapper.update(gc); }
    @Transactional public void deleteGraduationCriteria(GraduationCriteria gc) { graduationCriteriaMapper.delete(gc); }

    // ===== Semester (read + write delegates) =====

    public Semester findSemesterById(int id) { return semesterMapper.findOne(id); }
    public List<Semester> findAllSemesters() { return semesterMapper.findAll(); }
    public List<Semester> findSemestersByIds(List<Integer> ids) { return semesterMapper.findByIds(ids); }
    public List<Integer> findSemesterYears() { return semesterMapper.findYears(); }
    public Semester findSemesterByYearSemester(int year, int semester) { return semesterMapper.findByYearSemester(year, semester); }
    @Transactional public void insertSemester(Semester semester) { semesterMapper.insert(semester); }
    @Transactional public void updateSemesterDirect(Semester semester) { semesterMapper.update(semester); }
    @Transactional public void deleteSemester(Semester semester) { semesterMapper.delete(semester); }

    // ===== Division (read + write delegates) =====

    public Division findDivisionById(int id) { return divisionMapper.findOne(id); }
    public List<Division> findAllDivisions() { return divisionMapper.findAll(); }
    @Transactional public void insertDivision(Division division) { divisionMapper.insert(division); }
    @Transactional public void updateDivision(Division division) { divisionMapper.update(division); }
    @Transactional public void deleteDivision(Division division) { divisionMapper.delete(division); }

    // ===== LectureMethod (read + write delegates) =====

    public LectureMethod findLectureMethodById(int id) { return lectureMethodMapper.findOne(id); }
    public List<LectureMethod> findAllLectureMethods() { return lectureMethodMapper.findAll(); }
    public List<LectureMethod> findAllEnabledLectureMethods() { return lectureMethodMapper.findAllEnabled(); }
    @Transactional public void insertLectureMethod(LectureMethod lm) { lectureMethodMapper.insert(lm); }
    @Transactional public void updateLectureMethod(LectureMethod lm) { lectureMethodMapper.update(lm); }
    @Transactional public void deleteLectureMethod(LectureMethod lm) { lectureMethodMapper.delete(lm); }

    // ===== EvaluationMethod (read + write delegates) =====

    public EvaluationMethod findEvaluationMethodById(int id) { return evaluationMethodMapper.findOne(id); }
    public List<EvaluationMethod> findAllEvaluationMethods() { return evaluationMethodMapper.findAll(); }
    public List<EvaluationMethod> findAllEnabledEvaluationMethods() { return evaluationMethodMapper.findAllEnabled(); }
    @Transactional public void insertEvaluationMethod(EvaluationMethod em) { evaluationMethodMapper.insert(em); }
    @Transactional public void updateEvaluationMethod(EvaluationMethod em) { evaluationMethodMapper.update(em); }
    @Transactional public void deleteEvaluationMethod(EvaluationMethod em) { evaluationMethodMapper.delete(em); }

    // ===== EducationalMedium (read + write delegates) =====

    public EducationalMedium findEducationalMediumById(int id) { return educationalMediumMapper.findOne(id); }
    public List<EducationalMedium> findAllEducationalMediums() { return educationalMediumMapper.findAll(); }
    public List<EducationalMedium> findAllEnabledEducationalMediums() { return educationalMediumMapper.findAllEnabled(); }
    @Transactional public void insertEducationalMedium(EducationalMedium em) { educationalMediumMapper.insert(em); }
    @Transactional public void updateEducationalMedium(EducationalMedium em) { educationalMediumMapper.update(em); }
    @Transactional public void deleteEducationalMedium(EducationalMedium em) { educationalMediumMapper.delete(em); }

    // ===== Equipment (read + write delegates) =====

    public Equipment findEquipmentById(int id) { return equipmentMapper.findOne(id); }
    public List<Equipment> findAllEquipments() { return equipmentMapper.findAll(); }
    public List<Equipment> findAllEnabledEquipments() { return equipmentMapper.findAllEnabled(); }
    @Transactional public void insertEquipment(Equipment e) { equipmentMapper.insert(e); }
    @Transactional public void updateEquipment(Equipment e) { equipmentMapper.update(e); }
    @Transactional public void deleteEquipment(Equipment e) { equipmentMapper.delete(e); }

    // ===== Classroom (read + write delegates) =====

    public Classroom findClassroomById(int id) { return classroomMapper.findOne(id); }
    public List<Classroom> findAllClassrooms() { return classroomMapper.findAll(); }
    public List<Classroom> findAllEnabledClassrooms() { return classroomMapper.findAllEnabled(); }
    @Transactional public void insertClassroom(Classroom c) { classroomMapper.insert(c); }
    @Transactional public void updateClassroom(Classroom c) { classroomMapper.update(c); }
    @Transactional public void deleteClassroom(Classroom c) { classroomMapper.delete(c); }

    // ===== Fine-grained Course update methods (existing) =====

    @Transactional
    public void updateCourseCode(int id, String code) {
        Course course = courseMapper.findOne(id);
        course.setCode(code);
        courseMapper.update(course);
    }

    @Transactional
    public void updateCourseTitle(int id, String title) {
        Course course = courseMapper.findOne(id);
        course.setTitle(title);
        courseMapper.update(course);
    }

    @Transactional
    public void updateCourseSubjCategory(int id, String subjCategory) {
        Course course = courseMapper.findOne(id);
        course.setSubjCategory(subjCategory);
        courseMapper.update(course);
    }

    @Transactional
    public void updateCourseOverview(int id, String overview) {
        Course course = courseMapper.findOne(id);
        course.setOverview(overview);
        courseMapper.update(course);
    }

    @Transactional
    public void updateCourseLearningObjective(int id, String objective) {
        Course course = courseMapper.findOne(id);
        course.setLearningObjective(objective);
        courseMapper.update(course);
    }

    @Transactional
    public void updateCourseCredit(int id, int credit) {
        Course course = courseMapper.findOne(id);
        course.setCredit(credit);
        courseMapper.update(course);
    }

    @Transactional
    public void updateCourseLec(int id, int lec) {
        Course course = courseMapper.findOne(id);
        course.setLec(lec);
        courseMapper.update(course);
    }

    @Transactional
    public void updateCourseTut(int id, int tut) {
        Course course = courseMapper.findOne(id);
        course.setTut(tut);
        courseMapper.update(course);
    }

    @Transactional
    public void updateCourseLab(int id, int lab) {
        Course course = courseMapper.findOne(id);
        course.setLab(lab);
        courseMapper.update(course);
    }

    @Transactional
    public void updateCourseWs(int id, int ws) {
        Course course = courseMapper.findOne(id);
        course.setWs(ws);
        courseMapper.update(course);
    }

    @Transactional
    public void updateCourseSchoolYear(int id, int schoolYear) {
        Course course = courseMapper.findOne(id);
        course.setSchoolYear(schoolYear);
        courseMapper.update(course);
    }

    @Transactional
    public void updateCourseAchieve(int id, int achieveNum, boolean value) {
        Course course = courseMapper.findOne(id);
        switch (achieveNum) {
            case 1:  course.setAchieve1(value);  break;
            case 2:  course.setAchieve2(value);  break;
            case 3:  course.setAchieve3(value);  break;
            case 4:  course.setAchieve4(value);  break;
            case 5:  course.setAchieve5(value);  break;
            case 6:  course.setAchieve6(value);  break;
            case 7:  course.setAchieve7(value);  break;
            case 8:  course.setAchieve8(value);  break;
            case 9:  course.setAchieve9(value);  break;
            case 10: course.setAchieve10(value); break;
            default: throw new IllegalArgumentException("Invalid achieve number: " + achieveNum);
        }
        courseMapper.update(course);
    }

    // ===== Fine-grained GraduationCriteria update methods (existing) =====

    @Transactional
    public void updateGraduationCriteriaYear(int id, int year) {
        GraduationCriteria gc = graduationCriteriaMapper.findOne(id);
        gc.setYear(year);
        graduationCriteriaMapper.update(gc);
    }

    @Transactional
    public void updateGraduationCriteriaMsc(int id, int msc) {
        GraduationCriteria gc = graduationCriteriaMapper.findOne(id);
        gc.setMsc(msc);
        graduationCriteriaMapper.update(gc);
    }

    @Transactional
    public void updateGraduationCriteriaLiberal(int id, int liberal) {
        GraduationCriteria gc = graduationCriteriaMapper.findOne(id);
        gc.setLiberal(liberal);
        graduationCriteriaMapper.update(gc);
    }

    @Transactional
    public void updateGraduationCriteriaMajor(int id, int major) {
        GraduationCriteria gc = graduationCriteriaMapper.findOne(id);
        gc.setMajor(major);
        graduationCriteriaMapper.update(gc);
    }

    // ===== Fine-grained Semester update methods (existing) =====

    @Transactional
    public boolean updateSemesterYear(int id, int year) {
        Semester semester = semesterMapper.findOne(id);
        semester.setYear(year);
        Semester conflict = semesterMapper.findByYearSemester(year, semester.getSemester());
        if (conflict == null) {
            semesterMapper.update(semester);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean updateSemesterNumber(int id, int semesterNumber) {
        Semester semester = semesterMapper.findOne(id);
        semester.setSemester(semesterNumber);
        Semester conflict = semesterMapper.findByYearSemester(semester.getYear(), semesterNumber);
        if (conflict == null) {
            semesterMapper.update(semester);
            return true;
        }
        return false;
    }

    // ===== Fine-grained Division update methods (existing) =====

    @Transactional
    public void updateDivisionName(int id, String name) {
        Division division = divisionMapper.findOne(id);
        division.setName(name);
        divisionMapper.update(division);
    }

    // ===== Fine-grained LectureMethod update methods (existing) =====

    @Transactional
    public void updateLectureMethodName(int id, String name) {
        LectureMethod lectureMethod = lectureMethodMapper.findOne(id);
        lectureMethod.setName(name);
        lectureMethodMapper.update(lectureMethod);
    }

    // ===== Fine-grained EvaluationMethod update methods (existing) =====

    @Transactional
    public void updateEvaluationMethodName(int id, String name) {
        EvaluationMethod evaluationMethod = evaluationMethodMapper.findOne(id);
        evaluationMethod.setName(name);
        evaluationMethodMapper.update(evaluationMethod);
    }

    // ===== Fine-grained EducationalMedium update methods (existing) =====

    @Transactional
    public void updateEducationalMediumName(int id, String name) {
        EducationalMedium educationalMedium = educationalMediumMapper.findOne(id);
        educationalMedium.setName(name);
        educationalMediumMapper.update(educationalMedium);
    }

    // ===== Fine-grained Equipment update methods (existing) =====

    @Transactional
    public void updateEquipmentName(int id, String name) {
        Equipment equipment = equipmentMapper.findOne(id);
        equipment.setName(name);
        equipmentMapper.update(equipment);
    }

    @Transactional
    public void updateEquipmentCode(int id, String code) {
        Equipment equipment = equipmentMapper.findOne(id);
        equipment.setCode(code);
        equipmentMapper.update(equipment);
    }

    // ===== Fine-grained Classroom update methods (existing) =====

    @Transactional
    public void updateClassroomName(int id, String name) {
        Classroom classroom = classroomMapper.findOne(id);
        classroom.setName(name);
        classroomMapper.update(classroom);
    }

    @Transactional
    public void updateClassroomCode(int id, String code) {
        Classroom classroom = classroomMapper.findOne(id);
        classroom.setCode(code);
        classroomMapper.update(classroom);
    }
}
