package koreatech.cse.util.mybatis;

import koreatech.cse.datatables.DataTablesPageable;
import koreatech.cse.datatables.DataTablesRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort.Order;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public final class PaginationHelper {

    private PaginationHelper() {}

    public static DataTablesPageable pageable(DataTablesRequest datatablesRequest) {
        return new DataTablesPageable(datatablesRequest);
    }

    public static String camelCaseToUnderScore(String camelCase) {
        if (StringUtils.isBlank(camelCase)) {
            return camelCase;
        }
        return camelCase.replaceAll("([a-z])([A-Z])", "$1_$2");
    }

    public static boolean isCollectionOperator(Term term) {
        Operator operator = term.getOperator();
        return operator == Operator.IN || operator == Operator.NOT_IN || operator == Operator.LIKE_OR || operator == Operator.BETWEEN_OR;
    }

    public static String phrase(Order order) {
        return camelCaseToUnderScore(order.getProperty()).toUpperCase() + ' ' + order.getDirection();
    }

    public static String phrase(Term term) {
        if(term == null) return null;
        String columnNameAndOperator = generateColumnNameOperator(term) + ' ';
        String mybatisVariable = "#{conditionals." + term.getProperty() + ".value}";
        switch (term.getOperator()) {
            case BETWEEN:
            case BETWEEN_OR:
                String[] dates = ((String)term.getValue()).split("_");
                return "(" + columnNameAndOperator + " '" + dates[0] + "' AND '" + dates[1] + "')";
            case LIKE:
                return columnNameAndOperator + "CONCAT('%'," + mybatisVariable + ",'%')";
            case LIKE_OR:

            case NOT_IN:
            case IN:
            default:
                return columnNameAndOperator + mybatisVariable;
        }
    }

    public static String phrase(Pageable pageable, Operator operator) {
        if(pageable == null) return null;
        List<Term> terms = new ArrayList<>();
        for (Term term : pageable.getConditionals().values()) {
            if (term.getOperator() == operator) {
                terms.add(term);
            }
        }

        if(CollectionUtils.isEmpty(terms)) return null;
        String clause = "";

        switch (operator) {
            case LIKE_OR:
                for (Term term : terms) {
                    clause += generateColumnNameOperator(term) + " CONCAT('%','" + term.getValue() + "','%')";
                    if(terms.indexOf(term) < terms.size() - 1)
                        clause += " OR ";
                }
                return generateBracket(clause);
            case BETWEEN_OR:
                for (Term term : terms) {
                    clause += phrase(term);
                    if(terms.indexOf(term) < terms.size() - 1)
                        clause += " OR ";
                }
                return generateBracket(clause);
            case NOT_IN:
                return generateBracket(generateInClause(terms));
            case IN:
                return generateBracket(generateInClause(terms));
            default:
                return null;
        }
    }

    private static String generateInClause(List<Term> terms) {
        String clause = "";
        for (Term term : terms) {
            @SuppressWarnings("unchecked")
            List<Object> collections = (List<Object>) term.getValue();
            if(CollectionUtils.isEmpty(collections))
                continue;

            clause += generateColumnNameOperator(term) + " (";
            for(Object value : collections) {
                if(value instanceof Integer)
                    clause += value;
                else
                    clause += "'" + value + "'";

                if(collections.indexOf(value) < collections.size() - 1)
                    clause += " , ";
            }
            clause += ")";

            if(terms.indexOf(term) < terms.size() - 1)
                clause += " AND ";
        }
        return clause;
    }

    private static String generateColumnNameOperator(Term term) {
        if(term == null) return "";
        String prefix  = StringUtils.isBlank(term.getPrefix()) ? "" : term.getPrefix() + ".";
        return camelCaseToUnderScore(prefix + term.getProperty()).toUpperCase()  + ' ' + term.getOperator();
    }

    private static String generateBracket(String clause) {
        return StringUtils.isNotBlank(clause) ? "(" + clause + ")" : null;
    }
}
