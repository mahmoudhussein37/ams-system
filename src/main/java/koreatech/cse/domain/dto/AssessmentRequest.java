package koreatech.cse.domain.dto;

import koreatech.cse.domain.role.professor.Assessment;

public class AssessmentRequest {
    private String comment;
    private int score1;  private int score2;  private int score3;  private int score4;  private int score5;
    private int score6;  private int score7;  private int score8;  private int score9;  private int score10;
    private int score11; private int score12; private int score13; private int score14; private int score15;
    private int score16; private int score17; private int score18; private int score19; private int score20;
    private int score21; private int score22; private int score23; private int score24; private int score25;
    private int item1;  private int item2;  private int item3;  private int item4;  private int item5;
    private int item6;  private int item7;  private int item8;  private int item9;  private int item10;
    private int item11; private int item12; private int item13; private int item14; private int item15;
    private int item16; private int item17; private int item18; private int item19; private int item20;
    private int item21; private int item22; private int item23; private int item24; private int item25;

    public Assessment toEntity() {
        Assessment a = new Assessment();
        a.setComment(comment);
        a.setScore1(score1);   a.setScore2(score2);   a.setScore3(score3);   a.setScore4(score4);   a.setScore5(score5);
        a.setScore6(score6);   a.setScore7(score7);   a.setScore8(score8);   a.setScore9(score9);   a.setScore10(score10);
        a.setScore11(score11); a.setScore12(score12); a.setScore13(score13); a.setScore14(score14); a.setScore15(score15);
        a.setScore16(score16); a.setScore17(score17); a.setScore18(score18); a.setScore19(score19); a.setScore20(score20);
        a.setScore21(score21); a.setScore22(score22); a.setScore23(score23); a.setScore24(score24); a.setScore25(score25);
        a.setItem1(item1);   a.setItem2(item2);   a.setItem3(item3);   a.setItem4(item4);   a.setItem5(item5);
        a.setItem6(item6);   a.setItem7(item7);   a.setItem8(item8);   a.setItem9(item9);   a.setItem10(item10);
        a.setItem11(item11); a.setItem12(item12); a.setItem13(item13); a.setItem14(item14); a.setItem15(item15);
        a.setItem16(item16); a.setItem17(item17); a.setItem18(item18); a.setItem19(item19); a.setItem20(item20);
        a.setItem21(item21); a.setItem22(item22); a.setItem23(item23); a.setItem24(item24); a.setItem25(item25);
        return a;
    }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public int getScore1() { return score1; }   public void setScore1(int v) { this.score1 = v; }
    public int getScore2() { return score2; }   public void setScore2(int v) { this.score2 = v; }
    public int getScore3() { return score3; }   public void setScore3(int v) { this.score3 = v; }
    public int getScore4() { return score4; }   public void setScore4(int v) { this.score4 = v; }
    public int getScore5() { return score5; }   public void setScore5(int v) { this.score5 = v; }
    public int getScore6() { return score6; }   public void setScore6(int v) { this.score6 = v; }
    public int getScore7() { return score7; }   public void setScore7(int v) { this.score7 = v; }
    public int getScore8() { return score8; }   public void setScore8(int v) { this.score8 = v; }
    public int getScore9() { return score9; }   public void setScore9(int v) { this.score9 = v; }
    public int getScore10() { return score10; } public void setScore10(int v) { this.score10 = v; }
    public int getScore11() { return score11; } public void setScore11(int v) { this.score11 = v; }
    public int getScore12() { return score12; } public void setScore12(int v) { this.score12 = v; }
    public int getScore13() { return score13; } public void setScore13(int v) { this.score13 = v; }
    public int getScore14() { return score14; } public void setScore14(int v) { this.score14 = v; }
    public int getScore15() { return score15; } public void setScore15(int v) { this.score15 = v; }
    public int getScore16() { return score16; } public void setScore16(int v) { this.score16 = v; }
    public int getScore17() { return score17; } public void setScore17(int v) { this.score17 = v; }
    public int getScore18() { return score18; } public void setScore18(int v) { this.score18 = v; }
    public int getScore19() { return score19; } public void setScore19(int v) { this.score19 = v; }
    public int getScore20() { return score20; } public void setScore20(int v) { this.score20 = v; }
    public int getScore21() { return score21; } public void setScore21(int v) { this.score21 = v; }
    public int getScore22() { return score22; } public void setScore22(int v) { this.score22 = v; }
    public int getScore23() { return score23; } public void setScore23(int v) { this.score23 = v; }
    public int getScore24() { return score24; } public void setScore24(int v) { this.score24 = v; }
    public int getScore25() { return score25; } public void setScore25(int v) { this.score25 = v; }

    public int getItem1() { return item1; }   public void setItem1(int v) { this.item1 = v; }
    public int getItem2() { return item2; }   public void setItem2(int v) { this.item2 = v; }
    public int getItem3() { return item3; }   public void setItem3(int v) { this.item3 = v; }
    public int getItem4() { return item4; }   public void setItem4(int v) { this.item4 = v; }
    public int getItem5() { return item5; }   public void setItem5(int v) { this.item5 = v; }
    public int getItem6() { return item6; }   public void setItem6(int v) { this.item6 = v; }
    public int getItem7() { return item7; }   public void setItem7(int v) { this.item7 = v; }
    public int getItem8() { return item8; }   public void setItem8(int v) { this.item8 = v; }
    public int getItem9() { return item9; }   public void setItem9(int v) { this.item9 = v; }
    public int getItem10() { return item10; } public void setItem10(int v) { this.item10 = v; }
    public int getItem11() { return item11; } public void setItem11(int v) { this.item11 = v; }
    public int getItem12() { return item12; } public void setItem12(int v) { this.item12 = v; }
    public int getItem13() { return item13; } public void setItem13(int v) { this.item13 = v; }
    public int getItem14() { return item14; } public void setItem14(int v) { this.item14 = v; }
    public int getItem15() { return item15; } public void setItem15(int v) { this.item15 = v; }
    public int getItem16() { return item16; } public void setItem16(int v) { this.item16 = v; }
    public int getItem17() { return item17; } public void setItem17(int v) { this.item17 = v; }
    public int getItem18() { return item18; } public void setItem18(int v) { this.item18 = v; }
    public int getItem19() { return item19; } public void setItem19(int v) { this.item19 = v; }
    public int getItem20() { return item20; } public void setItem20(int v) { this.item20 = v; }
    public int getItem21() { return item21; } public void setItem21(int v) { this.item21 = v; }
    public int getItem22() { return item22; } public void setItem22(int v) { this.item22 = v; }
    public int getItem23() { return item23; } public void setItem23(int v) { this.item23 = v; }
    public int getItem24() { return item24; } public void setItem24(int v) { this.item24 = v; }
    public int getItem25() { return item25; } public void setItem25(int v) { this.item25 = v; }
}
