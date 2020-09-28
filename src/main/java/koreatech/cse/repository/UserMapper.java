package koreatech.cse.repository;


import koreatech.cse.domain.Searchable;
import koreatech.cse.domain.User;
import koreatech.cse.repository.provider.UserSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    @Insert("INSERT INTO USER (USERNAME, PASSWORD, number, division_id, major_id, register_date) VALUES (#{username}, #{password}, #{number}, #{divisionId}, #{majorId}, CURRENT_TIMESTAMP)")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
    void insert(User user);

    @Update("UPDATE USER SET USERNAME = #{username}, PASSWORD = #{password}, division_id = #{divisionId}, major_id = #{majorId} WHERE ID = #{id}")
    void update(User user);


    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "division_id", property = "divisionId"),
            @Result(column = "major_id", property = "majorId"),
            @Result(column = "id", property = "contact", one = @One(select = "koreatech.cse.repository.ContactMapper.findByUserId")),
            @Result(column = "id", property = "authorities", many = @Many(select = "koreatech.cse.repository.AuthorityMapper.findByUserId")),
            @Result(column = "division_id", property = "division", one = @One(select = "koreatech.cse.repository.DivisionMapper.findOne")),
            @Result(column = "major_id", property = "major", one = @One(select = "koreatech.cse.repository.MajorMapper.findOne")),
    })
    @Select("SELECT * FROM USER WHERE ID = #{id}")
    User findOne(@Param("id") int id);

    @ResultMap("findOne-int")
    @Select("SELECT * FROM USER")
    List<User> findAll();

    @ResultMap("findOne-int")
    @Select("SELECT * FROM USER u join authority a on u.id = a.user_id where a.role = 'ROLE_STUDENT'")
    List<User> findAllStudents();

    @ResultMap("findOne-int")
    @Select("select * from user where username = #{username}")
    User findByUsername(@Param("username") String username);

    @Delete("DELETE FROM USER WHERE ID = #{id}")
    void delete(@Param("id") int id);

    @SelectProvider(type = UserSqlProvider.class, method = "findAllByProvider")
    List<User> findByProvider(Searchable searchable);

    //@formatter off
    @Select("<script>"
            + "SELECT * FROM USER"
            + "<if test='name != null'> WHERE NAME = #{name}</if>"
            + "<if test='name != null and email != null'> OR EMAIL = #{email}</if>"
            + "<if test='orderParam != null'>ORDER BY ${orderParam} DESC</if>"
            + "</script>")
    //@formatter on
    List<User> findByScript(Searchable searchable);

    //@formatter off
    @Select("<script>"
            + "SELECT * FROM USER"
            + "<choose>"
            + "<when test='name != null and email != null'> WHERE NAME = #{name} OR EMAIL = #{email}</when>"
            + "<when test='name != null'> WHERE NAME = #{name}</when>"
            + "<otherwise> </otherwise>"
			+ "</choose>"
            + "<if test='orderParam != null'>ORDER BY ${orderParam} DESC</if>"
            + "</script>")
    //@formatter on
    List<User> findByScript2(Searchable searchable);

    //@formatter off
    @Select("<script>"
            + "SELECT * FROM USER"
            + "<if test='stringList != null and !stringList.empty'> WHERE NAME IN <foreach item='item' collection='stringList' open='(' separator=',' close=')'>#{item}</foreach></if>"
            + "</script>")
    //@formatter on
    List<User> findByList(@Param("stringList") List<String> stringList);
}
