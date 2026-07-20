package koreatech.cse.domain.dto;

import koreatech.cse.domain.Contact;

public class AdminProfessorRegistrationRequest {
    private String number;
    private int divisionId;
    private Contact contact = new Contact();

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public int getDivisionId() { return divisionId; }
    public void setDivisionId(int divisionId) { this.divisionId = divisionId; }

    public Contact getContact() { return contact; }
    public void setContact(Contact contact) { this.contact = contact == null ? new Contact() : contact; }
}
