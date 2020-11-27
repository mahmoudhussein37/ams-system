package koreatech.cse.repository;

import koreatech.cse.domain.univ.Article;
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
    void deleteById(@Param("boardTableName") String boardTableName, @Param("id") int id);

    @Select("SELECT id, USER_ID, SUBJECT, hit, registered_date, FROM ${boardTableName} ORDER BY registered_date desc LIMIT #{offset}, #{size}")
    List<Article> findArticleList(@Param("boardTableName") String boardTableName, @Param("offset") int offset, @Param("size") int size);

    @Select("SELECT id, SUBJECT, hit, registered_date, MOD_DATETIME FROM ${boardTableName} WHERE ${searchType} LIKE CONCAT('%',#{searchWord},'%') ORDER BY registered_date desc LIMIT #{offset}, #{size}")
    List<Article> findArticleListByLike(@Param("boardTableName") String boardTableName, @Param("searchType") String searchType, @Param("searchWord") String searchWord,
                                        @Param("offset") int offset, @Param("size") int size);

    @Select("SELECT COUNT(article_id) FROM ${boardTableName}")
    int countAll(@Param("boardTableName") String boardTableName);

    @Select("SELECT COUNT(article_id) FROM ${boardTableName} WHERE ${searchType} LIKE CONCAT('%',#{searchWord},'%')")
    int countAllByLike(@Param("boardTableName") String boardTableName, @Param("searchType") String searchType, @Param("searchWord") String searchWord);

}
