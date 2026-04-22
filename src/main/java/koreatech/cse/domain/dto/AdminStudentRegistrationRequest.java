package koreatech.cse.domain.dto;

public class AdminStudentRegistrationRequest {
    private String number;
    private String firstName;
    private String lastName;
    private int divisionId;
    private int advisorId;
    private int schoolYear;
    private String status;

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public int getDivisionId() { return divisionId; }
    public void setDivisionId(int divisionId) { this.divisionId = divisionId; }

    public int getAdvisorId() { return advisorId; }
    public void setAdvisorId(int advisorId) { this.advisorId = advisorId; }

    public int getSchoolYear() { return schoolYear; }
    public void setSchoolYear(int schoolYear) { this.schoolYear = schoolYear; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
