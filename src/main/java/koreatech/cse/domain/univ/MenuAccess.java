package koreatech.cse.domain.univ;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

public class MenuAccess implements Serializable {
    private static final long serialVersionUID = 352223849L;
    
    private int id;

    private boolean assessment;
    private boolean grade;
    private boolean syllabus;
    private boolean cqi;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAssessment() {
        return assessment;
    }

    public void setAssessment(boolean assessment) {
        this.assessment = assessment;
    }

    public boolean isGrade() {
        return grade;
    }

    public void setGrade(boolean grade) {
        this.grade = grade;
    }

    public boolean isSyllabus() {
        return syllabus;
    }

    public void setSyllabus(boolean syllabus) {
        this.syllabus = syllabus;
    }

    public boolean isCqi() {
        return cqi;
    }

    public void setCqi(boolean cqi) {
        this.cqi = cqi;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
