package koreatech.cse.domain.role.professor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProfLectureMethod implements Serializable {
    private static final long serialVersionUID = 43432179L;
    private static final Logger logger = LoggerFactory.getLogger(ProfLectureMethod.class);

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


    private String lectureMethods;
    private String lectureMethodOther;

    private String evaluationMethods;
    private String evaluationMethodOther;

    private String educationalMediums;
    private String educationalMediumOther;

    private String equipments;
    private String equipmentOther;


    private String[] lectureMethodCheckbox;
    private String[] evaluationMethodCheckbox;
    private String[] educationalMediumCheckbox;
    private String[] equipmentCheckbox;





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

    public String getLectureMethods() {
        return lectureMethods;
    }

    public void setLectureMethods(String lectureMethods) {
        this.lectureMethods = lectureMethods;
    }

    public String getLectureMethodOther() {
        return lectureMethodOther;
    }

    public void setLectureMethodOther(String lectureMethodOther) {
        this.lectureMethodOther = lectureMethodOther;
    }

    public String getEvaluationMethods() {
        return evaluationMethods;
    }

    public void setEvaluationMethods(String evaluationMethods) {
        this.evaluationMethods = evaluationMethods;
    }

    public String getEvaluationMethodOther() {
        return evaluationMethodOther;
    }

    public void setEvaluationMethodOther(String evaluationMethodOther) {
        this.evaluationMethodOther = evaluationMethodOther;
    }

    public String getEducationalMediums() {
        return educationalMediums;
    }

    public void setEducationalMediums(String educationalMediums) {
        this.educationalMediums = educationalMediums;
    }

    public String getEducationalMediumOther() {
        return educationalMediumOther;
    }

    public void setEducationalMediumOther(String educationalMediumOther) {
        this.educationalMediumOther = educationalMediumOther;
    }

    public String getEquipments() {
        return equipments;
    }

    public void setEquipments(String equipments) {
        this.equipments = equipments;
    }

    public String getEquipmentOther() {
        return equipmentOther;
    }

    public void setEquipmentOther(String equipmentOther) {
        this.equipmentOther = equipmentOther;
    }

    public String[] getLectureMethodCheckbox() {
        return lectureMethodCheckbox;
    }

    public void setLectureMethodCheckbox(String[] lectureMethodCheckbox) {
        this.lectureMethodCheckbox = lectureMethodCheckbox;
    }

    public String[] getEvaluationMethodCheckbox() {
        return evaluationMethodCheckbox;
    }

    public void setEvaluationMethodCheckbox(String[] evaluationMethodCheckbox) {
        this.evaluationMethodCheckbox = evaluationMethodCheckbox;
    }

    public String[] getEducationalMediumCheckbox() {
        return educationalMediumCheckbox;
    }

    public void setEducationalMediumCheckbox(String[] educationalMediumCheckbox) {
        this.educationalMediumCheckbox = educationalMediumCheckbox;
    }

    public String[] getEquipmentCheckbox() {
        return equipmentCheckbox;
    }

    public void setEquipmentCheckbox(String[] equipmentCheckbox) {
        this.equipmentCheckbox = equipmentCheckbox;
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

    public Boolean hasValue(String str, int id) {
        try {
            String[] split = str.split(",");
            for(String s:split) {
                int v = Integer.parseInt(s);
                if(v == id)
                    return true;
            }
        } catch(Exception e) {
            logger.error("String parse error", e);
        }
        return false;
    }
}
