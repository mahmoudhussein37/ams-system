package koreatech.cse.domain.univ;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;

public class Certificate implements Serializable {
    private static final long serialVersionUID = 35223238429L;
    private int id;
    private int userId;
    private int requestId;
    private Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNo() {
        return String.format("%08d", id);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
