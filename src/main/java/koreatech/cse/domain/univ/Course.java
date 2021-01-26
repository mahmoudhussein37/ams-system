package koreatech.cse.domain.univ;

import org.apache.commons.lang3.builder.ToStringBuilder;

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
    private int schoolYear;
    private boolean enabled;

    private boolean achieve1; //basic
    private boolean achieve2; //analysis confirmed
    private boolean achieve3; //design ability
    private boolean achieve4; //problem definition
    private boolean achieve5; //practice skill
    private boolean achieve6; //team skills
    private boolean achieve7; //communication
    private boolean achieve8; //life long learning
    private boolean achieve9; //impact predictions
    private boolean achieve10; //work ethics

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

    public String getSubjCategory() {
        return subjCategory;
    }

    public void setSubjCategory(String subjCategory) {
        this.subjCategory = subjCategory;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
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

    public boolean isAchieve1() {
        return achieve1;
    }

    public void setAchieve1(boolean achieve1) {
        this.achieve1 = achieve1;
    }

    public boolean isAchieve2() {
        return achieve2;
    }

    public void setAchieve2(boolean achieve2) {
        this.achieve2 = achieve2;
    }

    public boolean isAchieve3() {
        return achieve3;
    }

    public void setAchieve3(boolean achieve3) {
        this.achieve3 = achieve3;
    }

    public boolean isAchieve4() {
        return achieve4;
    }

    public void setAchieve4(boolean achieve4) {
        this.achieve4 = achieve4;
    }

    public boolean isAchieve5() {
        return achieve5;
    }

    public void setAchieve5(boolean achieve5) {
        this.achieve5 = achieve5;
    }

    public boolean isAchieve6() {
        return achieve6;
    }

    public void setAchieve6(boolean achieve6) {
        this.achieve6 = achieve6;
    }

    public boolean isAchieve7() {
        return achieve7;
    }

    public void setAchieve7(boolean achieve7) {
        this.achieve7 = achieve7;
    }

    public boolean isAchieve8() {
        return achieve8;
    }

    public void setAchieve8(boolean achieve8) {
        this.achieve8 = achieve8;
    }

    public boolean isAchieve9() {
        return achieve9;
    }

    public void setAchieve9(boolean achieve9) {
        this.achieve9 = achieve9;
    }

    public boolean isAchieve10() {
        return achieve10;
    }

    public void setAchieve10(boolean achieve10) {
        this.achieve10 = achieve10;
    }

    public boolean getAchieve(int id) {
        switch (id) {
            case 1:
                return achieve1;
            case 2:
                return achieve2;
            case 3:
                return achieve3;
            case 4:
                return achieve4;
            case 5:
                return achieve5;
            case 6:
                return achieve6;
            case 7:
                return achieve7;
            case 8:
                return achieve8;
            case 9:
                return achieve9;
            case 10:
                return achieve10;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
