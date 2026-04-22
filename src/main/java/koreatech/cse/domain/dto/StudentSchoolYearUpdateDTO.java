package koreatech.cse.domain.dto;

import koreatech.cse.domain.User;

public class StudentSchoolYearUpdateDTO {
    private int id;
    private int schoolYear;

    public static StudentSchoolYearUpdateDTO fromUser(User user) {
        StudentSchoolYearUpdateDTO dto = new StudentSchoolYearUpdateDTO();
        if (user == null) {
            return dto;
        }

        dto.setId(user.getId());
        dto.setSchoolYear(user.getSchoolYear());
        return dto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(int schoolYear) {
        this.schoolYear = schoolYear;
    }
}
