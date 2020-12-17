package koreatech.cse.repository.provider;


import koreatech.cse.datatables.DataTablesPageable;
import koreatech.cse.util.mybatis.Operator;
import koreatech.cse.util.mybatis.Pageable;
import koreatech.cse.util.mybatis.Term;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.data.domain.Sort.Order;

import static koreatech.cse.util.mybatis.PaginationHelper.isCollectionOperator;
import static koreatech.cse.util.mybatis.PaginationHelper.phrase;


public class ArticleSqlProvider {

    public String findAllSql(final DataTablesPageable pageable) {
        return new SQL() {
            {
                SELECT("*").FROM(pageable.getTableName());
                if (pageable.getConditionals() != null) {
                    for (Term term : pageable.getConditionals().values())
                        if (!isCollectionOperator(term))
                            WHERE(phrase(term));

                    WHERE(phrase(pageable, Operator.IN));
                }

                if(StringUtils.isNotBlank(pageable.getGlobalSearch())) {
                    WHERE(generateGlobalSearchPhrase(pageable.getGlobalSearch()));
                }

                if (pageable.getSort() != null) {
                    for (Order order : pageable.getSort()) {
                        ORDER_BY(phrase(order));
                    }
                }
            }
        }.toString() + " LIMIT " + pageable.getOffset() + ", " + pageable.getPageSize();
    }

    public String countAllSql(final DataTablesPageable pageable) {
        return new SQL() {
            {
                SELECT("COUNT(*)").FROM(pageable.getTableName());

                if (pageable.getConditionals() != null) {
                    for (Term term : pageable.getConditionals().values())
                        if (!isCollectionOperator(term))
                            WHERE(phrase(term));

                    WHERE(phrase(pageable, Operator.IN));
                }

                if(StringUtils.isNotBlank(pageable.getGlobalSearch())) {
                    WHERE(generateGlobalSearchPhrase(pageable.getGlobalSearch()));
                }
            }
        }.toString();
    }


    private String generateGlobalSearchPhrase(String searchValue) {
        Pageable additional = new Pageable();
        additional.addConditionals("subject", searchValue, Operator.LIKE_OR);
        additional.addConditionals("content", searchValue, Operator.LIKE_OR);

        return phrase(additional, Operator.LIKE_OR);
    }
}
