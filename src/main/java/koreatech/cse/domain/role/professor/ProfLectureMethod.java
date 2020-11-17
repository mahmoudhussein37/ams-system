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
    private String ref1;
    private String ref2;
    private String ref3;
    private String ref4;
    private String ref5;
    private String ref6;
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

    public String getRef1() {
        return ref1;
    }

    public void setRef1(String ref1) {
        this.ref1 = ref1;
    }

    public String getRef2() {
        return ref2;
    }

    public void setRef2(String ref2) {
        this.ref2 = ref2;
    }

    public String getRef3() {
        return ref3;
    }

    public void setRef3(String ref3) {
        this.ref3 = ref3;
    }

    public String getRef4() {
        return ref4;
    }

    public void setRef4(String ref4) {
        this.ref4 = ref4;
    }

    public String getRef5() {
        return ref5;
    }

    public void setRef5(String ref5) {
        this.ref5 = ref5;
    }

    public String getRef6() {
        return ref6;
    }

    public void setRef6(String ref6) {
        this.ref6 = ref6;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
