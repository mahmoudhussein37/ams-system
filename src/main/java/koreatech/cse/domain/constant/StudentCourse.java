package koreatech.cse.domain.constant;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

public class StudentCourse implements Serializable {
    private static final long serialVersionUID = 449L;

    private int id;
    private int userId;
    private int courseId;
    private int acquire; //TODO: 수강중, 수강완료
    private boolean retake;

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

    public boolean isRetake() {
        return retake;
    }

    public void setRetake(boolean retake) {
        this.retake = retake;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
