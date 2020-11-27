package koreatech.cse.domain.role.professor;

import koreatech.cse.domain.User;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

public class Counseling implements Serializable {
    private static final long serialVersionUID = 12342342349L;

    private int id;
    private String number;
    private int studentUserId;
    private int profUserId;
    private int year;
    private String place;
    private String date;
    private String contents;
    private String suggestions;

    private User studentUser;
    private User profUser;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getStudentUserId() {
        return studentUserId;
    }

    public void setStudentUserId(int studentUserId) {
        this.studentUserId = studentUserId;
    }

    public int getProfUserId() {
        return profUserId;
    }

    public void setProfUserId(int profUserId) {
        this.profUserId = profUserId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public User getStudentUser() {
        return studentUser;
    }

    public void setStudentUser(User studentUser) {
        this.studentUser = studentUser;
    }

    public User getProfUser() {
        return profUser;
    }

    public void setProfUser(User profUser) {
        this.profUser = profUser;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(String suggestions) {
        this.suggestions = suggestions;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
