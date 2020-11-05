package koreatech.cse.domain.univ;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;

public class AltCourse  implements Serializable {
    private static final long serialVersionUID = 3522323849L;

    private int id;
    private int targetCourseId;
    private int courseId;
    private Course course;
    private String type;
    private Date registeredDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getTargetCourseId() {
        return targetCourseId;
    }

    public void setTargetCourseId(int targetCourseId) {
        this.targetCourseId = targetCourseId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
