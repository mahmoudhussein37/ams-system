package koreatech.cse.domain.role.professor;

import java.io.Serializable;

public class Assessment implements Serializable {
    private static final long serialVersionUID = 43249L;

    private int id;
    private int userId; //student user id
    private int profCourseId;

    private String comment;

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


    private int itemId1;
    private int itemId2;
    private int itemId3;
    private int itemId4;
    private int itemId5;
    private int itemId6;
    private int itemId7;
    private int itemId8;
    private int itemId9;
    private int itemId10;
    private int itemId11;
    private int itemId12;
    private int itemId13;
    private int itemId14;
    private int itemId15;
    private int itemId16;
    private int itemId17;
    private int itemId18;
    private int itemId19;
    private int itemId20;
    private int itemId21;
    private int itemId22;
    private int itemId23;
    private int itemId24;
    private int itemId25;

    private int numItems;

    public void setItemId(int id, int index) {
        switch (index) {
            case 1:
                itemId1 = id;
                break;
            case 2:
                itemId2 = id;
                break;
            case 3:
                itemId3 = id;
                break;
            case 4:
                itemId4 = id;
                break;
            case 5:
                itemId5 = id;
                break;
            case 6:
                itemId6 = id;
                break;
            case 7:
                itemId7 = id;
                break;
            case 8:
                itemId8 = id;
                break;
            case 9:
                itemId9 = id;
                break;
            case 10:
                itemId10 = id;
            case 11:
                itemId11 = id;
                break;
            case 12:
                itemId12 = id;
                break;
            case 13:
                itemId13 = id;
                break;
            case 14:
                itemId14 = id;
                break;
            case 15:
                itemId15 = id;
                break;
            case 16:
                itemId16 = id;
                break;
            case 17:
                itemId17 = id;
                break;
            case 18:
                itemId18 = id;
                break;
            case 19:
                itemId19 = id;
                break;
            case 20:
                itemId20 = id;
                break;
            case 21:
                itemId21 = id;
                break;
            case 22:
                itemId22 = id;
                break;
            case 23:
                itemId23 = id;
                break;
            case 24:
                itemId24 = id;
                break;
            case 25:
                itemId25 = id;
                break;
            default:
        }
    }

    public int getItemId(int id) {
        switch (id) {
            case 1:
                return itemId1;
            case 2:
                return itemId2;
            case 3:
                return itemId3;
            case 4:
                return itemId4;
            case 5:
                return itemId5;
            case 6:
                return itemId6;
            case 7:
                return itemId7;
            case 8:
                return itemId8;
            case 9:
                return itemId9;
            case 10:
                return itemId10;
            case 11:
                return itemId11;
            case 12:
                return itemId12;
            case 13:
                return itemId13;
            case 14:
                return itemId14;
            case 15:
                return itemId15;
            case 16:
                return itemId16;
            case 17:
                return itemId17;
            case 18:
                return itemId18;
            case 19:
                return itemId19;
            case 20:
                return itemId20;
            case 21:
                return itemId21;
            case 22:
                return itemId22;
            case 23:
                return itemId23;
            case 24:
                return itemId24;
            case 25:
                return itemId25;
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

    public int getItemId1() {
        return itemId1;
    }

    public void setItemId1(int itemId1) {
        this.itemId1 = itemId1;
    }

    public int getItemId2() {
        return itemId2;
    }

    public void setItemId2(int itemId2) {
        this.itemId2 = itemId2;
    }

    public int getItemId3() {
        return itemId3;
    }

    public void setItemId3(int itemId3) {
        this.itemId3 = itemId3;
    }

    public int getItemId4() {
        return itemId4;
    }

    public void setItemId4(int itemId4) {
        this.itemId4 = itemId4;
    }

    public int getItemId5() {
        return itemId5;
    }

    public void setItemId5(int itemId5) {
        this.itemId5 = itemId5;
    }

    public int getItemId6() {
        return itemId6;
    }

    public void setItemId6(int itemId6) {
        this.itemId6 = itemId6;
    }

    public int getItemId7() {
        return itemId7;
    }

    public void setItemId7(int itemId7) {
        this.itemId7 = itemId7;
    }

    public int getItemId8() {
        return itemId8;
    }

    public void setItemId8(int itemId8) {
        this.itemId8 = itemId8;
    }

    public int getItemId9() {
        return itemId9;
    }

    public void setItemId9(int itemId9) {
        this.itemId9 = itemId9;
    }

    public int getItemId10() {
        return itemId10;
    }

    public void setItemId10(int itemId10) {
        this.itemId10 = itemId10;
    }

    public int getItemId11() {
        return itemId11;
    }

    public void setItemId11(int itemId11) {
        this.itemId11 = itemId11;
    }

    public int getItemId12() {
        return itemId12;
    }

    public void setItemId12(int itemId12) {
        this.itemId12 = itemId12;
    }

    public int getItemId13() {
        return itemId13;
    }

    public void setItemId13(int itemId13) {
        this.itemId13 = itemId13;
    }

    public int getItemId14() {
        return itemId14;
    }

    public void setItemId14(int itemId14) {
        this.itemId14 = itemId14;
    }

    public int getItemId15() {
        return itemId15;
    }

    public void setItemId15(int itemId15) {
        this.itemId15 = itemId15;
    }

    public int getItemId16() {
        return itemId16;
    }

    public void setItemId16(int itemId16) {
        this.itemId16 = itemId16;
    }

    public int getItemId17() {
        return itemId17;
    }

    public void setItemId17(int itemId17) {
        this.itemId17 = itemId17;
    }

    public int getItemId18() {
        return itemId18;
    }

    public void setItemId18(int itemId18) {
        this.itemId18 = itemId18;
    }

    public int getItemId19() {
        return itemId19;
    }

    public void setItemId19(int itemId19) {
        this.itemId19 = itemId19;
    }

    public int getItemId20() {
        return itemId20;
    }

    public void setItemId20(int itemId20) {
        this.itemId20 = itemId20;
    }

    public int getItemId21() {
        return itemId21;
    }

    public void setItemId21(int itemId21) {
        this.itemId21 = itemId21;
    }

    public int getItemId22() {
        return itemId22;
    }

    public void setItemId22(int itemId22) {
        this.itemId22 = itemId22;
    }

    public int getItemId23() {
        return itemId23;
    }

    public void setItemId23(int itemId23) {
        this.itemId23 = itemId23;
    }

    public int getItemId24() {
        return itemId24;
    }

    public void setItemId24(int itemId24) {
        this.itemId24 = itemId24;
    }

    public int getItemId25() {
        return itemId25;
    }

    public void setItemId25(int itemId25) {
        this.itemId25 = itemId25;
    }

    public int getNumItems() {
        return numItems;
    }

    public void setNumItems(int numItems) {
        this.numItems = numItems;
    }
}
