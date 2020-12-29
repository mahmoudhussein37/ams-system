package koreatech.cse.domain.univ;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

public class Semester implements Serializable {
    private static final long serialVersionUID = 353849L;

    private int id;
    private int year;
    private int semester;
    private boolean current;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
