package koreatech.cse.domain.univ;

import koreatech.cse.domain.User;
import koreatech.cse.domain.role.professor.LectureFundamentals;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;

public class Course implements Serializable {
    private static final long serialVersionUID = 14570353849L;

    private int id;
    private String code;
    private String title;
    private String subjCategory;
    private int divisionId;
    private Division division;
    private Date registeredDate;

    private String overview;
    private String learningObjective;

    private int credit;
    private int lec;
    private int tut;
    private int lab;
    private int ws;

    private String alternative;
    private String prerequisite;

    private boolean enabled;








    private String category;


    private int schoolYear;
    private String lang;
    private String lectureTime;
    private String compCategory;

    private int profUserId;
    private int maxStudent;


    private User profUser;
    private LectureFundamentals lectureFundamentals;







    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public User getProfUser() {
        return profUser;
    }

    public void setProfUser(User profUser) {
        this.profUser = profUser;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public LectureFundamentals getLectureFundamentals() {
        return lectureFundamentals;
    }

    public void setLectureFundamentals(LectureFundamentals lectureFundamentals) {
        this.lectureFundamentals = lectureFundamentals;
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

    public Date getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getLearningObjective() {
        return learningObjective;
    }

    public void setLearningObjective(String learningObjective) {
        this.learningObjective = learningObjective;
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
