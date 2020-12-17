package koreatech.cse.service;

import koreatech.cse.domain.Searchable;
import koreatech.cse.domain.User;
import koreatech.cse.domain.role.professor.Assessment;
import koreatech.cse.domain.role.professor.ProfessorCourse;
import koreatech.cse.domain.role.student.StudentCourse;
import koreatech.cse.domain.univ.Semester;
import koreatech.cse.repository.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.*;

@Service
public class ProfService {
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
        List<Assessment> assessmentList = assessmentMapper.findByProfCourseId(pc.getCourseId());
        pc.setAssessmentList(assessmentList);
        int myTotal = 0;
        int mySize = 0;
        for(Assessment assessment: pc.getAssessmentList()) {
            for(int j=1; j<=20; j++) {
                int item = assessment.getItem(j);
                if(item > 0) {
                    int score = assessment.getScore(j);
                    System.out.println("score = " + score);
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
            List<Assessment> assessments = assessmentMapper.findByProfCourseId(p.getId());
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
            List<ProfessorCourse> professorCourses = professorCourseMapper.findBy(searchable);
            double avg;
            int total = 0;

            int size = 0;

            for(ProfessorCourse p: professorCourses) {
                List<Assessment> assessments = assessmentMapper.findByProfCourseId(p.getId());
                p.setAssessmentList(assessments);
                for(Assessment assessment: assessments) {
                    for(int j=1; j<=20; j++) {
                        int item = assessment.getItem(j);
                        if(item > 0) {
                            int score = assessment.getScore(j);
                            System.out.println("score = " + score);
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
        Map<Semester, List<StudentCourse>> map = getStudentSemesterCourseMap(studentUserId);

        double totalGradeTotal = 0;
        int totalGradeCount = 0;

        for(Map.Entry entry: map.entrySet()) {
            Semester semester = (Semester)entry.getKey();
            List<StudentCourse> scList = map.get(semester);

            int gradeCount = 0;
            double gradeTotal = 0.0;
            for(StudentCourse sc: scList) {
                if(sc.isValid() && !sc.getGrade().equals("U") && !sc.getGrade().equals("S")) {
                    double currentGrade = sc.getCourse().getCredit() * sc.getGradeScore();
                    gradeTotal += currentGrade;
                    gradeCount += sc.getCourse().getCredit();


                }
            }
            totalGradeTotal += gradeTotal;
            totalGradeCount += gradeCount;
        }

        if(totalGradeCount == 0 && totalGradeTotal == 0.0) {
            return "0.0";
        } else {
            double f = totalGradeTotal / totalGradeCount;

            return String.format("%.2f", f);
        }
    }

    public Map<Semester, List<StudentCourse>> getStudentSemesterCourseMap(int studentUserId) {
        LinkedHashSet<Integer> semesterSet = studentCourseMapper.findSemesterIdByUserIdValid(studentUserId);

        Map<Semester, List<StudentCourse>> map = new HashMap<>();
        for(Integer semesterId: semesterSet) {
            Semester semester = semesterMapper.findOne(semesterId);
            Searchable searchable = new Searchable();
            searchable.setYear(semester.getYear());
            searchable.setSemester(semester.getSemester());
            searchable.setUserId(studentUserId);
            List<StudentCourse> courses = studentCourseMapper.findByUserIdYearSemester(searchable);

            List<String> failCodes = new ArrayList<>();
            List<StudentCourse> alternatives = new ArrayList<>();
            List<StudentCourse> filtered = new ArrayList<>();
            for(StudentCourse sc: courses) {
                if(sc.getGrade().equals("F") || sc.getGrade().equals("U")) {

                    failCodes.add(sc.getCourse().getCode());
                }
            }
            for(StudentCourse sc: courses) {
                for(String code: failCodes) {
                    if(sc.getCourse().getCode().equals(code) && !sc.getGrade().equals("F") && !sc.getGrade().equals("U")) {
                        alternatives.add(sc);
                    }
                }
            }
            //model.addAttribute("alternatives", alternatives);


            for(StudentCourse sc: courses) {
                if(sc.getGrade().equals("F") || sc.getGrade().equals("U")) {
                    boolean alternativeExist = false;
                    for(StudentCourse al: alternatives) {
                        if(al.getCourse().getCode().equals(sc.getCourse().getCode())) {
                            alternativeExist = true;
                        }
                    }
                    if(!alternativeExist)
                        filtered.add(sc);
                } else {
                    filtered.add(sc);
                }
            }


            map.put(semester, filtered);
        }
        return map;
    }

    
}
