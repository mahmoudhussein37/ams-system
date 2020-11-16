package koreatech.cse.domain.role.professor;

import java.io.Serializable;

public class ProfLectureMethod implements Serializable {
    private static final long serialVersionUID = 43432179L;

    private int id;
    private int profCourseId;
    private int courseId;
    private int userId; //professor user id;

    private String mainTeaching1;
    private String mainTeaching2;

    private String subTeaching1;
    private String subTeaching2;
    private String subTeaching3;
    private String subTeaching4;
    private String subTeaching5;
    private String subTeaching6;

    private String note;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProfCourseId() {
        return profCourseId;
    }

    public void setProfCourseId(int profCourseId) {
        this.profCourseId = profCourseId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMainTeaching1() {
        return mainTeaching1;
    }

    public void setMainTeaching1(String mainTeaching1) {
        this.mainTeaching1 = mainTeaching1;
    }

    public String getMainTeaching2() {
        return mainTeaching2;
    }

    public void setMainTeaching2(String mainTeaching2) {
        this.mainTeaching2 = mainTeaching2;
    }

    public String getSubTeaching1() {
        return subTeaching1;
    }

    public void setSubTeaching1(String subTeaching1) {
        this.subTeaching1 = subTeaching1;
    }

    public String getSubTeaching2() {
        return subTeaching2;
    }

    public void setSubTeaching2(String subTeaching2) {
        this.subTeaching2 = subTeaching2;
    }

    public String getSubTeaching3() {
        return subTeaching3;
    }

    public void setSubTeaching3(String subTeaching3) {
        this.subTeaching3 = subTeaching3;
    }

    public String getSubTeaching4() {
        return subTeaching4;
    }

    public void setSubTeaching4(String subTeaching4) {
        this.subTeaching4 = subTeaching4;
    }

    public String getSubTeaching5() {
        return subTeaching5;
    }

    public void setSubTeaching5(String subTeaching5) {
        this.subTeaching5 = subTeaching5;
    }

    public String getSubTeaching6() {
        return subTeaching6;
    }

    public void setSubTeaching6(String subTeaching6) {
        this.subTeaching6 = subTeaching6;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
