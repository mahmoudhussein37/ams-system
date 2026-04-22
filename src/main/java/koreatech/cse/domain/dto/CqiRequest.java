package koreatech.cse.domain.dto;

public class CqiRequest {
    private int id;
    private int score1;
    private int score2;
    private int score3;
    private int score4;
    private int score5;
    private int score6;
    private String problem;
    private String plan;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getScore1() { return score1; }
    public void setScore1(int score1) { this.score1 = score1; }

    public int getScore2() { return score2; }
    public void setScore2(int score2) { this.score2 = score2; }

    public int getScore3() { return score3; }
    public void setScore3(int score3) { this.score3 = score3; }

    public int getScore4() { return score4; }
    public void setScore4(int score4) { this.score4 = score4; }

    public int getScore5() { return score5; }
    public void setScore5(int score5) { this.score5 = score5; }

    public int getScore6() { return score6; }
    public void setScore6(int score6) { this.score6 = score6; }

    public String getProblem() { return problem; }
    public void setProblem(String problem) { this.problem = problem; }

    public String getPlan() { return plan; }
    public void setPlan(String plan) { this.plan = plan; }
}
