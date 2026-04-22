package koreatech.cse.domain.dto;

import koreatech.cse.domain.role.professor.ProfLectureMethod;

public class ProfLectureMethodRequest {
    private int id;
    private String mainTeaching1;
    private String mainTeaching2;
    private String subTeaching1;
    private String subTeaching2;
    private String ref1; private String ref2; private String ref3;
    private String ref4; private String ref5; private String ref6;
    private String note;
    private String[] lectureMethodCheckbox;
    private String lectureMethodOther;
    private String[] evaluationMethodCheckbox;
    private String evaluationMethodOther;
    private String[] educationalMediumCheckbox;
    private String educationalMediumOther;
    private String[] equipmentCheckbox;
    private String equipmentOther;

    public ProfLectureMethod toEntity() {
        ProfLectureMethod pm = new ProfLectureMethod();
        pm.setId(id);
        pm.setMainTeaching1(mainTeaching1);
        pm.setMainTeaching2(mainTeaching2);
        pm.setSubTeaching1(subTeaching1);
        pm.setSubTeaching2(subTeaching2);
        pm.setRef1(ref1); pm.setRef2(ref2); pm.setRef3(ref3);
        pm.setRef4(ref4); pm.setRef5(ref5); pm.setRef6(ref6);
        pm.setNote(note);
        pm.setLectureMethodCheckbox(lectureMethodCheckbox);
        pm.setLectureMethodOther(lectureMethodOther);
        pm.setEvaluationMethodCheckbox(evaluationMethodCheckbox);
        pm.setEvaluationMethodOther(evaluationMethodOther);
        pm.setEducationalMediumCheckbox(educationalMediumCheckbox);
        pm.setEducationalMediumOther(educationalMediumOther);
        pm.setEquipmentCheckbox(equipmentCheckbox);
        pm.setEquipmentOther(equipmentOther);
        return pm;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMainTeaching1() { return mainTeaching1; }
    public void setMainTeaching1(String v) { this.mainTeaching1 = v; }
    public String getMainTeaching2() { return mainTeaching2; }
    public void setMainTeaching2(String v) { this.mainTeaching2 = v; }

    public String getSubTeaching1() { return subTeaching1; }
    public void setSubTeaching1(String v) { this.subTeaching1 = v; }
    public String getSubTeaching2() { return subTeaching2; }
    public void setSubTeaching2(String v) { this.subTeaching2 = v; }

    public String getRef1() { return ref1; } public void setRef1(String v) { this.ref1 = v; }
    public String getRef2() { return ref2; } public void setRef2(String v) { this.ref2 = v; }
    public String getRef3() { return ref3; } public void setRef3(String v) { this.ref3 = v; }
    public String getRef4() { return ref4; } public void setRef4(String v) { this.ref4 = v; }
    public String getRef5() { return ref5; } public void setRef5(String v) { this.ref5 = v; }
    public String getRef6() { return ref6; } public void setRef6(String v) { this.ref6 = v; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public String[] getLectureMethodCheckbox() { return lectureMethodCheckbox; }
    public void setLectureMethodCheckbox(String[] v) { this.lectureMethodCheckbox = v; }
    public String getLectureMethodOther() { return lectureMethodOther; }
    public void setLectureMethodOther(String v) { this.lectureMethodOther = v; }

    public String[] getEvaluationMethodCheckbox() { return evaluationMethodCheckbox; }
    public void setEvaluationMethodCheckbox(String[] v) { this.evaluationMethodCheckbox = v; }
    public String getEvaluationMethodOther() { return evaluationMethodOther; }
    public void setEvaluationMethodOther(String v) { this.evaluationMethodOther = v; }

    public String[] getEducationalMediumCheckbox() { return educationalMediumCheckbox; }
    public void setEducationalMediumCheckbox(String[] v) { this.educationalMediumCheckbox = v; }
    public String getEducationalMediumOther() { return educationalMediumOther; }
    public void setEducationalMediumOther(String v) { this.educationalMediumOther = v; }

    public String[] getEquipmentCheckbox() { return equipmentCheckbox; }
    public void setEquipmentCheckbox(String[] v) { this.equipmentCheckbox = v; }
    public String getEquipmentOther() { return equipmentOther; }
    public void setEquipmentOther(String v) { this.equipmentOther = v; }
}
