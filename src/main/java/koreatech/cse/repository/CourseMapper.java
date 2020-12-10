package koreatech.cse.repository;


import koreatech.cse.domain.Searchable;
import koreatech.cse.domain.univ.Course;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CourseMapper {

    @Insert("INSERT INTO course (code, title, credit, division_id, school_year, subj_category, learning_objective, overview, " +
            "`achieve1`,"+
            "`achieve2`,"+
            "`achieve3`,"+
            "`achieve4`,"+
            "`achieve5`,"+
            "`achieve6`,"+
            "`achieve7`,"+
            "`achieve8`,"+
            "`achieve9`,"+
            "`achieve10`,"+
            "lec, tut, lab, ws) VALUES " +
            "(#{code}, #{title}, #{credit}, #{divisionId}, #{schoolYear}, #{subjCategory}, #{learningObjective}, #{overview}, " +
            "#{achieve1},"+
            "#{achieve2},"+
            "#{achieve3},"+
            "#{achieve4},"+
            "#{achieve5},"+
            "#{achieve6},"+
            "#{achieve7},"+
            "#{achieve8},"+
            "#{achieve9},"+
            "#{achieve10},"+
            "#{lec}, #{tut}, #{lab}, #{ws})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
    void insert(Course course);

    @ResultMap("findOne-int")
    @Select("SELECT * FROM course")
    List<Course> findAll();

    @ResultMap("findOne-int")
    @Select("SELECT * FROM course where division_id = #{divisionId}")
    List<Course> findByDivision(@Param("divisionId") int divisionId);

    @ResultMap("findOne-int")
    @Select("SELECT * FROM course where enabled = 1")
    List<Course> findAllEnabled();


    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "division_id", property = "divisionId"),
            @Result(column = "division_id", property = "division", one = @One(select = "koreatech.cse.repository.DivisionMapper.findOne")),
    })
    @Select("SELECT * FROM course where id=#{id}")
    Course findOne(@Param("id") int id);


    @ResultMap("findOne-int")
    //@formatter off
    @Select("<script>"
            + "SELECT * FROM course where 1=1 "
            + "<if test='division != 0'> and division_id = #{division}</if>"
            + "<if test='enabled != false'> and enabled = #{enabled}</if>"
            + "<if test='code != null'> and code LIKE CONCAT('%', #{code}, '%')</if>"
            + "<if test='title != null'> and title LIKE CONCAT('%', #{title}, '%')</if>"
            + "<if test='orderParam != null and orderDir != null'> ORDER BY ${orderParam} ${orderDir}</if>"
            + "</script>")
        //@formatter on
    List<Course> findBy(Searchable searchable);

    @Update("UPDATE `course` SET"+
            "`code` = #{code},"+
            "`title` = #{title},"+
            "`credit` = #{credit},"+
            "`lec` = #{lec},"+
            "`tut` = #{tut},"+
            "`lab` = #{lab},"+
            "`ws` = #{ws},"+
            "`achieve1` = #{achieve1},"+
            "`achieve2` = #{achieve2},"+
            "`achieve3` = #{achieve3},"+
            "`achieve4` = #{achieve4},"+
            "`achieve5` = #{achieve5},"+
            "`achieve6` = #{achieve6},"+
            "`achieve7` = #{achieve7},"+
            "`achieve8` = #{achieve8},"+
            "`achieve9` = #{achieve9},"+
            "`achieve10` = #{achieve10},"+
            "`division_id` = #{divisionId},"+
            "`school_year` = #{schoolYear},"+
            "`subj_category` = #{subjCategory},"+
            "`learning_objective` = #{learningObjective},"+
            "`overview` = #{overview},"+
            "`enabled` = #{enabled} "+
            "WHERE `id` = #{id}")
    @Options(flushCache = true)
    void update(Course course);

    @Delete("DELETE FROM `course` WHERE ID = #{id}")
    void delete(Course course);
}
