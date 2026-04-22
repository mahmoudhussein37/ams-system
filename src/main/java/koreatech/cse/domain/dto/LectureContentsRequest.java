package koreatech.cse.domain.dto;

import koreatech.cse.domain.role.professor.LectureContents;

public class LectureContentsRequest {
    private int id;
    private String content1;  private String content2;  private String content3;
    private String content4;  private String content5;  private String content6;
    private String content7;  private String content8;  private String content9;
    private String content10; private String content11; private String content12;
    private String content13; private String content14; private String content15;
    private String note1;  private String note2;  private String note3;
    private String note4;  private String note5;  private String note6;
    private String note7;  private String note8;  private String note9;
    private String note10; private String note11; private String note12;
    private String note13; private String note14; private String note15;

    public LectureContents toEntity() {
        LectureContents lc = new LectureContents();
        lc.setId(id);
        lc.setContent(content1, 1); lc.setContent(content2, 2); lc.setContent(content3, 3);
        lc.setContent(content4, 4); lc.setContent(content5, 5); lc.setContent(content6, 6);
        lc.setContent(content7, 7); lc.setContent(content8, 8); lc.setContent(content9, 9);
        lc.setContent(content10, 10); lc.setContent(content11, 11); lc.setContent(content12, 12);
        lc.setContent(content13, 13); lc.setContent(content14, 14); lc.setContent(content15, 15);
        lc.setNote(note1, 1); lc.setNote(note2, 2); lc.setNote(note3, 3);
        lc.setNote(note4, 4); lc.setNote(note5, 5); lc.setNote(note6, 6);
        lc.setNote(note7, 7); lc.setNote(note8, 8); lc.setNote(note9, 9);
        lc.setNote(note10, 10); lc.setNote(note11, 11); lc.setNote(note12, 12);
        lc.setNote(note13, 13); lc.setNote(note14, 14); lc.setNote(note15, 15);
        return lc;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getContent1() { return content1; }   public void setContent1(String v) { this.content1 = v; }
    public String getContent2() { return content2; }   public void setContent2(String v) { this.content2 = v; }
    public String getContent3() { return content3; }   public void setContent3(String v) { this.content3 = v; }
    public String getContent4() { return content4; }   public void setContent4(String v) { this.content4 = v; }
    public String getContent5() { return content5; }   public void setContent5(String v) { this.content5 = v; }
    public String getContent6() { return content6; }   public void setContent6(String v) { this.content6 = v; }
    public String getContent7() { return content7; }   public void setContent7(String v) { this.content7 = v; }
    public String getContent8() { return content8; }   public void setContent8(String v) { this.content8 = v; }
    public String getContent9() { return content9; }   public void setContent9(String v) { this.content9 = v; }
    public String getContent10() { return content10; } public void setContent10(String v) { this.content10 = v; }
    public String getContent11() { return content11; } public void setContent11(String v) { this.content11 = v; }
    public String getContent12() { return content12; } public void setContent12(String v) { this.content12 = v; }
    public String getContent13() { return content13; } public void setContent13(String v) { this.content13 = v; }
    public String getContent14() { return content14; } public void setContent14(String v) { this.content14 = v; }
    public String getContent15() { return content15; } public void setContent15(String v) { this.content15 = v; }

    public String getNote1() { return note1; }   public void setNote1(String v) { this.note1 = v; }
    public String getNote2() { return note2; }   public void setNote2(String v) { this.note2 = v; }
    public String getNote3() { return note3; }   public void setNote3(String v) { this.note3 = v; }
    public String getNote4() { return note4; }   public void setNote4(String v) { this.note4 = v; }
    public String getNote5() { return note5; }   public void setNote5(String v) { this.note5 = v; }
    public String getNote6() { return note6; }   public void setNote6(String v) { this.note6 = v; }
    public String getNote7() { return note7; }   public void setNote7(String v) { this.note7 = v; }
    public String getNote8() { return note8; }   public void setNote8(String v) { this.note8 = v; }
    public String getNote9() { return note9; }   public void setNote9(String v) { this.note9 = v; }
    public String getNote10() { return note10; } public void setNote10(String v) { this.note10 = v; }
    public String getNote11() { return note11; } public void setNote11(String v) { this.note11 = v; }
    public String getNote12() { return note12; } public void setNote12(String v) { this.note12 = v; }
    public String getNote13() { return note13; } public void setNote13(String v) { this.note13 = v; }
    public String getNote14() { return note14; } public void setNote14(String v) { this.note14 = v; }
    public String getNote15() { return note15; } public void setNote15(String v) { this.note15 = v; }
}
