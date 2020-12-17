package koreatech.cse.util.mybatis;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import java.util.Map;
import java.util.TreeMap;

public class Pageable extends PageRequest {

    private static final long serialVersionUID = -4940142830513904845L;
    private String tableName;


    protected Map<String, Term> conditionals = new TreeMap<String, Term>();

    protected Sort sort;

    public Pageable() {
        super(0, 1);
    }

    public Pageable(int page, int size) {
        super(page, size);
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void addConditionals(String property, Object value, Operator operator) {
        conditionals.put(property, Term.newInstance(property, value, operator));
    }

    public void addConditionals(String prefix, String property, Object value, Operator operator) {
        conditionals.put(property, Term.newInstance(prefix, property, value, operator));
    }

    public void addSort(Direction direction, String property) {
        if (getSort() == null) {
            this.sort = new Sort(direction, property);
        } else {
            this.sort = getSort().and(new Sort(direction, property));
        }
    }

    public Map<String, Term> getConditionals() {
        return conditionals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Pageable pageable = (Pageable) o;

        if (conditionals != null ? !conditionals.equals(pageable.conditionals) : pageable.conditionals != null)
            return false;
        return !(sort != null ? !sort.equals(pageable.sort) : pageable.sort != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (conditionals != null ? conditionals.hashCode() : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        return result;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
