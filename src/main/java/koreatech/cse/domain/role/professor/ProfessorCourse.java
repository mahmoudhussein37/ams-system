package koreatech.cse.domain.role.professor;

import koreatech.cse.domain.UploadedFile;
import koreatech.cse.domain.User;
import koreatech.cse.domain.univ.Course;
import koreatech.cse.domain.univ.Semester;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

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

    private UploadedFile attendanceFile;


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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
