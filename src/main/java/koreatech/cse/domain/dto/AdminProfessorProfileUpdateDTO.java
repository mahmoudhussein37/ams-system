package koreatech.cse.domain.dto;

import koreatech.cse.domain.Contact;
import koreatech.cse.domain.User;
import koreatech.cse.domain.constant.AccountState;

public class AdminProfessorProfileUpdateDTO {
    private int id;
    private int divisionId;
    private boolean enabled;
    private String username;
    private Contact contact = new Contact();

    public static AdminProfessorProfileUpdateDTO fromUser(User user) {
        AdminProfessorProfileUpdateDTO dto = new AdminProfessorProfileUpdateDTO();
        if (user == null) {
            return dto;
        }

        dto.setId(user.getId());
        dto.setDivisionId(user.getDivisionId());
        dto.setEnabled(user.getAccountState() == AccountState.ACTIVE);
        dto.setUsername(user.getUsername());

        Contact contact = new Contact();
        if (user.getContact() != null) {
            contact.setFirstName(user.getContact().getFirstName());
            contact.setLastName(user.getContact().getLastName());
        }
        dto.setContact(contact);
        return dto;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact == null ? new Contact() : contact;
    }
}
