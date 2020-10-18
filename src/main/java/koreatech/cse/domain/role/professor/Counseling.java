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
    private String date;

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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
