package koreatech.cse.domain.role.professor;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

public class LectureFundamentals implements Serializable {
    private static final long serialVersionUID = 4343219L;


    private int id;
    private int courseId;
    private int userId; //professor user id;
    private String intro;

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

    private int rateAttendance;
    private int rateAssignment;
    private int rateMid;
    private int rateFinal;
    private int rateOptional;

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

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
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

    public int getRateAttendance() {
        return rateAttendance;
    }

    public void setRateAttendance(int rateAttendance) {
        this.rateAttendance = rateAttendance;
    }

    public int getRateAssignment() {
        return rateAssignment;
    }

    public void setRateAssignment(int rateAssignment) {
        this.rateAssignment = rateAssignment;
    }

    public int getRateMid() {
        return rateMid;
    }

    public void setRateMid(int rateMid) {
        this.rateMid = rateMid;
    }

    public int getRateFinal() {
        return rateFinal;
    }

    public void setRateFinal(int rateFinal) {
        this.rateFinal = rateFinal;
    }

    public int getRateOptional() {
        return rateOptional;
    }

    public void setRateOptional(int rateOptional) {
        this.rateOptional = rateOptional;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
