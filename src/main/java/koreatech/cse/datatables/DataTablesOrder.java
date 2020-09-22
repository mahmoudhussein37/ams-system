package koreatech.cse.datatables;

import org.apache.commons.lang.builder.ToStringBuilder;

public class DataTablesOrder {

    private int column;
    private String dir;

    public int getColumn() {
        return column;
    }

    public String getDir() {
        return dir;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}