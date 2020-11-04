package koreatech.cse.repository;


import koreatech.cse.domain.Searchable;
import koreatech.cse.domain.univ.Course;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CourseMapper {

    @Insert("INSERT INTO course (semester_id, code, title, category, credit, division_id, divide, prof_user_id, school_year, lang, lec, tut, lab, ws, lecture_time, max_student, comp_category, subj_category) VALUES " +
            "(#{semesterId}, #{code}, #{title}, #{category}, #{credit}, #{divisionId}, #{divide}, #{profUserId}, #{schoolYear}, #{lang}, #{lec}, #{tut}, #{lab}, #{ws}, #{lectureTime}, #{maxStudent}, #{compCategory}, #{subjCategory})")
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
            @Result(column = "prof_user_id", property = "profUserId"),
            @Result(column = "division_id", property = "divisionId"),
            @Result(column = "semester_id", property = "semesterId"),
            @Result(column = "division_id", property = "division", one = @One(select = "koreatech.cse.repository.DivisionMapper.findOne")),
            @Result(column = "prof_user_id", property = "profUser", one = @One(select = "koreatech.cse.repository.UserMapper.findOne")),
            @Result(column = "id", property = "lectureFundamentals", one = @One(select = "koreatech.cse.repository.LectureFundamentalsMapper.findByCourseId")),
            @Result(column = "semester_id", property = "semester", one = @One(select = "koreatech.cse.repository.SemesterMapper.findOne")),
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
    List<Course> findByMakeupClass(Searchable searchable);


    @ResultMap("findOne-int")
    //@formatter off
    @Select("<script>"
            + "SELECT * FROM course c join semester s on c.semester_id = s.id where 1=1 "
            + "<if test='year != 0'> and s.year = #{year}</if>"
            + "<if test='semester != 0'> and s.semester = #{semester}</if>"
            + "<if test='division != 0'> and c.division_id = #{division}</if>"
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
            "`semester_id` = #{semesterId},"+
            "`code` = #{code},"+
            "`title` = #{title},"+
            "`category` = #{category},"+
            "`credit` = #{credit},"+
            "`division_id` = #{divisionId},"+
            "`divide` = #{divide},"+
            "`prof_user_id` = #{profUserId},"+
            "`school_year` = #{schoolYear},"+
            "`lang` = #{lang},"+
            "`lec` = #{lec},"+
            "`tut` = #{tut},"+
            "`lab` = #{lab},"+
            "`ws` = #{ws},"+
            "`lecture_time` = #{lectureTime},"+
            "`max_student` = #{maxStudent},"+
            "`comp_category` = #{compCategory},"+
            "`subj_category` = #{subjCategory},"+
            "`enabled` = #{enabled} "+
            "WHERE `id` = #{id}")
    @Options(flushCache = true)
    void update(Course course);

    @Delete("DELETE FROM `course` WHERE ID = #{id}")
    void delete(Course course);
}
