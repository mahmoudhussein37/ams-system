package koreatech.cse.domain.dto;

import koreatech.cse.domain.Contact;
import koreatech.cse.domain.User;

public class AdminStudentProfileUpdateDTO {
    private int id;
    private int divisionId;
    private int advisorId;
    private String status;
    private int schoolYear;
    private Contact contact = new Contact();

    public static AdminStudentProfileUpdateDTO fromUser(User user) {
        AdminStudentProfileUpdateDTO dto = new AdminStudentProfileUpdateDTO();
        if (user == null) {
            return dto;
        }

        dto.setId(user.getId());
        dto.setDivisionId(user.getDivisionId());
        dto.setAdvisorId(user.getAdvisorId());
        dto.setStatus(user.getStatus());
        dto.setSchoolYear(user.getSchoolYear());

        Contact contact = new Contact();
        if (user.getContact() != null) {
            contact.setFirstName(user.getContact().getFirstName());
            contact.setLastName(user.getContact().getLastName());
            contact.setGradYear(user.getContact().getGradYear());
            contact.setGradSemester(user.getContact().getGradSemester());
            contact.setGradDate(user.getContact().getGradDate());
            contact.setGradDegree(user.getContact().getGradDegree());
            contact.setDegreeNumber(user.getContact().getDegreeNumber());
            contact.setCertNumber(user.getContact().getCertNumber());
        }
        dto.setContact(contact);
        return dto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public int getAdvisorId() {
        return advisorId;
    }

    public void setAdvisorId(int advisorId) {
        this.advisorId = advisorId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(int schoolYear) {
        this.schoolYear = schoolYear;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact == null ? new Contact() : contact;
    }
}
