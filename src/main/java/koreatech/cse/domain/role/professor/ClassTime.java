package koreatech.cse.domain.role.professor;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

public class ClassTime implements Serializable {
    private static final long serialVersionUID = 1234234749L;

    private int id;
    private int profCourseId;

    private int w; //week
    private int s; //start time
    private int e; //end time

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProfCourseId() {
        return profCourseId;
    }

    public void setProfCourseId(int profCourseId) {
        this.profCourseId = profCourseId;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getS() {
        return s;
    }

    public void setS(int s) {
        this.s = s;
    }

    public int getE() {
        return e;
    }

    public void setE(int e) {
        this.e = e;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
