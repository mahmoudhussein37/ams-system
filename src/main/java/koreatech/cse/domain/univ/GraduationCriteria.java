package koreatech.cse.domain.univ;

import java.io.Serializable;

public class GraduationCriteria implements Serializable {
    private static final long serialVersionUID = 35849L;

    private int id;
    private int divisionId;

    private int year;
    private int msc;
    private int liberal;
    private int major;

    private Division division;
    private boolean enabled;


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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMsc() {
        return msc;
    }

    public void setMsc(int msc) {
        this.msc = msc;
    }

    public int getLiberal() {
        return liberal;
    }

    public void setLiberal(int liberal) {
        this.liberal = liberal;
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
