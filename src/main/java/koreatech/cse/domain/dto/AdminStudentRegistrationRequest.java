package koreatech.cse.domain.dto;

import koreatech.cse.domain.Contact;

public class AdminStudentRegistrationRequest {
    private String number;
    private int divisionId;
    private int advisorId;
    private int schoolYear;
    private String status;
    private Contact contact = new Contact();

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public Contact getContact() { return contact; }
    public void setContact(Contact contact) { this.contact = contact == null ? new Contact() : contact; }

    public int getDivisionId() { return divisionId; }
    public void setDivisionId(int divisionId) { this.divisionId = divisionId; }

    public int getAdvisorId() { return advisorId; }
    public void setAdvisorId(int advisorId) { this.advisorId = advisorId; }

    public int getSchoolYear() { return schoolYear; }
    public void setSchoolYear(int schoolYear) { this.schoolYear = schoolYear; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
