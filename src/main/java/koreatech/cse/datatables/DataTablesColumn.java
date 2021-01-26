package koreatech.cse.datatables;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class DataTablesColumn {

    private String data;
    private String name;
    private boolean searchable;
    private boolean orderable;
    private DataTablesSearch search;

    public String getData() {
        return data;
    }

    public String getName() {
        return name;
    }

    public boolean isSearchable() {
        return searchable;
    }

    public boolean isOrderable() {
        return orderable;
    }

    public DataTablesSearch getSearch() {
        return search;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}