package koreatech.cse.repository;

import koreatech.cse.datatables.DataTablesPageable;
import koreatech.cse.domain.univ.Article;
import koreatech.cse.util.mybatis.Operator;
import koreatech.cse.util.mybatis.Pageable;
import koreatech.cse.util.mybatis.Term;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Sort.Order;

import java.util.List;
import java.util.Map;

import static koreatech.cse.util.mybatis.PaginationHelper.isCollectionOperator;
import static koreatech.cse.util.mybatis.PaginationHelper.phrase;

@Repository
public interface BoardMapper {

    @UpdateProvider(type = BoardSqlProvider.class, method = "update")
    void update(@Param("boardTableName") String boardTableName, @Param("article") Article article);

    @InsertProvider(type = BoardSqlProvider.class, method = "insert")
    @SelectKey(statement = "select @@identity", keyProperty = "article.id", before = false, resultType = int.class)
    void insert(@Param("boardTableName") String boardTableName, @Param("article") Article article);

    @SelectProvider(type = BoardSqlProvider.class, method = "findOne")
    Article findOne(@Param("boardTableName") String boardTableName, @Param("id") int id);

    @UpdateProvider(type = BoardSqlProvider.class, method = "updateHit")
    void updateHit(@Param("boardTableName") String boardTableName, @Param("id") int id);

    @DeleteProvider(type = BoardSqlProvider.class, method = "delete")
    void delete(@Param("boardTableName") String boardTableName, @Param("id") int id);

    @SelectProvider(type = BoardSqlProvider.class, method = "findArticleList")
    List<Article> findArticleList(@Param("boardTableName") String boardTableName, @Param("size") int size);

    @SelectProvider(type = BoardSqlProvider.class, method = "findArticleAll")
    List<Article> findArticleAll(@Param("boardTableName") String boardTableName);

    @SelectProvider(type = BoardSqlProvider.class, method = "findArticleListAjax")
    List<Article> findArticleListAjax(Pageable pageable);

    @SelectProvider(type = BoardSqlProvider.class, method = "countArticleListAjax")
    int countArticleListAjax(Pageable pageable);

    /*
     * @ResultMap("findOne-int")
     * 
     * @SelectProvider(type = ConferenceManuscriptSqlProvider.class, method =
     * "findCowrittenManuscriptsSql")
     * List<Manuscript> findCowrittenManuscripts(Pageable pageable);
     */

    @SelectProvider(type = BoardSqlProvider.class, method = "countAll")
    int countAll(@Param("boardTableName") String boardTableName);

    class BoardSqlProvider {
        public String update(Map<String, Object> parameters) {
            return "UPDATE " + resolveBoardTableName(parameters)
                    + " SET SUBJECT = #{article.subject}, CONTENT = #{article.content} WHERE id = #{article.id}";
        }

        public String insert(Map<String, Object> parameters) {
            return "INSERT INTO " + resolveBoardTableName(parameters)
                    + " (USER_ID, SUBJECT, CONTENT) VALUES (#{article.userId}, #{article.subject}, #{article.content})";
        }

        public String findOne(Map<String, Object> parameters) {
            return "SELECT * FROM " + resolveBoardTableName(parameters) + " WHERE id = #{id}";
        }

        public String updateHit(Map<String, Object> parameters) {
            return "UPDATE " + resolveBoardTableName(parameters) + " SET hit = hit + 1 WHERE id = #{id}";
        }

        public String delete(Map<String, Object> parameters) {
            return "DELETE FROM " + resolveBoardTableName(parameters) + " WHERE id = #{id}";
        }

        public String findArticleList(Map<String, Object> parameters) {
            return "SELECT * FROM " + resolveBoardTableName(parameters)
                    + " ORDER BY registered_date desc LIMIT #{size}";
        }

        public String findArticleAll(Map<String, Object> parameters) {
            return "SELECT * FROM " + resolveBoardTableName(parameters) + " ORDER BY registered_date desc";
        }

        public String findArticleListAjax(Pageable pageable) {
            DataTablesPageable dataTablesPageable = (DataTablesPageable) pageable;
            return buildArticleListSql(dataTablesPageable) + " LIMIT " + dataTablesPageable.getOffset() + ", "
                    + dataTablesPageable.getPageSize();
        }

        public String countArticleListAjax(Pageable pageable) {
            DataTablesPageable dataTablesPageable = (DataTablesPageable) pageable;
            return buildArticleCountSql(dataTablesPageable);
        }

        public String countAll(Map<String, Object> parameters) {
            return "SELECT COUNT(article_id) FROM " + resolveBoardTableName(parameters);
        }

        private String buildArticleListSql(final DataTablesPageable pageable) {
            final String boardTableName = resolvePageableBoardTableName(pageable);
            return new SQL() {
                {
                    SELECT("*").FROM(boardTableName);
                    if (pageable.getConditionals() != null) {
                        for (Term term : pageable.getConditionals().values()) {
                            if (!isCollectionOperator(term)) {
                                WHERE(phrase(term));
                            }
                        }

                        WHERE(phrase(pageable, Operator.IN));
                    }

                    if (StringUtils.isNotBlank(pageable.getGlobalSearch())) {
                        WHERE(generateGlobalSearchPhrase(pageable.getGlobalSearch()));
                    }

                    if (pageable.getSort() != null) {
                        for (Order order : pageable.getSort()) {
                            ORDER_BY(phrase(order));
                        }
                    }
                }
            }.toString();
        }

        private String buildArticleCountSql(final DataTablesPageable pageable) {
            final String boardTableName = resolvePageableBoardTableName(pageable);
            return new SQL() {
                {
                    SELECT("COUNT(*)").FROM(boardTableName);

                    if (pageable.getConditionals() != null) {
                        for (Term term : pageable.getConditionals().values()) {
                            if (!isCollectionOperator(term)) {
                                WHERE(phrase(term));
                            }
                        }

                        WHERE(phrase(pageable, Operator.IN));
                    }

                    if (StringUtils.isNotBlank(pageable.getGlobalSearch())) {
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

        private String resolveBoardTableName(Map<String, Object> parameters) {
            return resolveBoardTableName((String) parameters.get("boardTableName"));
        }

        private String resolvePageableBoardTableName(Pageable pageable) {
            if (pageable == null) {
                throw new IllegalArgumentException("Pageable is required");
            }
            return resolveBoardTableName(pageable.getTableName());
        }

        private String resolveBoardTableName(String boardTableName) {
            if ("board_notice".equals(boardTableName)) {
                return "board_notice";
            }
            if ("board_de".equals(boardTableName)) {
                return "board_de";
            }
            if ("board_hire".equals(boardTableName)) {
                return "board_hire";
            }
            if ("board_schedule".equals(boardTableName)) {
                return "board_schedule";
            }
            throw new IllegalArgumentException("Unsupported board table");
        }
    }

}
