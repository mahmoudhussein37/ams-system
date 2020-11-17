package koreatech.cse.domain.role.professor;

import java.io.Serializable;

public class LectureContents implements Serializable {
    private static final long serialVersionUID = 43432179L;

    private int id;
    private int profCourseId;
    private int courseId;
    private int userId; //professor user id;

    private String content1;
    private String content2;
    private String content3;
    private String content4;
    private String content5;
    private String content6;
    private String content7;
    private String content8;
    private String content9;
    private String content10;
    private String content11;
    private String content12;
    private String content13;
    private String content14;
    private String content15;
    


    private String note1;
    private String note2;
    private String note3;
    private String note4;
    private String note5;
    private String note6;
    private String note7;
    private String note8;
    private String note9;
    private String note10;
    private String note11;
    private String note12;
    private String note13;
    private String note14;
    private String note15;




    public void setContent(String text, int index) {
        switch (index) {
            case 1:
                content1 = text;
                break;
            case 2:
                content2 = text;
                break;
            case 3:
                content3 = text;
                break;
            case 4:
                content4 = text;
                break;
            case 5:
                content5 = text;
                break;
            case 6:
                content6 = text;
                break;
            case 7:
                content7 = text;
                break;
            case 8:
                content8 = text;
                break;
            case 9:
                content9 = text;
                break;
            case 10:
                content10 = text;
            case 11:
                content11 = text;
                break;
            case 12:
                content12 = text;
                break;
            case 13:
                content13 = text;
                break;
            case 14:
                content14 = text;
                break;
            case 15:
                content15 = text;
                break;

            default:
        }
    }

    public String getContent(int id) {
        switch (id) {
            case 1:
                return content1;
            case 2:
                return content2;
            case 3:
                return content3;
            case 4:
                return content4;
            case 5:
                return content5;
            case 6:
                return content6;
            case 7:
                return content7;
            case 8:
                return content8;
            case 9:
                return content9;
            case 10:
                return content10;
            case 11:
                return content11;
            case 12:
                return content12;
            case 13:
                return content13;
            case 14:
                return content14;
            case 15:
                return content15;

            default:
                return "";
        }
    }

    public void setNote(String text, int index) {
        switch (index) {
            case 1:
                note1 = text;
                break;
            case 2:
                note2 = text;
                break;
            case 3:
                note3 = text;
                break;
            case 4:
                note4 = text;
                break;
            case 5:
                note5 = text;
                break;
            case 6:
                note6 = text;
                break;
            case 7:
                note7 = text;
                break;
            case 8:
                note8 = text;
                break;
            case 9:
                note9 = text;
                break;
            case 10:
                note10 = text;
            case 11:
                note11 = text;
                break;
            case 12:
                note12 = text;
                break;
            case 13:
                note13 = text;
                break;
            case 14:
                note14 = text;
                break;
            case 15:
                note15 = text;
                break;

            default:
        }
    }

    public String getNote(int id) {
        switch (id) {
            case 1:
                return note1;
            case 2:
                return note2;
            case 3:
                return note3;
            case 4:
                return note4;
            case 5:
                return note5;
            case 6:
                return note6;
            case 7:
                return note7;
            case 8:
                return note8;
            case 9:
                return note9;
            case 10:
                return note10;
            case 11:
                return note11;
            case 12:
                return note12;
            case 13:
                return note13;
            case 14:
                return note14;
            case 15:
                return note15;

            default:
                return "";
        }
    }

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

    public String getContent1() {
        return content1;
    }

    public void setContent1(String content1) {
        this.content1 = content1;
    }

    public String getContent2() {
        return content2;
    }

    public void setContent2(String content2) {
        this.content2 = content2;
    }

    public String getContent3() {
        return content3;
    }

    public void setContent3(String content3) {
        this.content3 = content3;
    }

    public String getContent4() {
        return content4;
    }

    public void setContent4(String content4) {
        this.content4 = content4;
    }

    public String getContent5() {
        return content5;
    }

    public void setContent5(String content5) {
        this.content5 = content5;
    }

    public String getContent6() {
        return content6;
    }

    public void setContent6(String content6) {
        this.content6 = content6;
    }

    public String getContent7() {
        return content7;
    }

    public void setContent7(String content7) {
        this.content7 = content7;
    }

    public String getContent8() {
        return content8;
    }

    public void setContent8(String content8) {
        this.content8 = content8;
    }

    public String getContent9() {
        return content9;
    }

    public void setContent9(String content9) {
        this.content9 = content9;
    }

    public String getContent10() {
        return content10;
    }

    public void setContent10(String content10) {
        this.content10 = content10;
    }

    public String getContent11() {
        return content11;
    }

    public void setContent11(String content11) {
        this.content11 = content11;
    }

    public String getContent12() {
        return content12;
    }

    public void setContent12(String content12) {
        this.content12 = content12;
    }

    public String getContent13() {
        return content13;
    }

    public void setContent13(String content13) {
        this.content13 = content13;
    }

    public String getContent14() {
        return content14;
    }

    public void setContent14(String content14) {
        this.content14 = content14;
    }

    public String getContent15() {
        return content15;
    }

    public void setContent15(String content15) {
        this.content15 = content15;
    }

    public String getNote1() {
        return note1;
    }

    public void setNote1(String note1) {
        this.note1 = note1;
    }

    public String getNote2() {
        return note2;
    }

    public void setNote2(String note2) {
        this.note2 = note2;
    }

    public String getNote3() {
        return note3;
    }

    public void setNote3(String note3) {
        this.note3 = note3;
    }

    public String getNote4() {
        return note4;
    }

    public void setNote4(String note4) {
        this.note4 = note4;
    }

    public String getNote5() {
        return note5;
    }

    public void setNote5(String note5) {
        this.note5 = note5;
    }

    public String getNote6() {
        return note6;
    }

    public void setNote6(String note6) {
        this.note6 = note6;
    }

    public String getNote7() {
        return note7;
    }

    public void setNote7(String note7) {
        this.note7 = note7;
    }

    public String getNote8() {
        return note8;
    }

    public void setNote8(String note8) {
        this.note8 = note8;
    }

    public String getNote9() {
        return note9;
    }

    public void setNote9(String note9) {
        this.note9 = note9;
    }

    public String getNote10() {
        return note10;
    }

    public void setNote10(String note10) {
        this.note10 = note10;
    }

    public String getNote11() {
        return note11;
    }

    public void setNote11(String note11) {
        this.note11 = note11;
    }

    public String getNote12() {
        return note12;
    }

    public void setNote12(String note12) {
        this.note12 = note12;
    }

    public String getNote13() {
        return note13;
    }

    public void setNote13(String note13) {
        this.note13 = note13;
    }

    public String getNote14() {
        return note14;
    }

    public void setNote14(String note14) {
        this.note14 = note14;
    }

    public String getNote15() {
        return note15;
    }

    public void setNote15(String note15) {
        this.note15 = note15;
    }
}
