package koreatech.cse.domain.role.professor;

import koreatech.cse.domain.UploadedFile;
import koreatech.cse.domain.User;
import koreatech.cse.domain.role.student.StudentCourse;
import koreatech.cse.domain.univ.AssessmentFactor;
import koreatech.cse.domain.univ.Classroom;
import koreatech.cse.domain.univ.Course;
import koreatech.cse.domain.univ.Semester;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.security.access.method.P;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.*;

public class ProfessorCourse implements Serializable {
    private static final long serialVersionUID = 4349L;

    private int id;
    private int userId;
    private int courseId;
    private int divide;
    private int semesterId;
    private Course course;
    private User professorUser;
    private Semester semester;

    private int limitStudent;
    private int numStudent;
    private int attendance;
    private int lateness;
    private int absence;
    private boolean enabled;

    private boolean secondTest;

    private int schoolYear;
    private int classroom; //classroom id
    private boolean engAccreditation;
    private String language;

    private Classroom classroomObj;

    private UploadedFile attendanceFile;

    private List<Assessment> assessmentList;

    private List<StudentCourse> studentCourseList;
    private List<ClassTime> classTimeList;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }


    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getLimitStudent() {
        return limitStudent;
    }

    public void setLimitStudent(int limitStudent) {
        this.limitStudent = limitStudent;
    }

    public int getNumStudent() {
        return numStudent;
    }

    public void setNumStudent(int numStudent) {
        this.numStudent = numStudent;
    }

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    public int getLateness() {
        return lateness;
    }

    public void setLateness(int lateness) {
        this.lateness = lateness;
    }

    public int getAbsence() {
        return absence;
    }

    public void setAbsence(int absence) {
        this.absence = absence;
    }

    public int getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }

    public User getProfessorUser() {
        return professorUser;
    }

    public void setProfessorUser(User professorUser) {
        this.professorUser = professorUser;
    }

    public int getDivide() {
        return divide;
    }

    public void setDivide(int divide) {
        this.divide = divide;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public UploadedFile getAttendanceFile() {
        return attendanceFile;
    }

    public void setAttendanceFile(UploadedFile attendanceFile) {
        this.attendanceFile = attendanceFile;
    }

    public int getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(int schoolYear) {
        this.schoolYear = schoolYear;
    }

    public int getClassroom() {
        return classroom;
    }

    public void setClassroom(int classroom) {
        this.classroom = classroom;
    }

    public boolean isEngAccreditation() {
        return engAccreditation;
    }

    public void setEngAccreditation(boolean engAccreditation) {
        this.engAccreditation = engAccreditation;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Classroom getClassroomObj() {
        return classroomObj;
    }

    public void setClassroomObj(Classroom classroomObj) {
        this.classroomObj = classroomObj;
    }

    public List<Assessment> getAssessmentList() {
        return assessmentList;
    }

    public void setAssessmentList(List<Assessment> assessmentList) {
        this.assessmentList = assessmentList;
    }

    public List<AssessmentFactor> getFilteredAssessmentFactors(List<AssessmentFactor> assessmentFactors) {
        if(CollectionUtils.isEmpty(this.assessmentList))
            return new ArrayList<>();
        Assessment assessment = assessmentList.get(0);
        List<AssessmentFactor> filtered = new ArrayList<>();

        for(AssessmentFactor assessmentFactor: assessmentFactors) {
            for(int i=1; i<=20; i++) {
                int id = assessment.getItem(i);
                if(assessmentFactor.getId() == id)
                    filtered.add(assessmentFactor);
            }
        }
        return filtered;
    }

    public double getAssessmentFactorAverage(AssessmentFactor assessmentFactor) {
        if(CollectionUtils.isEmpty(this.assessmentList))
            return 0.0;
        int total = 0;
        for(Assessment assessment: assessmentList) {
            int score = assessment.getScoreByItemId(assessmentFactor.getId());
            total += score;
        }

        if (total == 0)
            return 0.0;
        else {
            return (double)total / (double)assessmentList.size();
        }
    }

    public Map<String, Integer> getNumGradeMap() {
        Map<String, Integer> grades = new LinkedHashMap<>();
        grades.put("A", 0);
        grades.put("B", 0);
        grades.put("C", 0);
        grades.put("D", 0);
        grades.put("E", 0);
        grades.put("F", 0);
        grades.put("G", 0);
        grades.put("S", 0);
        grades.put("U", 0);
        if(CollectionUtils.isEmpty(studentCourseList)) {
            return grades;
        }

        for(StudentCourse sc: studentCourseList) {
            if(sc.getGrade().equals("A")) {
                Integer count = grades.get("A");
                grades.put("A", count + 1);
            }
            if(sc.getGrade().equals("B")) {
                Integer count = grades.get("B");
                grades.put("B", count + 1);
            }
            if(sc.getGrade().equals("C")) {
                Integer count = grades.get("C");
                grades.put("C", count + 1);
            }
            if(sc.getGrade().equals("D")) {
                Integer count = grades.get("D");
                grades.put("D", count + 1);
            }
            if(sc.getGrade().equals("E")) {
                Integer count = grades.get("E");
                grades.put("E", count + 1);
            }
            if(sc.getGrade().equals("F")) {
                Integer count = grades.get("F");
                grades.put("F", count + 1);
            }
            if(sc.getGrade().equals("G")) {
                Integer count = grades.get("G");
                grades.put("G", count + 1);
            }
            if(sc.getGrade().equals("S")) {
                Integer count = grades.get("S");
                grades.put("S", count + 1);
            }
            if(sc.getGrade().equals("U")) {
                Integer count = grades.get("U");
                grades.put("U", count + 1);
            }
        }
        return grades;
    }

    public List<StudentCourse> getStudentCourseList() {
        return studentCourseList;
    }

    public void setStudentCourseList(List<StudentCourse> studentCourseList) {
        this.studentCourseList = studentCourseList;
    }

    public List<ClassTime> getClassTimeList() {
        return classTimeList;
    }

    public void setClassTimeList(List<ClassTime> classTimeList) {
        this.classTimeList = classTimeList;
    }

    public boolean isSecondTest() {
        return secondTest;
    }

    public void setSecondTest(boolean secondTest) {
        this.secondTest = secondTest;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
