package koreatech.cse.domain.role.professor;

import koreatech.cse.domain.User;
import koreatech.cse.domain.univ.Semester;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class Cqi implements Serializable {
    private static final long serialVersionUID = 123423449L;

    private int id;
    private int userId;
    private int courseId;
    private int profCourseId;
    private int semesterId;
    private int divide;

    private User professorUser;
    private Semester semester;
    private int score1;//course learning objective score
    private int score2;
    private int score3;
    private int score4;
    private int score5;
    private int score6;

    private String problem; //Improvement effects and problems

    private String plan; //Comprehensive lecture improvement plan for future lectures

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

    public int getProfCourseId() {
        return profCourseId;
    }

    public void setProfCourseId(int profCourseId) {
        this.profCourseId = profCourseId;
    }

    public int getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }

    public int getDivide() {
        return divide;
    }

    public void setDivide(int divide) {
        this.divide = divide;
    }

    public User getProfessorUser() {
        return professorUser;
    }

    public void setProfessorUser(User professorUser) {
        this.professorUser = professorUser;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
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

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
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

            default:
                return 0;
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
