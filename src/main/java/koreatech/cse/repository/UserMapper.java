package koreatech.cse.repository;


import koreatech.cse.domain.Searchable;
import koreatech.cse.domain.User;
import koreatech.cse.repository.provider.UserSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    @Insert("INSERT INTO `user`("+
            "`username`,"+
            "`password`,"+
            "`number`,"+
            "`division_id`,"+
            "`major_id`,"+
            "`school_year`,"+
            "`confirm`,"+
            "`register_date`,"+
            "`advisor`,"+
            "`status`,"+
            "`enabled`"+
            ")VALUES("+
            "#{username},"+
            "#{password},"+
            "#{number},"+
            "#{divisionId},"+
            "#{majorId},"+
            "#{schoolYear},"+
            "#{confirm},"+
            "CURRENT_TIMESTAMP,"+
            "#{advisor},"+
            "#{status},"+
            "#{enabled}"+
            ")")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
    void insert(User user);

    @Update("UPDATE `user` SET"+
            "`number` = #{number},"+
            "`division_id` = #{divisionId},"+
            "`major_id` = #{majorId},"+
            "`school_year` = #{schoolYear},"+
            "`advisor` = #{advisor},"+
            "`status` = #{status} "+
            "WHERE `id` = #{id}")
    void update(User user);

    @Update("UPDATE `user` SET"+
            "`username` = #{username},"+
            "`password` = #{password},"+
            "`enabled` = #{enabled},"+
            "`confirm` = #{confirm} "+
            "WHERE `id` = #{id}")
    void updateFromSignup(User user);


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

    @ResultMap("findOne-int")
    @Select("select * from user where number = #{number}")
    User findByNumber(@Param("number") String number);

    @Delete("DELETE FROM USER WHERE ID = #{id}")
    void delete(@Param("id") int id);

    @SelectProvider(type = UserSqlProvider.class, method = "findAllByProvider")
    List<User> findByProvider(Searchable searchable);

    @ResultMap("findOne-int")
    //@formatter off
    @Select("<script>"
            + "SELECT * FROM USER u join contact c on u.id=c.user_id join authority a on u.id = a.user_id where a.role = 'ROLE_STUDENT' "
            + "<if test='name != null'> and (c.last_name LIKE CONCAT('%', #{name}, '%') or c.first_name LIKE CONCAT('%', #{name}, '%'))</if>"
            + "<if test='number != null'> and u.number LIKE CONCAT('%', #{number}, '%')</if>"
            + "<if test='division != 0'> and u.division_id = #{division}</if>"
            + "<if test='major != 0'> and u.major_id = #{major}</if>"
            + "<if test='orderParam != null and orderDir != null'> ORDER BY ${orderParam} ${orderDir}</if>"
            + "</script>")
    //@formatter on
    List<User> findByStudentLookup(Searchable searchable);

    @ResultMap("findOne-int")
    //@formatter off
    @Select("<script>"
            + "SELECT * FROM USER u join contact c on u.id=c.user_id join authority a on u.id = a.user_id where a.role = 'ROLE_PROFESSOR' "
            + "<if test='name != null'> and (c.last_name LIKE CONCAT('%', #{name}, '%') or c.first_name LIKE CONCAT('%', #{name}, '%'))</if>"
            + "<if test='number != null'> and u.number LIKE CONCAT('%', #{number}, '%')</if>"
            + "<if test='division != 0'> and u.division_id = #{division}</if>"
            + "<if test='major != 0'> and u.major_id = #{major}</if>"
            + "<if test='orderParam != null and orderDir != null'> ORDER BY ${orderParam} ${orderDir}</if>"
            + "</script>")
        //@formatter on
    List<User> findByProfLookup(Searchable searchable);

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
