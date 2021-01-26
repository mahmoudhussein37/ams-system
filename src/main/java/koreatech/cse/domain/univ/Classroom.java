package koreatech.cse.domain.univ;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class Classroom implements Serializable {
    private static final long serialVersionUID = 352223849L;

    private int id;
    private String code;
    private String name;
    private boolean enabled;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
