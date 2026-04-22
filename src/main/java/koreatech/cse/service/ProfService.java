package koreatech.cse.service;

import koreatech.cse.domain.Searchable;
import koreatech.cse.domain.User;
import koreatech.cse.domain.role.professor.Assessment;
import koreatech.cse.domain.role.professor.ProfessorCourse;
import koreatech.cse.domain.role.student.StudentCourse;
import koreatech.cse.domain.univ.Semester;
import koreatech.cse.repository.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.*;

@Service
public class ProfService {
    private static final Logger logger = LoggerFactory.getLogger(ProfService.class);
    private static final int IN_CLAUSE_BATCH_SIZE = 150;

    @Inject
    private AssessmentMapper assessmentMapper;
    @Inject
    private ProfessorCourseMapper professorCourseMapper;
    @Inject
    private UserMapper userMapper;
    @Inject
    private StudentCourseMapper studentCourseMapper;
    @Inject
    private SemesterMapper semesterMapper;

    public double getAssignedAvg(ProfessorCourse pc) {
        List<Assessment> assessmentList = pc.getAssessmentList();
        if (assessmentList == null) {
            assessmentList = assessmentMapper.findByProfCourseId(pc.getId());
        }
        pc.setAssessmentList(assessmentList);
        int myTotal = 0;
        int mySize = 0;
        for(Assessment assessment: pc.getAssessmentList()) {
            for(int j=1; j<=20; j++) {
                int item = assessment.getItem(j);
                if(item > 0) {
                    int score = assessment.getScore(j);
                    myTotal += score;

                    mySize++;
                }
            }
        }
        double myAvg;
        if(myTotal == 0 || mySize == 0)
            myAvg = 0.0;
        else {
            myAvg = (double)myTotal / (double)mySize;
        }
        return myAvg;
    }

    public Map<Integer, Double> getAverageAssignedDivideMap(List<ProfessorCourse> professorCourseList) {
        Map<Integer, Double> averageAssignedDivideMap = new LinkedHashMap<>();
        for(ProfessorCourse p: professorCourseList) {
            List<Assessment> assessments = p.getAssessmentList();
            if (assessments == null) {
                assessments = assessmentMapper.findByProfCourseId(p.getId());
            }
            p.setAssessmentList(assessments);
            double avg = 0.0;
            int total = 0;
            int size = 0;
            for(Assessment assessment: assessments) {
                for(int j=1; j<=20; j++) {
                    int item = assessment.getItem(j);
                    if(item > 0) {
                        int score = assessment.getScore(j);
                        total += score;
                        size++;
                    }
                }
            }
            if(total == 0 || size == 0)
                avg = 0.0;
            else {
                avg = (double)total / (double)size;
            }
            averageAssignedDivideMap.put(p.getDivide(), avg);
        }
        return averageAssignedDivideMap;
    }

    public Map<Integer, Double> getAverageAssignedMap(int courseId, int currentYear) {
        Map<Integer, Double> averageAssignedMap = new LinkedHashMap<>();
        for(int i=(currentYear - 2); i<=currentYear; i++) {

            Searchable searchable = new Searchable();
            searchable.setCourseId(courseId);
            searchable.setYear(i);
            searchable.setEnabled(true);
            searchable.setOrderParam("divide");
            searchable.setOrderDir("asc");
            List<ProfessorCourse> professorCourses = professorCourseMapper.findByFlat(searchable);
            double avg;
            int total = 0;

            int size = 0;

            if (professorCourses.isEmpty()) {
                averageAssignedMap.put(i, 0.0);
                continue;
            }

            List<Integer> professorCourseIds = new ArrayList<>();
            for (ProfessorCourse professorCourse : professorCourses) {
                professorCourseIds.add(professorCourse.getId());
            }

            Map<Integer, List<Assessment>> assessmentsByCourseId = new HashMap<>();
            List<Assessment> assessments = findAssessmentsByProfCourseIdsBatched(professorCourseIds);
            for (Assessment assessment : assessments) {
                int professorCourseId = assessment.getProfCourseId();
                List<Assessment> groupedAssessments = assessmentsByCourseId.get(professorCourseId);
                if (groupedAssessments == null) {
                    groupedAssessments = new ArrayList<>();
                    assessmentsByCourseId.put(professorCourseId, groupedAssessments);
                }
                groupedAssessments.add(assessment);
            }

            for(ProfessorCourse p: professorCourses) {
                List<Assessment> courseAssessments = assessmentsByCourseId.get(p.getId());
                if (courseAssessments == null) {
                    courseAssessments = new ArrayList<>();
                }
                p.setAssessmentList(courseAssessments);
                for(Assessment assessment: courseAssessments) {
                    for(int j=1; j<=20; j++) {
                        int item = assessment.getItem(j);
                        if(item > 0) {
                            int score = assessment.getScore(j);
                            total += score;
                            size++;
                        }
                    }
                }
            }
            if(total == 0 || size == 0)
                avg = 0.0;
            else {
                avg = (double)total / (double)size;
            }

            averageAssignedMap.put(i, avg);
        }
        return averageAssignedMap;
    }

    public String getStudentTotalScore(int studentUserId) {
        return getStudentTotalScore(getStudentSemesterCourseMap(studentUserId));
    }

    public String getStudentTotalScore(Map<Semester, List<StudentCourse>> semesterCourseMap) {
        double totalGradeTotal = 0;
        int totalGradeCount = 0;

        for (Map.Entry<Semester, List<StudentCourse>> entry : semesterCourseMap.entrySet()) {
            List<StudentCourse> studentCourses = entry.getValue();

            int gradeCount = 0;
            double gradeTotal = 0.0;
            for (StudentCourse studentCourse : studentCourses) {
                if (studentCourse.isValid() && !studentCourse.getGrade().equals("U")
                        && !studentCourse.getGrade().equals("S")) {
                    double currentGrade = studentCourse.getCourse().getCredit() * studentCourse.getGradeScore();
                    gradeTotal += currentGrade;
                    gradeCount += studentCourse.getCourse().getCredit();
                }
            }
            totalGradeTotal += gradeTotal;
            totalGradeCount += gradeCount;
        }

        if (totalGradeCount == 0 || totalGradeTotal == 0.0) {
            return "0.0";
        }

        double finalScore = totalGradeTotal / totalGradeCount;
        return String.format("%.2f", finalScore);
    }

    public Map<Semester, List<StudentCourse>> getStudentSemesterCourseMap(int studentUserId) {
        LinkedHashSet<Integer> semesterSet = studentCourseMapper.findSemesterIdByUserIdValid(studentUserId);
        List<Integer> semesterIds = new ArrayList<>(semesterSet);
        Map<Semester, List<StudentCourse>> semesterCourseMap = new LinkedHashMap<>();

        if (semesterIds.isEmpty()) {
            return semesterCourseMap;
        }

        List<Semester> semesters = semesterMapper.findByIds(semesterIds);
        Map<Integer, Semester> semesterById = new HashMap<>();
        for (Semester semester : semesters) {
            semesterById.put(semester.getId(), semester);
        }

        List<StudentCourse> allCourses = studentCourseMapper.findInquiryByUserIdValid(studentUserId);
        Set<Integer> assessedProfCourseIds = findAssessedProfCourseIds(studentUserId, allCourses);
        Map<Integer, List<StudentCourse>> filteredBySemesterId = new HashMap<>();

        for (StudentCourse studentCourse : allCourses) {
            if (!assessedProfCourseIds.contains(studentCourse.getProfCourseId())) {
                continue;
            }

            ProfessorCourse professorCourse = studentCourse.getProfessorCourse();
            if (professorCourse == null) {
                continue;
            }

            int semesterId = professorCourse.getSemesterId();
            List<StudentCourse> semesterCourses = filteredBySemesterId.get(semesterId);
            if (semesterCourses == null) {
                semesterCourses = new ArrayList<>();
                filteredBySemesterId.put(semesterId, semesterCourses);
            }
            semesterCourses.add(studentCourse);
        }

        for (Integer semesterId : semesterIds) {
            Semester semester = semesterById.get(semesterId);
            if (semester == null) {
                continue;
            }

            List<StudentCourse> semesterCourses = filteredBySemesterId.get(semesterId);
            if (semesterCourses == null) {
                semesterCourseMap.put(semester, new ArrayList<>());
                continue;
            }

            semesterCourseMap.put(semester, filterRepeatedFailures(semesterCourses));
        }

        return semesterCourseMap;
    }

    private List<Assessment> findAssessmentsByProfCourseIdsBatched(List<Integer> profCourseIds) {
        if (profCourseIds == null || profCourseIds.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> uniqueProfCourseIds = new ArrayList<>(new LinkedHashSet<>(profCourseIds));
        Set<Integer> seenAssessmentIds = new LinkedHashSet<>();
        List<Assessment> merged = new ArrayList<>();

        for (int start = 0; start < uniqueProfCourseIds.size(); start += IN_CLAUSE_BATCH_SIZE) {
            int end = Math.min(start + IN_CLAUSE_BATCH_SIZE, uniqueProfCourseIds.size());
            List<Assessment> batch = assessmentMapper.findByProfCourseIds(uniqueProfCourseIds.subList(start, end));
            for (Assessment assessment : batch) {
                if (seenAssessmentIds.add(assessment.getId())) {
                    merged.add(assessment);
                }
            }
        }

        return merged;
    }

    private List<Assessment> findAssessmentsByUserAndProfCourseIdsBatched(int userId,
            List<Integer> profCourseIds) {
        if (profCourseIds == null || profCourseIds.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> uniqueProfCourseIds = new ArrayList<>(new LinkedHashSet<>(profCourseIds));
        Set<Integer> seenAssessmentIds = new LinkedHashSet<>();
        List<Assessment> merged = new ArrayList<>();

        for (int start = 0; start < uniqueProfCourseIds.size(); start += IN_CLAUSE_BATCH_SIZE) {
            int end = Math.min(start + IN_CLAUSE_BATCH_SIZE, uniqueProfCourseIds.size());
            List<Assessment> batch = assessmentMapper.findByUserIdAndProfCourseIds(userId,
                    uniqueProfCourseIds.subList(start, end));
            for (Assessment assessment : batch) {
                if (seenAssessmentIds.add(assessment.getId())) {
                    merged.add(assessment);
                }
            }
        }

        return merged;
    }

    private Set<Integer> findAssessedProfCourseIds(int userId, List<StudentCourse> courses) {
        Set<Integer> assessedProfCourseIds = new HashSet<>();
        if (courses == null || courses.isEmpty()) {
            return assessedProfCourseIds;
        }

        Set<Integer> profCourseIds = new LinkedHashSet<>();
        for (StudentCourse course : courses) {
            profCourseIds.add(course.getProfCourseId());
        }

        if (profCourseIds.isEmpty()) {
            return assessedProfCourseIds;
        }

        List<Assessment> assessments = findAssessmentsByUserAndProfCourseIdsBatched(userId,
            new ArrayList<>(profCourseIds));
        for (Assessment assessment : assessments) {
            assessedProfCourseIds.add(assessment.getProfCourseId());
        }

        return assessedProfCourseIds;
    }

    private List<StudentCourse> filterRepeatedFailures(List<StudentCourse> courses) {
        List<String> failCodes = new ArrayList<>();
        List<StudentCourse> alternatives = new ArrayList<>();
        List<StudentCourse> filteredCourses = new ArrayList<>();

        for (StudentCourse studentCourse : courses) {
            if (studentCourse.getGrade().equals("F") || studentCourse.getGrade().equals("U")) {
                failCodes.add(studentCourse.getCourse().getCode());
            }
        }

        for (StudentCourse studentCourse : courses) {
            for (String code : failCodes) {
                if (studentCourse.getCourse().getCode().equals(code)
                        && !studentCourse.getGrade().equals("F")
                        && !studentCourse.getGrade().equals("U")) {
                    alternatives.add(studentCourse);
                }
            }
        }

        for (StudentCourse studentCourse : courses) {
            if (studentCourse.getGrade().equals("F") || studentCourse.getGrade().equals("U")) {
                boolean alternativeExist = false;
                for (StudentCourse alternative : alternatives) {
                    if (alternative.getCourse().getCode().equals(studentCourse.getCourse().getCode())) {
                        alternativeExist = true;
                        break;
                    }
                }
                if (!alternativeExist) {
                    filteredCourses.add(studentCourse);
                }
            } else {
                filteredCourses.add(studentCourse);
            }
        }

        return filteredCourses;
    }

    public List<ProfessorCourse> findSyllabusCoursesForStudent(User studentUser, int requestedDivisionId,
            int year, int semester, String title) {
        int studentDivisionId = resolveStudentDivisionId(studentUser);
        validateRequestedDivision(studentUser, requestedDivisionId, studentDivisionId, "syllabus table");

        if (year == 0 && semester == 0 && requestedDivisionId == 0 && StringUtils.isBlank(title)) {
            return new ArrayList<>();
        }

        Searchable searchable = new Searchable();
        searchable.setYear(year);
        searchable.setSemester(semester);
        searchable.setDivision(studentDivisionId);
        searchable.setTitle(title);
        searchable.setEnabled(true);

        return professorCourseMapper.findSyllabusForStudent(searchable);
    }

    public ProfessorCourse findSyllabusDetailForStudent(User studentUser, int requestedDivisionId, int profCourseId) {
        int studentDivisionId = resolveStudentDivisionId(studentUser);
        validateRequestedDivision(studentUser, requestedDivisionId, studentDivisionId, "syllabus detail");

        ProfessorCourse professorCourse = professorCourseMapper.findOneByIdAndDivision(profCourseId, studentDivisionId);
        if (professorCourse == null) {
            logger.warn("Invalid syllabus access attempt: userId={}, studentDivisionId={}, profCourseId={}",
                    studentUser.getId(), studentDivisionId, profCourseId);
            throw new AccessDeniedException("Access denied.");
        }

        return professorCourse;
    }

    private int resolveStudentDivisionId(User studentUser) {
        if (studentUser == null || studentUser.getId() <= 0 || studentUser.getDivisionId() <= 0) {
            throw new AccessDeniedException("Access denied.");
        }
        return studentUser.getDivisionId();
    }

    private void validateRequestedDivision(User studentUser, int requestedDivisionId, int studentDivisionId,
            String context) {
        if (requestedDivisionId > 0 && requestedDivisionId != studentDivisionId) {
            logger.warn("Denied cross-division access ({}): userId={}, studentDivisionId={}, requestedDivisionId={}",
                    context, studentUser.getId(), studentDivisionId, requestedDivisionId);
            throw new AccessDeniedException("Access denied.");
        }
    }

    
}
