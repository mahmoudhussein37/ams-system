package koreatech.cse.domain.univ;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

public class Course implements Serializable {
    private static final long serialVersionUID = 14570353849L;

    private int id;
    private int year;
    private String semester;
    private String code;
    private String title;
    private String category;
    private int credit;
    private boolean retake;
    private String retakeCode;
    private String retakeTitle;
    private int divisionId;

    private int majorId;
    private int schoolYear;
    private String lang;
    private String lectureTime;
    private String compCategory;
    private String subjCategory;
    private int profUserId;
    private int maxStudent;


    private boolean enabled;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public boolean isRetake() {
        return retake;
    }

    public void setRetake(boolean retake) {
        this.retake = retake;
    }

    public String getRetakeCode() {
        return retakeCode;
    }

    public void setRetakeCode(String retakeCode) {
        this.retakeCode = retakeCode;
    }

    public String getRetakeTitle() {
        return retakeTitle;
    }

    public void setRetakeTitle(String retakeTitle) {
        this.retakeTitle = retakeTitle;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    public int getMajorId() {
        return majorId;
    }

    public void setMajorId(int majorId) {
        this.majorId = majorId;
    }

    public int getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(int schoolYear) {
        this.schoolYear = schoolYear;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLectureTime() {
        return lectureTime;
    }

    public void setLectureTime(String lectureTime) {
        this.lectureTime = lectureTime;
    }

    public String getCompCategory() {
        return compCategory;
    }

    public void setCompCategory(String compCategory) {
        this.compCategory = compCategory;
    }

    public String getSubjCategory() {
        return subjCategory;
    }

    public void setSubjCategory(String subjCategory) {
        this.subjCategory = subjCategory;
    }

    public int getProfUserId() {
        return profUserId;
    }

    public void setProfUserId(int profUserId) {
        this.profUserId = profUserId;
    }

    public int getMaxStudent() {
        return maxStudent;
    }

    public void setMaxStudent(int maxStudent) {
        this.maxStudent = maxStudent;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
