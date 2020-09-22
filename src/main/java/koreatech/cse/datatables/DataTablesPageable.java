package koreatech.cse.datatables;

import koreatech.cse.util.mybatis.Operator;
import koreatech.cse.util.mybatis.Pageable;
import koreatech.cse.util.mybatis.Term;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Sort.Direction;

public class DataTablesPageable extends Pageable {

    private static final long serialVersionUID = 7615640779308451772L;
    private String globalSearch;
    private DataTablesRequest dataTablesRequest;

    public DataTablesPageable(DataTablesRequest dataTablesRequest) {
        super(dataTablesRequest.getStart() / dataTablesRequest.getLength(), dataTablesRequest.getLength());
        this.dataTablesRequest = dataTablesRequest;
        setGlobalSearch(dataTablesRequest);
        setSearchColumns(dataTablesRequest);
        setSort(dataTablesRequest);
    }

    public DataTablesRequest getDataTablesRequest() {
        return dataTablesRequest;
    }

    public String getGlobalSearch() {
        return globalSearch;
    }

    public void setGlobalSearch(String globalSearch) {
        this.globalSearch = globalSearch;
    }

    private void setGlobalSearch(final DataTablesRequest datatablesRequest) {
        String globalSearchValue = datatablesRequest.getSearch().getValue();
        setGlobalSearch(globalSearchValue);
    }

    public void addConditionals(String prefix, String property, Object value, Operator operator) {
        conditionals.put(property, Term.newInstance(prefix, property, value, operator));
    }

    private void setSearchColumns(final DataTablesRequest dataTablesRequest) {
        for (DataTablesColumn column : dataTablesRequest.getColumns()) {
            if (!column.isSearchable()) {
                continue;
            }
            String searchValue = column.getSearch().getValue();
            if (StringUtils.isBlank(searchValue)) {
                continue;
            }


            addConditionals(column.getData(), searchValue, isDatePair(column) ? Operator.BETWEEN : Operator.LIKE);
        }
    }

    private void setSort(final DataTablesRequest request) {
        for (DataTablesOrder order : request.getOrder()) {
            DataTablesColumn column = request.getColumns()[order.getColumn()];
            if (!column.isOrderable())
                continue;


            addSort(Direction.fromString(order.getDir()), column.getData());
        }
    }

    private boolean isDatePair(DataTablesColumn column) {
        /*ManualSearchColumn[] manualSearchColumns = {ManualSearchColumn.submitDate, ManualSearchColumn.confirmDate, ManualSearchColumn.acceptDate, ManualSearchColumn.publishDate};
        for(ManualSearchColumn manualSearchColumn: manualSearchColumns)
            if(column.getName().equals(manualSearchColumn.name()))
                return true;*/
        return false;
    }

}
