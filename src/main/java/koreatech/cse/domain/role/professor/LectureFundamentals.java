package koreatech.cse.domain.role.professor;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LectureFundamentals implements Serializable {
    private static final long serialVersionUID = 4343219L;
    private static final Logger logger = LoggerFactory.getLogger(LectureFundamentals.class);


    private int id;
    private int profCourseId;
    private int courseId;
    private int userId; //professor user id;
    private String intro;


    private Integer rateAssignment;
    private Integer rateMid;
    private Integer rateFinal;
    private Integer rateOptional;

    private String clo1;
    private String la1;
    private String reflect1;
    private String obj1;
    private String comp1;

    private String clo2;
    private String la2;
    private String reflect2;
    private String obj2;
    private String comp2;

    private String clo3;
    private String la3;
    private String reflect3;
    private String obj3;
    private String comp3;

    private String clo4;
    private String la4;
    private String reflect4;
    private String obj4;
    private String comp4;

    private String clo5;
    private String la5;
    private String reflect5;
    private String obj5;
    private String comp5;

    private String clo6;
    private String la6;
    private String reflect6;
    private String obj6;
    private String comp6;

    private String pl1;
    private String pl2;
    private String pl3;


    private String[] ref1Checkbox;
    private String[] ref2Checkbox;
    private String[] ref3Checkbox;
    private String[] ref4Checkbox;
    private String[] ref5Checkbox;
    private String[] ref6Checkbox;


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



    public int getRateAssignment() {
        return rateAssignment != null ? rateAssignment : 0;
    }

    public void setRateAssignment(Integer rateAssignment) {
        this.rateAssignment = rateAssignment;
    }

    public int getRateMid() {
        return rateMid != null ? rateMid : 0;
    }

    public void setRateMid(Integer rateMid) {
        this.rateMid = rateMid;
    }

    public int getRateFinal() {
        return rateFinal != null ? rateFinal : 0;
    }

    public void setRateFinal(Integer rateFinal) {
        this.rateFinal = rateFinal;
    }

    public int getRateOptional() {
        return rateOptional != null ? rateOptional : 0;
    }

    public void setRateOptional(Integer rateOptional) {
        this.rateOptional = rateOptional;
    }

    public int getProfCourseId() {
        return profCourseId;
    }

    public void setProfCourseId(int profCourseId) {
        this.profCourseId = profCourseId;
    }

    public String getClo1() {
        return clo1;
    }

    public void setClo1(String clo1) {
        this.clo1 = clo1;
    }

    public String getLa1() {
        return la1;
    }

    public void setLa1(String la1) {
        this.la1 = la1;
    }

    public String getReflect1() {
        return reflect1;
    }

    public void setReflect1(String reflect1) {
        this.reflect1 = reflect1;
    }

    public String getObj1() {
        return obj1;
    }

    public void setObj1(String obj1) {
        this.obj1 = obj1;
    }

    public String getComp1() {
        return comp1;
    }

    public void setComp1(String comp1) {
        this.comp1 = comp1;
    }

    public String getClo2() {
        return clo2;
    }

    public void setClo2(String clo2) {
        this.clo2 = clo2;
    }

    public String getLa2() {
        return la2;
    }

    public void setLa2(String la2) {
        this.la2 = la2;
    }

    public String getReflect2() {
        return reflect2;
    }

    public void setReflect2(String reflect2) {
        this.reflect2 = reflect2;
    }

    public String getObj2() {
        return obj2;
    }

    public void setObj2(String obj2) {
        this.obj2 = obj2;
    }

    public String getComp2() {
        return comp2;
    }

    public void setComp2(String comp2) {
        this.comp2 = comp2;
    }

    public String getClo3() {
        return clo3;
    }

    public void setClo3(String clo3) {
        this.clo3 = clo3;
    }

    public String getLa3() {
        return la3;
    }

    public void setLa3(String la3) {
        this.la3 = la3;
    }

    public String getReflect3() {
        return reflect3;
    }

    public void setReflect3(String reflect3) {
        this.reflect3 = reflect3;
    }

    public String getObj3() {
        return obj3;
    }

    public void setObj3(String obj3) {
        this.obj3 = obj3;
    }

    public String getComp3() {
        return comp3;
    }

    public void setComp3(String comp3) {
        this.comp3 = comp3;
    }

    public String getClo4() {
        return clo4;
    }

    public void setClo4(String clo4) {
        this.clo4 = clo4;
    }

    public String getLa4() {
        return la4;
    }

    public void setLa4(String la4) {
        this.la4 = la4;
    }

    public String getReflect4() {
        return reflect4;
    }

    public void setReflect4(String reflect4) {
        this.reflect4 = reflect4;
    }

    public String getObj4() {
        return obj4;
    }

    public void setObj4(String obj4) {
        this.obj4 = obj4;
    }

    public String getComp4() {
        return comp4;
    }

    public void setComp4(String comp4) {
        this.comp4 = comp4;
    }

    public String getClo5() {
        return clo5;
    }

    public void setClo5(String clo5) {
        this.clo5 = clo5;
    }

    public String getLa5() {
        return la5;
    }

    public void setLa5(String la5) {
        this.la5 = la5;
    }

    public String getReflect5() {
        return reflect5;
    }

    public void setReflect5(String reflect5) {
        this.reflect5 = reflect5;
    }

    public String getObj5() {
        return obj5;
    }

    public void setObj5(String obj5) {
        this.obj5 = obj5;
    }

    public String getComp5() {
        return comp5;
    }

    public void setComp5(String comp5) {
        this.comp5 = comp5;
    }

    public String getClo6() {
        return clo6;
    }

    public void setClo6(String clo6) {
        this.clo6 = clo6;
    }

    public String getLa6() {
        return la6;
    }

    public void setLa6(String la6) {
        this.la6 = la6;
    }

    public String getReflect6() {
        return reflect6;
    }

    public void setReflect6(String reflect6) {
        this.reflect6 = reflect6;
    }

    public String getObj6() {
        return obj6;
    }

    public void setObj6(String obj6) {
        this.obj6 = obj6;
    }

    public String getComp6() {
        return comp6;
    }

    public void setComp6(String comp6) {
        this.comp6 = comp6;
    }

    public String getPl1() {
        return pl1;
    }

    public void setPl1(String pl1) {
        this.pl1 = pl1;
    }

    public String getPl2() {
        return pl2;
    }

    public void setPl2(String pl2) {
        this.pl2 = pl2;
    }

    public String getPl3() {
        return pl3;
    }

    public void setPl3(String pl3) {
        this.pl3 = pl3;
    }

    public String[] getRef1Checkbox() {
        return ref1Checkbox;
    }

    public void setRef1Checkbox(String[] ref1Checkbox) {
        this.ref1Checkbox = ref1Checkbox;
    }

    public String[] getRef2Checkbox() {
        return ref2Checkbox;
    }

    public void setRef2Checkbox(String[] ref2Checkbox) {
        this.ref2Checkbox = ref2Checkbox;
    }

    public String[] getRef3Checkbox() {
        return ref3Checkbox;
    }

    public void setRef3Checkbox(String[] ref3Checkbox) {
        this.ref3Checkbox = ref3Checkbox;
    }

    public String[] getRef4Checkbox() {
        return ref4Checkbox;
    }

    public void setRef4Checkbox(String[] ref4Checkbox) {
        this.ref4Checkbox = ref4Checkbox;
    }

    public String[] getRef5Checkbox() {
        return ref5Checkbox;
    }

    public void setRef5Checkbox(String[] ref5Checkbox) {
        this.ref5Checkbox = ref5Checkbox;
    }

    public String[] getRef6Checkbox() {
        return ref6Checkbox;
    }

    public void setRef6Checkbox(String[] ref6Checkbox) {
        this.ref6Checkbox = ref6Checkbox;
    }


    public String getClo(int id) {
        switch (id) {
            case 1:
                return clo1;
            case 2:
                return clo2;
            case 3:
                return clo3;
            case 4:
                return clo4;
            case 5:
                return clo5;
            case 6:
                return clo6;

            default:
                return "";
        }
    }

    public List<String> toStringList(String str) {
        List<String> splitList = new ArrayList<>();
        try {
            String[] split = str.split(",");
            for(String s:split) {
                splitList.add(s);
            }
        } catch(Exception e) {
            logger.error("String parse error", e);
        }
        return splitList;
    }

    public Boolean hasValue(String str, String v) {
        try {
            String[] split = str.split(",");
            for(String s:split) {
                if(s.equals(v))
                    return true;
            }
        } catch(Exception e) {
            logger.error("String parse error", e);
        }
        return false;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
