package koreatech.cse.datatables;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * http://datatables.net/manual/server-side
 */
public class DataTablesRequest {

    private int draw;
    private int start;
    private int length;
    private DataTablesSearch search;
    private DataTablesOrder[] order;
    private DataTablesColumn[] columns;

    public int getDraw() {
        return draw;
    }

    public int getStart() {
        return start;
    }

    public int getLength() {
        return length;
    }

    public DataTablesSearch getSearch() {
        return search;
    }

    public DataTablesOrder[] getOrder() {
        return order == null ? null : order.clone();
    }

    public DataTablesColumn[] getColumns() {
        return columns == null ? null : columns.clone();
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
