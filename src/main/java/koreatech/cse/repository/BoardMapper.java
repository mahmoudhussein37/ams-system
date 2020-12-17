package koreatech.cse.repository;

import koreatech.cse.domain.univ.Article;
import koreatech.cse.repository.provider.ArticleSqlProvider;
import koreatech.cse.util.mybatis.Pageable;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardMapper {

    @Update("UPDATE ${boardTableName} SET SUBJECT = #{article.subject}, CONTENT = #{article.content} WHERE id = #{article.id}")
    void update(@Param("boardTableName") String boardTableName, @Param("article") Article article);

    @Insert("INSERT INTO ${boardTableName} (USER_ID, SUBJECT, CONTENT) VALUES (#{article.userId}, #{article.subject}, #{article.content})")
    @SelectKey(statement = "select @@identity", keyProperty = "article.id", before = false, resultType = int.class)
    void insert(@Param("boardTableName") String boardTableName, @Param("article") Article article);

    @Select("SELECT * FROM ${boardTableName} WHERE id = #{id}")
    Article findOne(@Param("boardTableName") String boardTableName, @Param("id") int id);

    @Update("UPDATE ${boardTableName} SET hit = hit + 1 WHERE id = #{id}")
    void updateHit(@Param("boardTableName") String boardTableName, @Param("id") int id);

    @Delete("DELETE FROM ${boardTableName} WHERE id = #{id}")
    void delete(@Param("boardTableName") String boardTableName, @Param("id") int id);

    @Select("SELECT * FROM ${boardTableName} ORDER BY registered_date desc LIMIT #{size}")
    List<Article> findArticleList(@Param("boardTableName") String boardTableName, @Param("size") int size);

    @Select("SELECT * FROM ${boardTableName} ORDER BY registered_date desc")
    List<Article> findArticleAll(@Param("boardTableName") String boardTableName);

    @SelectProvider(type = ArticleSqlProvider.class, method = "findAllSql")
    List<Article> findArticleListAjax(Pageable pageable);

    @SelectProvider(type = ArticleSqlProvider.class, method = "countAllSql")
    int countArticleListAjax(Pageable pageable);

    /*@ResultMap("findOne-int")
    @SelectProvider(type = ConferenceManuscriptSqlProvider.class, method = "findCowrittenManuscriptsSql")
    List<Manuscript> findCowrittenManuscripts(Pageable pageable);*/

    @Select("SELECT COUNT(article_id) FROM ${boardTableName}")
    int countAll(@Param("boardTableName") String boardTableName);

}
