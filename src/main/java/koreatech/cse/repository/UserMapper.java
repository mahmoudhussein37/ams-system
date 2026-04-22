package koreatech.cse.repository;

import koreatech.cse.domain.Searchable;
import koreatech.cse.domain.User;
import koreatech.cse.repository.provider.UserSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface UserMapper {
        @Insert("INSERT INTO `user`(" +
                        "`username`," +
                        "`password`," +
                        "`number`," +
                        "`division_id`," +
                        "`advisor_id`," +
                        "`school_year`," +
                        "`confirm`," +
                        "`register_date`," +
                        "`status`," +
                        "`enabled`" +
                        ")VALUES(" +
                        "#{username}," +
                        "#{password}," +
                        "#{number}," +
                        "#{divisionId}," +
                        "#{advisorId}," +
                        "#{schoolYear}," +
                        "#{confirm}," +
                        "CURRENT_TIMESTAMP," +
                        "#{status}," +
                        "#{enabled}" +
                        ")")
        @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
        void insert(User user);

        @Update("UPDATE `user` SET" +
                        "`number` = #{number}," +
                        "`division_id` = #{divisionId}," +
                        "`advisor_id` = #{advisorId}," +
                        "`school_year` = #{schoolYear}," +
                        "`status` = #{status} " +
                        "WHERE `id` = #{id}")
        void update(User user);

        @Update("UPDATE `user` SET" +
                        "`username` = #{username}," +
                        "`password` = #{password}," +
                        "`school_year` = #{schoolYear}," +
                        "`enabled` = #{enabled}," +
                        "`confirm` = #{confirm} " +
                        "WHERE `id` = #{id}")
        void updateFromSignup(User user);

        @Update("UPDATE `user` SET" +
                        "`enabled` = #{enabled}," +
                        "`confirm` = #{confirm} " +
                        "WHERE `id` = #{id}")
        void updateAccountState(User user);

        @Results({
                        @Result(column = "id", property = "id"),
                        @Result(column = "division_id", property = "divisionId"),
                        @Result(column = "advisor_id", property = "advisorId"),
                        @Result(column = "id", property = "contact", one = @One(select = "koreatech.cse.repository.ContactMapper.findByUserId")),
                        @Result(column = "id", property = "authorities", many = @Many(select = "koreatech.cse.repository.AuthorityMapper.findByUserId")),
                        @Result(column = "division_id", property = "division", one = @One(select = "koreatech.cse.repository.DivisionMapper.findOne")),
                        @Result(column = "advisor_id", property = "advisor", one = @One(select = "koreatech.cse.repository.UserMapper.findOne")),
                        @Result(column = "id", property = "profile", one = @One(select = "koreatech.cse.repository.UploadedFileMapper.findProfileFile"))
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
        @Select("SELECT * FROM USER u join authority a on u.id = a.user_id where a.role = 'ROLE_PROFESSOR'")
        List<User> findAllProfessors();

        @ResultMap("findOne-int")
        @Select("SELECT * FROM USER u join authority a on u.id = a.user_id where a.role = 'ROLE_PROFESSOR' and u.division_id=#{divisionId}")
        List<User> findProfessorsByDivision(@Param("divisionId") int divisionId);

        @ResultMap("findOne-int")
        @Select("SELECT * FROM USER u join authority a on u.id = a.user_id where a.role = 'ROLE_ADMIN'")
        List<User> findAllAdmins();

        @ResultMap("findOne-int")
        @Select("select * from user where username = #{username}")
        User findByUsername(@Param("username") String username);

        @ResultMap("findOne-int")
        @Select("select * from user where number = #{number} limit 1")
        User findByNumber(@Param("number") String number);

        @ResultMap("findOne-int")
        @Select("select * from user u join authority a on u.id = a.user_id where a.role = 'ROLE_STUDENT' and u.number = #{number}")
        User findStudentByNumber(@Param("number") String number);

        @Delete("DELETE FROM USER WHERE ID = #{id}")
        void delete(User user);

        @SelectProvider(type = UserSqlProvider.class, method = "findAllByProvider")
        List<User> findByProvider(Searchable searchable);

        @ResultMap("findOne-int")
        // @formatter off
        @Select("<script>"
                        + "SELECT * FROM USER u join contact c on u.id=c.user_id join authority a on u.id = a.user_id where a.role = 'ROLE_STUDENT' "
                        + "<if test='name != null'> and (c.last_name LIKE CONCAT('%', #{name}, '%') or c.first_name LIKE CONCAT('%', #{name}, '%'))</if>"
                        + "<if test='number != null'> and u.number LIKE CONCAT('%', #{number}, '%')</if>"
                        + "<if test='division != 0'> and u.division_id = #{division}</if>"
                        + "<if test='advisor != 0'> and u.advisor_id = #{advisor}</if>"
                        + "<if test='schoolYear != 0'> and u.school_year = #{schoolYear}</if>"
                        + "<if test='accountStatus != null'> and (CASE WHEN u.confirm = 0 THEN 'pending' WHEN u.enabled = 1 THEN 'active' ELSE 'disabled' END) = #{accountStatus}</if>"
                        + "<if test='orderParam != null and orderDir != null'> ORDER BY ${orderParam} ${orderDir}</if>"
                        + "</script>")
        // @formatter on
        List<User> findStudentBy(Searchable searchable);

        @ResultMap("findOne-int")
        // @formatter off
        @Select("<script>"
                        + "SELECT * FROM USER u join contact c on u.id=c.user_id join authority a on u.id = a.user_id where a.role = 'ROLE_STUDENT' "
                        + "<if test='name != null'> and (c.last_name LIKE CONCAT('%', #{name}, '%') or c.first_name LIKE CONCAT('%', #{name}, '%'))</if>"
                        + "<if test='number != null'> and u.number LIKE CONCAT('%', #{number}, '%')</if>"
                        + "<if test='division != 0'> and u.division_id = #{division}</if>"
                        + "<if test='userIds != null and !userIds.empty'> AND u.id not in <foreach item='item' collection='userIds' open='(' separator=',' close=')'>#{item}</foreach></if>"
                        + "</script>")
        // @formatter on
        List<User> findStudentsByAdvisorSchoolYearDivisionExceptRegistered(@Param("userIds") List<Integer> userIds,
                        @Param("number") String number, @Param("name") String name, @Param("division") int division);

        @ResultMap("findOne-int")
        // @formatter off
        @Select("<script>"
                        + "SELECT * FROM USER u join contact c on u.id=c.user_id join authority a on u.id = a.user_id where a.role = 'ROLE_PROFESSOR' "
                        + "<if test='name != null'> and (c.last_name LIKE CONCAT('%', #{name}, '%') or c.first_name LIKE CONCAT('%', #{name}, '%'))</if>"
                        + "<if test='number != null'> and u.number LIKE CONCAT('%', #{number}, '%')</if>"
                        + "<if test='division != 0'> and u.division_id = #{division}</if>"
                        + "<if test='orderParam != null and orderDir != null'> ORDER BY ${orderParam} ${orderDir}</if>"
                        + "</script>")
        // @formatter on
        List<User> findProfessorBy(Searchable searchable);

        @ResultMap("findOne-int")
    //@formatter:off
    @Select("<script>"
            + "SELECT * FROM USER WHERE 1=1"
            + "<if test='userIds != null and !userIds.empty'> AND id IN <foreach item='item' collection='userIds' open='(' separator=',' close=')'>#{item}</foreach></if>"
            + "</script>")
        //@formatter:on
        List<User> findByUserIds(@Param("userIds") List<Integer> userIds);

        // @formatter off
        @Select("<script>"
                        + "SELECT * FROM USER"
                        + "<if test='name != null'> WHERE NAME = #{name}</if>"
                        + "<if test='name != null and email != null'> OR EMAIL = #{email}</if>"
                        + "<if test='orderParam != null'>ORDER BY ${orderParam} DESC</if>"
                        + "</script>")
        // @formatter on
        List<User> findByScript(Searchable searchable);

        // @formatter off
        @Select("<script>"
                        + "SELECT * FROM USER"
                        + "<choose>"
                        + "<when test='name != null and email != null'> WHERE NAME = #{name} OR EMAIL = #{email}</when>"
                        + "<when test='name != null'> WHERE NAME = #{name}</when>"
                        + "<otherwise> </otherwise>"
                        + "</choose>"
                        + "<if test='orderParam != null'>ORDER BY ${orderParam} DESC</if>"
                        + "</script>")
        // @formatter on
        List<User> findByScript2(Searchable searchable);

        // @formatter off
        @Select("<script>"
                        + "SELECT * FROM USER"
                        + "<if test='stringList != null and !stringList.empty'> WHERE NAME IN <foreach item='item' collection='stringList' open='(' separator=',' close=')'>#{item}</foreach></if>"
                        + "</script>")
        // @formatter on
        List<User> findByList(@Param("stringList") List<String> stringList);

        /**
         * Find students eligible for course registration.
         * Filters by: ROLE_STUDENT, division_id, school_year
         * Excludes: students already registered for the given profCourseId
         */
        @ResultMap("findOne-int")
        @Select("<script>"
                        + "SELECT DISTINCT u.* FROM USER u "
                        + "JOIN contact c ON u.id = c.user_id "
                        + "JOIN authority a ON u.id = a.user_id "
                        + "WHERE a.role = 'ROLE_STUDENT' "
                        + "AND u.division_id = #{divisionId} "
                        + "AND u.school_year = #{schoolYear} "
                        + "AND u.id NOT IN ("
                        + "  SELECT sc.user_id FROM student_course sc WHERE sc.prof_course_id = #{profCourseId}"
                        + ") "
                        + "ORDER BY u.number ASC"
                        + "</script>")
        List<User> findEligibleStudentsForCourseRegistration(
                        @Param("divisionId") int divisionId,
                        @Param("schoolYear") int schoolYear,
                        @Param("profCourseId") int profCourseId);
}
