package koreatech.cse.domain.dto;

public class AdminProfessorRegistrationRequest {
    private String number;
    private String firstName;
    private String lastName;
    private int divisionId;

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public int getDivisionId() { return divisionId; }
    public void setDivisionId(int divisionId) { this.divisionId = divisionId; }
}
