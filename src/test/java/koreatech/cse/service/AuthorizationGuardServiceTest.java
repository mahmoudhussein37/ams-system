package koreatech.cse.service;

import koreatech.cse.domain.User;
import koreatech.cse.domain.dto.GradeUpdateDTO;
import koreatech.cse.domain.role.professor.Assessment;
import koreatech.cse.domain.role.professor.LectureFundamentals;
import koreatech.cse.domain.role.professor.ProfessorCourse;
import koreatech.cse.domain.role.student.GraduationResearchPlan;
import koreatech.cse.domain.role.student.StudentCourse;
import koreatech.cse.repository.AssessmentMapper;
import koreatech.cse.repository.CounselingMapper;
import koreatech.cse.repository.GraduationResearchPlanMapper;
import koreatech.cse.repository.LectureContentsMapper;
import koreatech.cse.repository.LectureFundamentalsMapper;
import koreatech.cse.repository.ProfLectureMethodMapper;
import koreatech.cse.repository.ProfessorCourseMapper;
import koreatech.cse.repository.StudentCourseMapper;
import koreatech.cse.repository.UserMapper;
import koreatech.cse.repository.provider.CqiMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthorizationGuardServiceTest {

    @Mock
    private ProfessorCourseMapper professorCourseMapper;
    @Mock
    private CounselingMapper counselingMapper;
    @Mock
    private StudentCourseMapper studentCourseMapper;
    @Mock
    private LectureFundamentalsMapper lectureFundamentalsMapper;
    @Mock
    private ProfLectureMethodMapper profLectureMethodMapper;
    @Mock
    private LectureContentsMapper lectureContentsMapper;
    @Mock
    private CqiMapper cqiMapper;
    @Mock
    private UserMapper userMapper;
    @Mock
    private GraduationResearchPlanMapper graduationResearchPlanMapper;
    @Mock
    private AssessmentMapper assessmentMapper;

    private AuthorizationGuardService authorizationGuardService;

    @Before
    public void setUp() {
        authorizationGuardService = new AuthorizationGuardService();
        ReflectionTestUtils.setField(authorizationGuardService, "professorCourseMapper", professorCourseMapper);
        ReflectionTestUtils.setField(authorizationGuardService, "counselingMapper", counselingMapper);
        ReflectionTestUtils.setField(authorizationGuardService, "studentCourseMapper", studentCourseMapper);
        ReflectionTestUtils.setField(authorizationGuardService, "lectureFundamentalsMapper", lectureFundamentalsMapper);
        ReflectionTestUtils.setField(authorizationGuardService, "profLectureMethodMapper", profLectureMethodMapper);
        ReflectionTestUtils.setField(authorizationGuardService, "lectureContentsMapper", lectureContentsMapper);
        ReflectionTestUtils.setField(authorizationGuardService, "cqiMapper", cqiMapper);
        ReflectionTestUtils.setField(authorizationGuardService, "userMapper", userMapper);
        ReflectionTestUtils.setField(authorizationGuardService, "graduationResearchPlanMapper", graduationResearchPlanMapper);
        ReflectionTestUtils.setField(authorizationGuardService, "assessmentMapper", assessmentMapper);
    }

    @Test(expected = AccessDeniedException.class)
    public void professorACannotAccessProfessorBCourse() {
        User professorA = createUser(1001);
        ProfessorCourse professorBCourse = new ProfessorCourse();
        professorBCourse.setId(77);
        professorBCourse.setUserId(2002);

        when(professorCourseMapper.findOne(77)).thenReturn(professorBCourse);

        authorizationGuardService.requireOwnedProfessorCourse(77, professorA, "syllabus-course-detail");
    }

    @Test(expected = AccessDeniedException.class)
    public void professorACannotEditUnrelatedStudentGrade() {
        User professorA = createUser(1001);
        StudentCourse targetStudentCourse = new StudentCourse();
        targetStudentCourse.setId(11);
        targetStudentCourse.setProfCourseId(501);
        targetStudentCourse.setUserId(3300);

        ProfessorCourse professorBCourse = new ProfessorCourse();
        professorBCourse.setId(501);
        professorBCourse.setUserId(2002);

        GradeUpdateDTO dto = new GradeUpdateDTO();
        dto.setScore(15);
        dto.setGrade("scoreMid");

        when(studentCourseMapper.findOne(11)).thenReturn(targetStudentCourse);
        when(professorCourseMapper.findOne(501)).thenReturn(professorBCourse);

        authorizationGuardService.updateGrade(11, dto, professorA);

        verify(studentCourseMapper, never()).update(targetStudentCourse);
    }

    @Test(expected = AccessDeniedException.class)
    public void wrongProfessorCannotOpenCounselingDetail() {
        User professorA = createUser(1001);

        when(counselingMapper.findOneByIdAndProfessorUserId(41, 1001)).thenReturn(null);

        authorizationGuardService.requireOwnedCounseling(41, professorA, "counseling-detail");
    }

    @Test(expected = AccessDeniedException.class)
    public void studentCannotAccessUnrelatedCourseAssessment() {
        User studentA = createUser(1001);
        StudentCourse studentBCourse = new StudentCourse();
        studentBCourse.setId(900);
        studentBCourse.setUserId(2222);

        when(studentCourseMapper.findOne(900)).thenReturn(studentBCourse);

        authorizationGuardService.requireOwnedStudentCourseForStudent(900, studentA, "student-class-assessment-detail");
    }

    @Test(expected = AccessDeniedException.class)
    public void professorCannotUseForeignLectureFundamentalsIdEvenWithOwnedCourseParam() {
        User professorA = createUser(1001);

        ProfessorCourse ownedCourse = new ProfessorCourse();
        ownedCourse.setId(10);
        ownedCourse.setUserId(1001);

        LectureFundamentals foreignLectureFundamentals = new LectureFundamentals();
        foreignLectureFundamentals.setId(501);
        foreignLectureFundamentals.setProfCourseId(999);

        when(professorCourseMapper.findOne(10)).thenReturn(ownedCourse);
        when(lectureFundamentalsMapper.findOne(501)).thenReturn(foreignLectureFundamentals);

        authorizationGuardService.requireOwnedLectureFundamentals(501, 10, professorA,
                "syllabus-lecture-fundamentals-update");
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateGradeRejectsOutOfRangeScoreValues() {
        User professorA = createUser(1001);

        StudentCourse ownedStudentCourse = new StudentCourse();
        ownedStudentCourse.setId(11);
        ownedStudentCourse.setProfCourseId(501);

        ProfessorCourse ownedCourse = new ProfessorCourse();
        ownedCourse.setId(501);
        ownedCourse.setUserId(1001);

        GradeUpdateDTO dto = new GradeUpdateDTO();
        dto.setScore(101);
        dto.setGrade("scoreMid");

        when(studentCourseMapper.findOne(11)).thenReturn(ownedStudentCourse);
        when(professorCourseMapper.findOne(501)).thenReturn(ownedCourse);

        authorizationGuardService.updateGrade(11, dto, professorA);
    }

    @Test(expected = AccessDeniedException.class)
    public void professorCannotAccessUnadvisedStudentDetailThroughGuard() {
        User professorA = createUser(1001);

        User unadvisedStudent = createUser(3010);
        unadvisedStudent.setAdvisorId(2002);

        when(userMapper.findOne(3010)).thenReturn(unadvisedStudent);

        authorizationGuardService.requireAdvisedStudent(3010, professorA, "student-lookup-detail");
    }

    @Test
    public void professorCanAccessAdvisedStudentDetailThroughGuard() {
        User professorA = createUser(1001);

        User advisedStudent = createUser(3010);
        advisedStudent.setAdvisorId(1001);

        when(userMapper.findOne(3010)).thenReturn(advisedStudent);

        User guardedStudent = authorizationGuardService.requireAdvisedStudent(3010, professorA,
                "student-lookup-detail");

        assertSame(advisedStudent, guardedStudent);
    }

    @Test(expected = AccessDeniedException.class)
    public void professorCannotAccessForeignGraduationResearchPlan() {
        User professorA = createUser(1001);

        GraduationResearchPlan foreignPlan = new GraduationResearchPlan();
        foreignPlan.setId(7001);
        foreignPlan.setUserId(3010);

        User foreignStudent = createUser(3010);
        foreignStudent.setAdvisorId(2002);

        when(graduationResearchPlanMapper.findOne(7001)).thenReturn(foreignPlan);
        when(userMapper.findOne(3010)).thenReturn(foreignStudent);

        authorizationGuardService.requireOwnedGraduationResearchPlanForProfessor(7001, professorA,
                "graduation-plan-detail");
    }

    @Test
    public void professorCanAccessAdvisedStudentGraduationResearchPlan() {
        User professorA = createUser(1001);

        GraduationResearchPlan advisedPlan = new GraduationResearchPlan();
        advisedPlan.setId(7001);
        advisedPlan.setUserId(3010);

        User advisedStudent = createUser(3010);
        advisedStudent.setAdvisorId(1001);

        when(graduationResearchPlanMapper.findOne(7001)).thenReturn(advisedPlan);
        when(userMapper.findOne(3010)).thenReturn(advisedStudent);

        GraduationResearchPlan guardedPlan = authorizationGuardService
                .requireOwnedGraduationResearchPlanForProfessor(7001, professorA, "graduation-plan-detail");

        assertSame(advisedPlan, guardedPlan);
    }

    @Test(expected = AccessDeniedException.class)
    public void submitAssessmentDeniedForForeignStudentCourse() {
        User studentA = createUser(1001);

        StudentCourse studentBCourse = new StudentCourse();
        studentBCourse.setId(900);
        studentBCourse.setUserId(2222);

        when(studentCourseMapper.findOne(900)).thenReturn(studentBCourse);

        authorizationGuardService.submitAssessmentForOwnedStudentCourse(900, new Assessment(), studentA);

        verify(assessmentMapper, never()).insert(org.mockito.ArgumentMatchers.any(Assessment.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void submitAssessmentRejectsNullPayloadEvenForOwnedCourse() {
        User studentA = createUser(1001);

        StudentCourse ownedCourse = new StudentCourse();
        ownedCourse.setId(900);
        ownedCourse.setUserId(1001);
        ownedCourse.setProfCourseId(55);

        when(studentCourseMapper.findOne(900)).thenReturn(ownedCourse);

        authorizationGuardService.submitAssessmentForOwnedStudentCourse(900, null, studentA);
    }

    @Test
    public void submitAssessmentSetsServerSideOwnershipBeforeInsert() {
        User studentA = createUser(1001);

        StudentCourse ownedCourse = new StudentCourse();
        ownedCourse.setId(900);
        ownedCourse.setUserId(1001);
        ownedCourse.setProfCourseId(55);

        Assessment assessment = new Assessment();
        assessment.setUserId(9999);
        assessment.setProfCourseId(7777);

        when(studentCourseMapper.findOne(900)).thenReturn(ownedCourse);

        authorizationGuardService.submitAssessmentForOwnedStudentCourse(900, assessment, studentA);

        assertEquals(1001, assessment.getUserId());
        assertEquals(55, assessment.getProfCourseId());
        verify(assessmentMapper).insert(assessment);
    }

    private User createUser(int id) {
        User user = new User();
        user.setId(id);
        return user;
    }
}
