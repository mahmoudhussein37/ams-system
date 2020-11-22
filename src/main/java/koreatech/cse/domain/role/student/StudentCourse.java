package koreatech.cse.domain.role.student;

import koreatech.cse.domain.User;
import koreatech.cse.domain.role.professor.ProfessorCourse;
import koreatech.cse.domain.univ.Course;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

public class StudentCourse implements Serializable {
    private static final long serialVersionUID = 449L;

    private int id;
    private int userId;
    private int courseId;
    private int profCourseId;
    private ProfessorCourse professorCourse;
    private Course course;
    private User studentUser;

    private int scoreAttendance;
    private int scoreAssignment;
    private int scoreMid;
    private int scoreFinal;
    private int scoreOptions;

    private int scoreTotal;
    private String grade;


    private int acquire; //TODO: 수강중, 수강완료

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

    public int getAcquire() {
        return acquire;
    }

    public void setAcquire(int acquire) {
        this.acquire = acquire;
    }

    public int getProfCourseId() {
        return profCourseId;
    }

    public void setProfCourseId(int profCourseId) {
        this.profCourseId = profCourseId;
    }

    public ProfessorCourse getProfessorCourse() {
        return professorCourse;
    }

    public void setProfessorCourse(ProfessorCourse professorCourse) {
        this.professorCourse = professorCourse;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public User getStudentUser() {
        return studentUser;
    }

    public void setStudentUser(User studentUser) {
        this.studentUser = studentUser;
    }

    public int getScoreAttendance() {
        return scoreAttendance;
    }

    public void setScoreAttendance(int scoreAttendance) {
        this.scoreAttendance = scoreAttendance;
    }

    public int getScoreAssignment() {
        return scoreAssignment;
    }

    public void setScoreAssignment(int scoreAssignment) {
        this.scoreAssignment = scoreAssignment;
    }

    public int getScoreMid() {
        return scoreMid;
    }

    public void setScoreMid(int scoreMid) {
        this.scoreMid = scoreMid;
    }

    public int getScoreFinal() {
        return scoreFinal;
    }

    public void setScoreFinal(int scoreFinal) {
        this.scoreFinal = scoreFinal;
    }

    public int getScoreOptions() {
        return scoreOptions;
    }

    public void setScoreOptions(int scoreOptions) {
        this.scoreOptions = scoreOptions;
    }

    public int getScoreTotal() {
        return scoreTotal;
    }

    public void setScoreTotal(int scoreTotal) {
        this.scoreTotal = scoreTotal;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
