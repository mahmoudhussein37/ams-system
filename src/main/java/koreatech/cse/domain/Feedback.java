package koreatech.cse.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;

public class Feedback implements Serializable {
    private static final long serialVersionUID = 149657035384L;

    private int id;
    private String email;
    private String number;
    private String name;
    private String contents;
    private Date datetime;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
