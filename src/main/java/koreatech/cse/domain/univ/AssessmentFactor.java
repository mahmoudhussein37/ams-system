package koreatech.cse.domain.univ;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class AssessmentFactor implements Serializable {
    private static final long serialVersionUID = 352223849L;


    private int id;

    private String title;
    private String question;
    private int courseId;
    private boolean enabled;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
