package koreatech.cse.domain.dto;

public class MenuSettingsRequest {
    private boolean assessment;
    private boolean grade;
    private boolean syllabus;
    private boolean cqi;

    public boolean isAssessment() { return assessment; }
    public void setAssessment(boolean assessment) { this.assessment = assessment; }

    public boolean isGrade() { return grade; }
    public void setGrade(boolean grade) { this.grade = grade; }

    public boolean isSyllabus() { return syllabus; }
    public void setSyllabus(boolean syllabus) { this.syllabus = syllabus; }

    public boolean isCqi() { return cqi; }
    public void setCqi(boolean cqi) { this.cqi = cqi; }
}
