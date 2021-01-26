package koreatech.cse.domain.role.professor;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class Assessment implements Serializable {
    private static final long serialVersionUID = 43249L;

    private int id;
    private int userId; //student user id
    private int profCourseId;

    private String comment;

    private boolean confirm;

    private int score1;
    private int score2;
    private int score3;
    private int score4;
    private int score5;
    private int score6;
    private int score7;
    private int score8;
    private int score9;
    private int score10;
    private int score11;
    private int score12;
    private int score13;
    private int score14;
    private int score15;
    private int score16;
    private int score17;
    private int score18;
    private int score19;
    private int score20;
    private int score21;
    private int score22;
    private int score23;
    private int score24;
    private int score25;


    private int item1;
    private int item2;
    private int item3;
    private int item4;
    private int item5;
    private int item6;
    private int item7;
    private int item8;
    private int item9;
    private int item10;
    private int item11;
    private int item12;
    private int item13;
    private int item14;
    private int item15;
    private int item16;
    private int item17;
    private int item18;
    private int item19;
    private int item20;
    private int item21;
    private int item22;
    private int item23;
    private int item24;
    private int item25;

    private int numItems;

    public void setItem(int id, int index) {
        switch (index) {
            case 1:
                item1 = id;
                break;
            case 2:
                item2 = id;
                break;
            case 3:
                item3 = id;
                break;
            case 4:
                item4 = id;
                break;
            case 5:
                item5 = id;
                break;
            case 6:
                item6 = id;
                break;
            case 7:
                item7 = id;
                break;
            case 8:
                item8 = id;
                break;
            case 9:
                item9 = id;
                break;
            case 10:
                item10 = id;
            case 11:
                item11 = id;
                break;
            case 12:
                item12 = id;
                break;
            case 13:
                item13 = id;
                break;
            case 14:
                item14 = id;
                break;
            case 15:
                item15 = id;
                break;
            case 16:
                item16 = id;
                break;
            case 17:
                item17 = id;
                break;
            case 18:
                item18 = id;
                break;
            case 19:
                item19 = id;
                break;
            case 20:
                item20 = id;
                break;
            case 21:
                item21 = id;
                break;
            case 22:
                item22 = id;
                break;
            case 23:
                item23 = id;
                break;
            case 24:
                item24 = id;
                break;
            case 25:
                item25 = id;
                break;
            default:
        }
    }

    public int getItem(int id) {
        switch (id) {
            case 1:
                return item1;
            case 2:
                return item2;
            case 3:
                return item3;
            case 4:
                return item4;
            case 5:
                return item5;
            case 6:
                return item6;
            case 7:
                return item7;
            case 8:
                return item8;
            case 9:
                return item9;
            case 10:
                return item10;
            case 11:
                return item11;
            case 12:
                return item12;
            case 13:
                return item13;
            case 14:
                return item14;
            case 15:
                return item15;
            case 16:
                return item16;
            case 17:
                return item17;
            case 18:
                return item18;
            case 19:
                return item19;
            case 20:
                return item20;
            case 21:
                return item21;
            case 22:
                return item22;
            case 23:
                return item23;
            case 24:
                return item24;
            case 25:
                return item25;
            default:
                return 0;
        }
    }

    public int getScore(int id) {
        switch (id) {
            case 1:
                return score1;
            case 2:
                return score2;
            case 3:
                return score3;
            case 4:
                return score4;
            case 5:
                return score5;
            case 6:
                return score6;
            case 7:
                return score7;
            case 8:
                return score8;
            case 9:
                return score9;
            case 10:
                return score10;
            case 11:
                return score11;
            case 12:
                return score12;
            case 13:
                return score13;
            case 14:
                return score14;
            case 15:
                return score15;
            case 16:
                return score16;
            case 17:
                return score17;
            case 18:
                return score18;
            case 19:
                return score19;
            case 20:
                return score20;
            case 21:
                return score21;
            case 22:
                return score22;
            case 23:
                return score23;
            case 24:
                return score24;
            case 25:
                return score25;
            default:
                return 0;
        }
    }

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

    public int getProfCourseId() {
        return profCourseId;
    }

    public void setProfCourseId(int profCourseId) {
        this.profCourseId = profCourseId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getScore1() {
        return score1;
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }

    public int getScore2() {
        return score2;
    }

    public void setScore2(int score2) {
        this.score2 = score2;
    }

    public int getScore3() {
        return score3;
    }

    public void setScore3(int score3) {
        this.score3 = score3;
    }

    public int getScore4() {
        return score4;
    }

    public void setScore4(int score4) {
        this.score4 = score4;
    }

    public int getScore5() {
        return score5;
    }

    public void setScore5(int score5) {
        this.score5 = score5;
    }

    public int getScore6() {
        return score6;
    }

    public void setScore6(int score6) {
        this.score6 = score6;
    }

    public int getScore7() {
        return score7;
    }

    public void setScore7(int score7) {
        this.score7 = score7;
    }

    public int getScore8() {
        return score8;
    }

    public void setScore8(int score8) {
        this.score8 = score8;
    }

    public int getScore9() {
        return score9;
    }

    public void setScore9(int score9) {
        this.score9 = score9;
    }

    public int getScore10() {
        return score10;
    }

    public void setScore10(int score10) {
        this.score10 = score10;
    }

    public int getScore11() {
        return score11;
    }

    public void setScore11(int score11) {
        this.score11 = score11;
    }

    public int getScore12() {
        return score12;
    }

    public void setScore12(int score12) {
        this.score12 = score12;
    }

    public int getScore13() {
        return score13;
    }

    public void setScore13(int score13) {
        this.score13 = score13;
    }

    public int getScore14() {
        return score14;
    }

    public void setScore14(int score14) {
        this.score14 = score14;
    }

    public int getScore15() {
        return score15;
    }

    public void setScore15(int score15) {
        this.score15 = score15;
    }

    public int getScore16() {
        return score16;
    }

    public void setScore16(int score16) {
        this.score16 = score16;
    }

    public int getScore17() {
        return score17;
    }

    public void setScore17(int score17) {
        this.score17 = score17;
    }

    public int getScore18() {
        return score18;
    }

    public void setScore18(int score18) {
        this.score18 = score18;
    }

    public int getScore19() {
        return score19;
    }

    public void setScore19(int score19) {
        this.score19 = score19;
    }

    public int getScore20() {
        return score20;
    }

    public void setScore20(int score20) {
        this.score20 = score20;
    }

    public int getScore21() {
        return score21;
    }

    public void setScore21(int score21) {
        this.score21 = score21;
    }

    public int getScore22() {
        return score22;
    }

    public void setScore22(int score22) {
        this.score22 = score22;
    }

    public int getScore23() {
        return score23;
    }

    public void setScore23(int score23) {
        this.score23 = score23;
    }

    public int getScore24() {
        return score24;
    }

    public void setScore24(int score24) {
        this.score24 = score24;
    }

    public int getScore25() {
        return score25;
    }

    public void setScore25(int score25) {
        this.score25 = score25;
    }

    public int getItem1() {
        return item1;
    }

    public void setItem1(int item1) {
        this.item1 = item1;
    }

    public int getItem2() {
        return item2;
    }

    public void setItem2(int item2) {
        this.item2 = item2;
    }

    public int getItem3() {
        return item3;
    }

    public void setItem3(int item3) {
        this.item3 = item3;
    }

    public int getItem4() {
        return item4;
    }

    public void setItem4(int item4) {
        this.item4 = item4;
    }

    public int getItem5() {
        return item5;
    }

    public void setItem5(int item5) {
        this.item5 = item5;
    }

    public int getItem6() {
        return item6;
    }

    public void setItem6(int item6) {
        this.item6 = item6;
    }

    public int getItem7() {
        return item7;
    }

    public void setItem7(int item7) {
        this.item7 = item7;
    }

    public int getItem8() {
        return item8;
    }

    public void setItem8(int item8) {
        this.item8 = item8;
    }

    public int getItem9() {
        return item9;
    }

    public void setItem9(int item9) {
        this.item9 = item9;
    }

    public int getItem10() {
        return item10;
    }

    public void setItem10(int item10) {
        this.item10 = item10;
    }

    public int getItem11() {
        return item11;
    }

    public void setItem11(int item11) {
        this.item11 = item11;
    }

    public int getItem12() {
        return item12;
    }

    public void setItem12(int item12) {
        this.item12 = item12;
    }

    public int getItem13() {
        return item13;
    }

    public void setItem13(int item13) {
        this.item13 = item13;
    }

    public int getItem14() {
        return item14;
    }

    public void setItem14(int item14) {
        this.item14 = item14;
    }

    public int getItem15() {
        return item15;
    }

    public void setItem15(int item15) {
        this.item15 = item15;
    }

    public int getItem16() {
        return item16;
    }

    public void setItem16(int item16) {
        this.item16 = item16;
    }

    public int getItem17() {
        return item17;
    }

    public void setItem17(int item17) {
        this.item17 = item17;
    }

    public int getItem18() {
        return item18;
    }

    public void setItem18(int item18) {
        this.item18 = item18;
    }

    public int getItem19() {
        return item19;
    }

    public void setItem19(int item19) {
        this.item19 = item19;
    }

    public int getItem20() {
        return item20;
    }

    public void setItem20(int item20) {
        this.item20 = item20;
    }

    public int getItem21() {
        return item21;
    }

    public void setItem21(int item21) {
        this.item21 = item21;
    }

    public int getItem22() {
        return item22;
    }

    public void setItem22(int item22) {
        this.item22 = item22;
    }

    public int getItem23() {
        return item23;
    }

    public void setItem23(int item23) {
        this.item23 = item23;
    }

    public int getItem24() {
        return item24;
    }

    public void setItem24(int item24) {
        this.item24 = item24;
    }

    public int getItem25() {
        return item25;
    }

    public void setItem25(int item25) {
        this.item25 = item25;
    }

    public int getNumItems() {
        return numItems;
    }

    public void setNumItems(int numItems) {
        this.numItems = numItems;
    }

    public int getScoreByItemId(int itemId) {
        for(int i=1; i<=20; i++) {
            int id = this.getItem(i);
            if(id == itemId)
                return getScore(i);
        }
        return -1;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
