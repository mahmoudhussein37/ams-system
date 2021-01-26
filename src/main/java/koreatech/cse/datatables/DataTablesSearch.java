package koreatech.cse.datatables;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class DataTablesSearch {

    private String value;
    private boolean regex;

    public String getValue() {
        return value;
    }

    public boolean getRegex() {
        return regex;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}