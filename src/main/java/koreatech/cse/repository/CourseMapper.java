package koreatech.cse.repository;


import koreatech.cse.domain.Searchable;
import koreatech.cse.domain.univ.Course;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CourseMapper {

    @Insert("INSERT INTO course (code, title, credit, division_id, school_year, subj_category, learning_objective, overview, lec, tut, lab, ws, prerequisite, alternative) VALUES " +
            "(#{code}, #{title}, #{credit}, #{divisionId}, #{schoolYear}, #{subjCategory}, #{learningObjective}, #{overview}, #{lec}, #{tut}, #{lab}, #{ws}, #{prerequisite}, #{alternative})")
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
            + "<if test='year != 0'> and year = #{year}</if>"
            + "<if test='semester != 0'> and semester = #{semester}</if>"
            + "<if test='orderParam != null and orderDir != null'> ORDER BY ${orderParam} ${orderDir}</if>"
            + "</script>")
        //@formatter on
    List<Course> findByYearSemester(Searchable searchable);

    @ResultMap("findOne-int")
    //@formatter off
    @Select("<script>"
            + "SELECT * FROM course where 1=1 "
            + "<if test='division != 0'> and division_id = #{division}</if>"
            + "<if test='code != null'> and code LIKE CONCAT('%', #{code}, '%')</if>"
            + "<if test='title != null'> and title LIKE CONCAT('%', #{title}, '%')</if>"
            + "<if test='orderParam != null and orderDir != null'> ORDER BY ${orderParam} ${orderDir}</if>"
            + "</script>")
        //@formatter on
    List<Course> findByCodeTitleDivision(Searchable searchable);


    @ResultMap("findOne-int")
    //@formatter off
    @Select("<script>"
            + "SELECT * FROM course c join semester s on c.semester_id = s.id where 1=1 "
            + "<if test='year != 0'> and s.year = #{year}</if>"
            + "<if test='semester != 0'> and s.semester = #{semester}</if>"
            + "<if test='division != 0'> and c.division_id = #{division}</if>"
            + "<if test='enabled != false'> and c.enabled = #{enabled}</if>"
            + "<if test='orderParam != null and orderDir != null'> ORDER BY ${orderParam} ${orderDir}</if>"
            + "</script>")
        //@formatter on
    List<Course> findByYearSemesterDivision(Searchable searchable);

    @ResultMap("findOne-int")
    //@formatter off
    @Select("<script>"
            + "SELECT * FROM course c join semester s on c.semester_id = s.id where 1=1 "

            + "<if test='year != 0'> and s.year = #{year}</if>"
            + "<if test='semester != 0'> and s.semester = #{semester}</if>"
            + "<if test='division != 0'> and c.division_id = #{division}</if>"
            + "<if test='userId != 0'> and c.prof_user_id = #{userId}</if>"

            + "<if test='orderParam != null and orderDir != null'> ORDER BY ${orderParam} ${orderDir}</if>"
            + "</script>")
        //@formatter on
    List<Course> findByYearSemesterDivisionProfId(Searchable searchable);

    @Update("UPDATE `course` SET"+
            "`code` = #{code},"+
            "`title` = #{title},"+
            "`credit` = #{credit},"+
            "`lec` = #{lec},"+
            "`tut` = #{tut},"+
            "`lab` = #{lab},"+
            "`ws` = #{ws},"+
            "`prerequisite` = #{prerequisite},"+
            "`alternative` = #{alternative},"+
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
