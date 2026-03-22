package koreatech.cse.domain;

import koreatech.cse.domain.constant.Role;
import koreatech.cse.domain.role.student.StudentCourse;
import koreatech.cse.domain.univ.Course;
import koreatech.cse.domain.univ.Division;
import koreatech.cse.domain.univ.GraduationCriteria;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
public class User implements UserDetails {
    private int id;
    private String name;
    private String username;
    private String password;
    private boolean confirm;
    private boolean enabled;
    private Contact contact;

    private int divisionId;

    private Division division;

    private String number; //student/professor number

    private User advisor; //지도교수
    private int advisorId; //지도교수
    private String status;
    private int schoolYear;


    private GraduationCriteria graduationCriteria;
    private List<StudentCourse> studentCourses;

    private UploadedFile profile;



    private List<Authority> authorities;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public User getAdvisor() {
        return advisor;
    }

    public void setAdvisor(User advisor) {
        this.advisor = advisor;
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

    public int getAdvisorId() {
        return advisorId;
    }

    public void setAdvisorId(int advisorId) {
        this.advisorId = advisorId;
    }

    public GraduationCriteria getGraduationCriteria() {
        return graduationCriteria;
    }

    public int getCriteriaCount(String category) {
        if(this.studentCourses == null) return 0;
        int count = 0;
        for(StudentCourse studentCourse: studentCourses) {
            Course course = studentCourse.getCourse();
            if(course.getSubjCategory() == null)
                continue;

            if(course.getSubjCategory().equals(category))
                count += studentCourse.getCourse().getCredit();

        }
        return count;
    }

    public void setGraduationCriteria(GraduationCriteria graduationCriteria) {
        this.graduationCriteria = graduationCriteria;
    }

    public List<StudentCourse> getStudentCourses() {
        return studentCourses;
    }

    public void setStudentCourses(List<StudentCourse> studentCourses) {
        this.studentCourses = studentCourses;
    }

    public static User current() {
        try {
            return (User) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean hasRole(Role role) {
        for(Authority authority: authorities)
            if(authority.getRole() == role) return true;
        return false;

    }

    public String getCurrentRole() {
        if(hasRole(Role.admin))
            return Role.admin.name();
        if(hasRole(Role.professor))
            return Role.professor.name();


        return Role.student.name();
    }

    public UploadedFile getProfile() {
        return profile;
    }

    public void setProfile(UploadedFile profile) {
        this.profile = profile;
    }

    public String getFullName() {
        return this.contact.getFullName();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
