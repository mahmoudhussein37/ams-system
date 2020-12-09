package koreatech.cse.service;

import koreatech.cse.domain.Searchable;
import koreatech.cse.domain.role.professor.Assessment;
import koreatech.cse.domain.role.professor.Cqi;
import koreatech.cse.domain.role.professor.ProfessorCourse;
import koreatech.cse.repository.AssessmentMapper;
import koreatech.cse.repository.ProfessorCourseMapper;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProfService {
    @Inject
    private AssessmentMapper assessmentMapper;
    @Inject
    private ProfessorCourseMapper professorCourseMapper;

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

    
}
