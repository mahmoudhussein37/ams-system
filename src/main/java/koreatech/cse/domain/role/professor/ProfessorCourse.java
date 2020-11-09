package koreatech.cse.domain.role.professor;

import koreatech.cse.domain.univ.Course;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

public class ProfessorCourse implements Serializable {
    private static final long serialVersionUID = 4349L;

    private int id;
    private int userId;
    private int courseId;
    private Course course;

    private int limitStudent;
    private int numStudent;
    private int attendance;
    private int lateness;
    private int absence;

    private int credit;
    private int lec;
    private int tut;
    private int lab;
    private int ws;

    private String alternative;
    private String prerequisite;


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

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getLec() {
        return lec;
    }

    public void setLec(int lec) {
        this.lec = lec;
    }

    public int getTut() {
        return tut;
    }

    public void setTut(int tut) {
        this.tut = tut;
    }

    public int getLab() {
        return lab;
    }

    public void setLab(int lab) {
        this.lab = lab;
    }

    public int getWs() {
        return ws;
    }

    public void setWs(int ws) {
        this.ws = ws;
    }

    public String getAlternative() {
        return alternative;
    }

    public void setAlternative(String alternative) {
        this.alternative = alternative;
    }

    public String getPrerequisite() {
        return prerequisite;
    }

    public void setPrerequisite(String prerequisite) {
        this.prerequisite = prerequisite;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
