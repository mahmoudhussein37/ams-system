package koreatech.cse.domain.dto;

public class SignupRequest {
    private String number;
    private String username;
    private String password;
    private ContactInfo contact = new ContactInfo();

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public ContactInfo getContact() { return contact; }
    public void setContact(ContactInfo contact) { this.contact = contact == null ? new ContactInfo() : contact; }

    public static class ContactInfo {
        private String firstName;
        private String lastName;

        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }

        public String getLastName() { return lastName; }
        public void setLastName(String lastName) { this.lastName = lastName; }
    }
}
