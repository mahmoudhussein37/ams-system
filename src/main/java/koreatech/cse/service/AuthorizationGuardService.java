package koreatech.cse.service;

import koreatech.cse.domain.User;
import koreatech.cse.domain.dto.GradeUpdateDTO;
import koreatech.cse.domain.role.professor.Assessment;
import koreatech.cse.domain.role.professor.Cqi;
import koreatech.cse.domain.role.professor.Counseling;
import koreatech.cse.domain.role.professor.LectureFundamentals;
import koreatech.cse.domain.role.professor.LectureContents;
import koreatech.cse.domain.role.professor.ProfLectureMethod;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Service
public class AuthorizationGuardService {
    private static final Logger logger = LoggerFactory.getLogger(AuthorizationGuardService.class);

    private static final Set<String> ALLOWED_SCORE_FIELDS = new HashSet<>(Arrays.asList(
            "scoreAssignment",
            "scoreMid",
            "scoreFinal",
            "scoreOptions"));

    private static final Set<String> ALLOWED_GRADES = new HashSet<>(Arrays.asList(
            "A", "B", "C", "D", "E", "F", "G", "S", "U"));

    private static final int MIN_SCORE = 0;
    private static final int MAX_SCORE = 100;

    @Inject
    private ProfessorCourseMapper professorCourseMapper;
    @Inject
    private CounselingMapper counselingMapper;
    @Inject
    private StudentCourseMapper studentCourseMapper;
    @Inject
    private LectureFundamentalsMapper lectureFundamentalsMapper;
    @Inject
    private ProfLectureMethodMapper profLectureMethodMapper;
    @Inject
    private LectureContentsMapper lectureContentsMapper;
    @Inject
    private CqiMapper cqiMapper;
    @Inject
    private UserMapper userMapper;
    @Inject
    private GraduationResearchPlanMapper graduationResearchPlanMapper;
    @Inject
    private AssessmentMapper assessmentMapper;

    public ProfessorCourse requireOwnedProfessorCourse(int profCourseId, User currentUser, String resource) {
        ProfessorCourse professorCourse = professorCourseMapper.findOne(profCourseId);
        validateProfessorOwnership(profCourseId, currentUser, professorCourse, resource);
        return professorCourse;
    }

    public Counseling requireOwnedCounseling(int counselingId, User currentUser, String resource) {
        if (!isValidAuthenticatedUser(currentUser)) {
            logUnauthorized(currentUser, resource, counselingId);
            throw new AccessDeniedException("Access denied.");
        }

        Counseling counseling = counselingMapper.findOneByIdAndProfessorUserId(counselingId, currentUser.getId());
        if (counseling == null) {
            logUnauthorized(currentUser, resource, counselingId);
            throw new AccessDeniedException("Access denied.");
        }
        return counseling;
    }

    public StudentCourse requireOwnedStudentCourseForProfessor(int studentCourseId, User currentUser, String resource) {
        StudentCourse studentCourse = studentCourseMapper.findOne(studentCourseId);
        if (studentCourse == null) {
            logUnauthorized(currentUser, resource, studentCourseId);
            throw new AccessDeniedException("Access denied.");
        }

        ProfessorCourse professorCourse = professorCourseMapper.findOne(studentCourse.getProfCourseId());
        validateProfessorOwnership(studentCourse.getProfCourseId(), currentUser, professorCourse, resource);
        return studentCourse;
    }

    public StudentCourse requireOwnedStudentCourseForStudent(int studentCourseId, User currentUser, String resource) {
        StudentCourse studentCourse = studentCourseMapper.findOne(studentCourseId);
        if (!isValidAuthenticatedUser(currentUser) || studentCourse == null || studentCourse.getUserId() != currentUser.getId()) {
            logUnauthorized(currentUser, resource, studentCourseId);
            throw new AccessDeniedException("Access denied.");
        }
        return studentCourse;
    }

    public LectureFundamentals requireOwnedLectureFundamentals(int lectureFundamentalsId,
            int profCourseId, User currentUser, String resource) {
        requireOwnedProfessorCourse(profCourseId, currentUser, resource);
        LectureFundamentals lectureFundamentals = lectureFundamentalsMapper.findOne(lectureFundamentalsId);
        if (lectureFundamentals == null || lectureFundamentals.getProfCourseId() != profCourseId) {
            logUnauthorized(currentUser, resource, lectureFundamentalsId);
            throw new AccessDeniedException("Access denied.");
        }
        return lectureFundamentals;
    }

    public ProfLectureMethod requireOwnedProfLectureMethod(int profLectureMethodId,
            int profCourseId, User currentUser, String resource) {
        requireOwnedProfessorCourse(profCourseId, currentUser, resource);
        ProfLectureMethod profLectureMethod = profLectureMethodMapper.findOne(profLectureMethodId);
        if (profLectureMethod == null || profLectureMethod.getProfCourseId() != profCourseId) {
            logUnauthorized(currentUser, resource, profLectureMethodId);
            throw new AccessDeniedException("Access denied.");
        }
        return profLectureMethod;
    }

    public LectureContents requireOwnedLectureContents(int lectureContentsId,
            int profCourseId, User currentUser, String resource) {
        requireOwnedProfessorCourse(profCourseId, currentUser, resource);
        LectureContents lectureContents = lectureContentsMapper.findOne(lectureContentsId);
        if (lectureContents == null || lectureContents.getProfCourseId() != profCourseId) {
            logUnauthorized(currentUser, resource, lectureContentsId);
            throw new AccessDeniedException("Access denied.");
        }
        return lectureContents;
    }

    public Cqi requireOwnedCqi(int cqiId, int profCourseId, User currentUser, String resource) {
        requireOwnedProfessorCourse(profCourseId, currentUser, resource);
        Cqi cqi = cqiMapper.findOne(cqiId);
        if (cqi == null || cqi.getProfCourseId() != profCourseId || cqi.getUserId() != currentUser.getId()) {
            logUnauthorized(currentUser, resource, cqiId);
            throw new AccessDeniedException("Access denied.");
        }
        return cqi;
    }

    public User requireAdvisedStudent(int studentId, User currentUser, String resource) {
        User studentUser = userMapper.findOne(studentId);
        if (!isValidAuthenticatedUser(currentUser)
                || studentUser == null
                || studentUser.getAdvisorId() != currentUser.getId()) {
            logUnauthorized(currentUser, resource, studentId);
            throw new AccessDeniedException("Access denied.");
        }
        return studentUser;
    }

    public GraduationResearchPlan requireOwnedGraduationResearchPlanForProfessor(int planId, User currentUser,
            String resource) {
        GraduationResearchPlan plan = graduationResearchPlanMapper.findOne(planId);
        if (!isValidAuthenticatedUser(currentUser) || plan == null) {
            logUnauthorized(currentUser, resource, planId);
            throw new AccessDeniedException("Access denied.");
        }

        User studentUser = userMapper.findOne(plan.getUserId());
        if (studentUser == null || studentUser.getAdvisorId() != currentUser.getId()) {
            logUnauthorized(currentUser, resource, planId);
            throw new AccessDeniedException("Access denied.");
        }
        return plan;
    }

    public void submitAssessmentForOwnedStudentCourse(int studentCourseId, Assessment assessment, User currentUser) {
        StudentCourse ownedStudentCourse = requireOwnedStudentCourseForStudent(studentCourseId, currentUser,
                "student-class-assessment-submit");
        if (assessment == null) {
            throw new IllegalArgumentException("Invalid assessment payload.");
        }
        assessment.setUserId(currentUser.getId());
        assessment.setProfCourseId(ownedStudentCourse.getProfCourseId());
        assessmentMapper.insert(assessment);
    }

    public void updateGrade(int studentCourseId, GradeUpdateDTO dto, User currentUser) {
        StudentCourse studentCourse = requireOwnedStudentCourseForProfessor(studentCourseId, currentUser,
                "register-grade/edit");
        applyExplicitGradeUpdate(studentCourse, dto);
        studentCourse.setScoreTotal(calculateScoreTotal(studentCourse));
        studentCourseMapper.update(studentCourse);
    }

    public int calculateScoreTotal(StudentCourse studentCourse) {
        return studentCourse.getScoreAssignment()
                + studentCourse.getScoreMid()
                + studentCourse.getScoreFinal()
                + studentCourse.getScoreOptions();
    }

    public String calculateSuggestedGrade(StudentCourse studentCourse) {
        int total = calculateScoreTotal(studentCourse);
        ProfessorCourse professorCourse = professorCourseMapper.findOne(studentCourse.getProfCourseId());
        LectureFundamentals lectureFundamentals = lectureFundamentalsMapper.findByProfCourseId(studentCourse.getProfCourseId());

        if (professorCourse == null || lectureFundamentals == null) {
            return normalizeGradeOrDefault(studentCourse.getGrade());
        }

        if (professorCourse.isSecondTest()) {
            if (total >= 85) {
                return "A";
            }
            if (total >= 75) {
                return "B";
            }
            if (total >= 65) {
                return "C";
            }
            if (total >= 50) {
                return "D";
            }
            if (total >= 40) {
                return "E";
            }
            return "F";
        }

        double finalRatio;
        if (studentCourse.getScoreFinal() == 0 || lectureFundamentals.getRateFinal() == 0.0) {
            finalRatio = 0.0;
        } else {
            finalRatio = (double) studentCourse.getScoreFinal() / lectureFundamentals.getRateFinal();
        }

        if (finalRatio < 0.4) {
            return "G";
        }
        if (total >= 85) {
            return "A";
        }
        if (total >= 75) {
            return "B";
        }
        if (total >= 65) {
            return "C";
        }
        if (total >= 60) {
            return "D";
        }
        return "F";
    }

    private void applyExplicitGradeUpdate(StudentCourse studentCourse, GradeUpdateDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Invalid grade update payload.");
        }

        if (dto.getScore() != null) {
            validateRange(dto.getScore(), "score");
            String scoreField = trimToNull(dto.getGrade());
            if (!ALLOWED_SCORE_FIELDS.contains(scoreField)) {
                throw new IllegalArgumentException("Unsupported score field.");
            }

            if ("scoreAssignment".equals(scoreField)) {
                studentCourse.setScoreAssignment(dto.getScore());
                return;
            }
            if ("scoreMid".equals(scoreField)) {
                studentCourse.setScoreMid(dto.getScore());
                return;
            }
            if ("scoreFinal".equals(scoreField)) {
                studentCourse.setScoreFinal(dto.getScore());
                return;
            }
            if ("scoreOptions".equals(scoreField)) {
                studentCourse.setScoreOptions(dto.getScore());
                return;
            }
        }

        if (dto.getAttendance() != null) {
            validateRange(dto.getAttendance(), "attendance");
            studentCourse.setAcquire(dto.getAttendance());
            return;
        }

        String normalizedGrade = normalizeGradeOrNull(dto.getGrade());
        if (normalizedGrade == null || !ALLOWED_GRADES.contains(normalizedGrade)) {
            throw new IllegalArgumentException("Unsupported grade value.");
        }
        studentCourse.setGrade(normalizedGrade);
    }

    private void validateProfessorOwnership(int profCourseId, User currentUser) {
        ProfessorCourse professorCourse = professorCourseMapper.findOne(profCourseId);
        validateProfessorOwnership(profCourseId, currentUser, professorCourse, "professor-course");
    }

    private void validateProfessorOwnership(int profCourseId, User currentUser,
            ProfessorCourse professorCourse, String resource) {
        if (!isValidAuthenticatedUser(currentUser)
                || professorCourse == null
                || professorCourse.getUserId() != currentUser.getId()) {
            logUnauthorized(currentUser, resource, profCourseId);
            throw new AccessDeniedException("Access denied.");
        }
    }

    private boolean isValidAuthenticatedUser(User currentUser) {
        return currentUser != null && currentUser.getId() > 0;
    }

    private String normalizeGradeOrDefault(String grade) {
        String normalized = normalizeGradeOrNull(grade);
        return normalized == null ? "F" : normalized;
    }

    private String normalizeGradeOrNull(String grade) {
        String value = trimToNull(grade);
        if (value == null) {
            return null;
        }
        return value.toUpperCase(Locale.ROOT);
    }

    private void validateRange(int value, String fieldName) {
        if (value < MIN_SCORE || value > MAX_SCORE) {
            throw new IllegalArgumentException("Invalid " + fieldName + " range.");
        }
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private void logUnauthorized(User currentUser, String resource, int id) {
        logger.warn("SECURITY_EVENT | type=UNAUTHORIZED_ACCESS | user={} | resource={} | id={} | ip={}",
                currentUser == null ? "anonymous" : String.valueOf(currentUser.getId()),
                resource == null ? "unknown" : resource,
                id,
                resolveClientIp());
    }

    private String resolveClientIp() {
        HttpServletRequest request = resolveCurrentRequest();
        if (request == null) {
            return "n/a";
        }

        String forwardedFor = trimToNull(request.getHeader("X-Forwarded-For"));
        if (forwardedFor != null) {
            return forwardedFor.split(",")[0].trim();
        }

        String remoteAddress = trimToNull(request.getRemoteAddr());
        return remoteAddress == null ? "n/a" : remoteAddress;
    }

    private HttpServletRequest resolveCurrentRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (!(requestAttributes instanceof ServletRequestAttributes)) {
            return null;
        }
        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }
}
