package koreatech.cse.repository;

import koreatech.cse.domain.Authority;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorityMapper {
    @Insert("INSERT INTO authority (USER_ID, ROLE) VALUES (#{userId}, #{role})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
    void insert(Authority authority);

    @Update("UPDATE authority SET USER_ID = #{userId}, ROLE = #{role} WHERE ID = #{id}")
    void update(Authority authority);

    @Select("SELECT * FROM authority WHERE USER_ID = #{userId}")
    List<Authority> findByUserId(@Param("userId") int userId);

    @Select("SELECT * FROM authority WHERE USER_ID = #{userId} AND ROLE = #{role} LIMIT 1")
    Authority findByUserIdAndRole(@Param("userId") int userId, @Param("role") String role);

    @Delete("DELETE FROM authority WHERE ID = #{id}")
    void delete(Authority authority);

}
