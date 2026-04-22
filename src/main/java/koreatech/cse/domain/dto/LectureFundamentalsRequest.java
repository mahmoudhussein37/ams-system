package koreatech.cse.domain.dto;

import koreatech.cse.domain.role.professor.LectureFundamentals;

public class LectureFundamentalsRequest {
    private int id;
    private String intro;
    private Integer rateAssignment;
    private Integer rateMid;
    private Integer rateFinal;
    private Integer rateOptional;
    private String clo1; private String clo2; private String clo3;
    private String clo4; private String clo5; private String clo6;
    private String la1;  private String la2;  private String la3;
    private String la4;  private String la5;  private String la6;
    private String obj1; private String obj2; private String obj3;
    private String obj4; private String obj5; private String obj6;
    private String comp1; private String comp2; private String comp3;
    private String comp4; private String comp5; private String comp6;
    private String pl1; private String pl2; private String pl3;
    private String[] ref1Checkbox;
    private String[] ref2Checkbox;
    private String[] ref3Checkbox;
    private String[] ref4Checkbox;
    private String[] ref5Checkbox;
    private String[] ref6Checkbox;

    public LectureFundamentals toEntity() {
        LectureFundamentals lf = new LectureFundamentals();
        lf.setId(id);
        lf.setIntro(intro);
        lf.setRateAssignment(rateAssignment);
        lf.setRateMid(rateMid);
        lf.setRateFinal(rateFinal);
        lf.setRateOptional(rateOptional);
        lf.setClo1(clo1); lf.setClo2(clo2); lf.setClo3(clo3);
        lf.setClo4(clo4); lf.setClo5(clo5); lf.setClo6(clo6);
        lf.setLa1(la1); lf.setLa2(la2); lf.setLa3(la3);
        lf.setLa4(la4); lf.setLa5(la5); lf.setLa6(la6);
        lf.setObj1(obj1); lf.setObj2(obj2); lf.setObj3(obj3);
        lf.setObj4(obj4); lf.setObj5(obj5); lf.setObj6(obj6);
        lf.setComp1(comp1); lf.setComp2(comp2); lf.setComp3(comp3);
        lf.setComp4(comp4); lf.setComp5(comp5); lf.setComp6(comp6);
        lf.setPl1(pl1); lf.setPl2(pl2); lf.setPl3(pl3);
        lf.setRef1Checkbox(ref1Checkbox);
        lf.setRef2Checkbox(ref2Checkbox);
        lf.setRef3Checkbox(ref3Checkbox);
        lf.setRef4Checkbox(ref4Checkbox);
        lf.setRef5Checkbox(ref5Checkbox);
        lf.setRef6Checkbox(ref6Checkbox);
        return lf;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getIntro() { return intro; }
    public void setIntro(String intro) { this.intro = intro; }

    public Integer getRateAssignment() { return rateAssignment; }
    public void setRateAssignment(Integer rateAssignment) { this.rateAssignment = rateAssignment; }

    public Integer getRateMid() { return rateMid; }
    public void setRateMid(Integer rateMid) { this.rateMid = rateMid; }

    public Integer getRateFinal() { return rateFinal; }
    public void setRateFinal(Integer rateFinal) { this.rateFinal = rateFinal; }

    public Integer getRateOptional() { return rateOptional; }
    public void setRateOptional(Integer rateOptional) { this.rateOptional = rateOptional; }

    public String getClo1() { return clo1; } public void setClo1(String v) { this.clo1 = v; }
    public String getClo2() { return clo2; } public void setClo2(String v) { this.clo2 = v; }
    public String getClo3() { return clo3; } public void setClo3(String v) { this.clo3 = v; }
    public String getClo4() { return clo4; } public void setClo4(String v) { this.clo4 = v; }
    public String getClo5() { return clo5; } public void setClo5(String v) { this.clo5 = v; }
    public String getClo6() { return clo6; } public void setClo6(String v) { this.clo6 = v; }

    public String getLa1() { return la1; } public void setLa1(String v) { this.la1 = v; }
    public String getLa2() { return la2; } public void setLa2(String v) { this.la2 = v; }
    public String getLa3() { return la3; } public void setLa3(String v) { this.la3 = v; }
    public String getLa4() { return la4; } public void setLa4(String v) { this.la4 = v; }
    public String getLa5() { return la5; } public void setLa5(String v) { this.la5 = v; }
    public String getLa6() { return la6; } public void setLa6(String v) { this.la6 = v; }

    public String getObj1() { return obj1; } public void setObj1(String v) { this.obj1 = v; }
    public String getObj2() { return obj2; } public void setObj2(String v) { this.obj2 = v; }
    public String getObj3() { return obj3; } public void setObj3(String v) { this.obj3 = v; }
    public String getObj4() { return obj4; } public void setObj4(String v) { this.obj4 = v; }
    public String getObj5() { return obj5; } public void setObj5(String v) { this.obj5 = v; }
    public String getObj6() { return obj6; } public void setObj6(String v) { this.obj6 = v; }

    public String getComp1() { return comp1; } public void setComp1(String v) { this.comp1 = v; }
    public String getComp2() { return comp2; } public void setComp2(String v) { this.comp2 = v; }
    public String getComp3() { return comp3; } public void setComp3(String v) { this.comp3 = v; }
    public String getComp4() { return comp4; } public void setComp4(String v) { this.comp4 = v; }
    public String getComp5() { return comp5; } public void setComp5(String v) { this.comp5 = v; }
    public String getComp6() { return comp6; } public void setComp6(String v) { this.comp6 = v; }

    public String getPl1() { return pl1; } public void setPl1(String v) { this.pl1 = v; }
    public String getPl2() { return pl2; } public void setPl2(String v) { this.pl2 = v; }
    public String getPl3() { return pl3; } public void setPl3(String v) { this.pl3 = v; }

    public String[] getRef1Checkbox() { return ref1Checkbox; }
    public void setRef1Checkbox(String[] v) { this.ref1Checkbox = v; }
    public String[] getRef2Checkbox() { return ref2Checkbox; }
    public void setRef2Checkbox(String[] v) { this.ref2Checkbox = v; }
    public String[] getRef3Checkbox() { return ref3Checkbox; }
    public void setRef3Checkbox(String[] v) { this.ref3Checkbox = v; }
    public String[] getRef4Checkbox() { return ref4Checkbox; }
    public void setRef4Checkbox(String[] v) { this.ref4Checkbox = v; }
    public String[] getRef5Checkbox() { return ref5Checkbox; }
    public void setRef5Checkbox(String[] v) { this.ref5Checkbox = v; }
    public String[] getRef6Checkbox() { return ref6Checkbox; }
    public void setRef6Checkbox(String[] v) { this.ref6Checkbox = v; }
}
