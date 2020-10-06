package koreatech.cse.domain.role.student;

import koreatech.cse.domain.constant.AttendanceStatus;
import koreatech.cse.domain.univ.Course;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

public class Attendance implements Serializable {
    private static final long serialVersionUID = 42342349L;

    private int id;
    private int userId;
    private int courseId;
    private String date;
    private String period;
    private AttendanceStatus attendance;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public AttendanceStatus getAttendance() {
        return attendance;
    }

    public void setAttendance(AttendanceStatus attendance) {
        this.attendance = attendance;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
