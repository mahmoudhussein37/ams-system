package koreatech.cse.repository;


import koreatech.cse.domain.Contact;
import koreatech.cse.domain.Searchable;
import koreatech.cse.domain.User;
import koreatech.cse.domain.univ.Course;
import koreatech.cse.domain.univ.Major;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CourseMapper {

    @Insert("INSERT INTO course (year, semester, code, title, category, credit, major_id, division_id, school_year, lang, lecture_time, comp_category, subj_category) VALUES " +
            "(#{year}, #{semester}, #{code}, #{title}, #{category}, #{credit}, #{majorId}, #{divisionId}, #{schoolYear}, #{lang}, #{lectureTime}, #{compCategory}, #{subjCategory})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
    void insert(Course course);

    @Select("SELECT * FROM course")
    List<Course> findAll();

    @Select("SELECT * FROM course where enabled = 1")
    List<Course> findAllEnabled();

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
            + "SELECT * FROM course where 1=1 "

            + "<if test='year != 0'> and year = #{year}</if>"
            + "<if test='semester != 0'> and semester = #{semester}</if>"
            + "<if test='division != 0'> and division_id = #{division}</if>"
            + "<if test='orderParam != null and orderDir != null'> ORDER BY ${orderParam} ${orderDir}</if>"
            + "</script>")
        //@formatter on
    List<Course> findByInquiryCourse(Searchable searchable);

    @ResultMap("findOne-int")
    //@formatter off
    @Select("<script>"
            + "SELECT * FROM course where 1=1 "

            + "<if test='year != 0'> and year = #{year}</if>"
            + "<if test='semester != 0'> and semester = #{semester}</if>"
            + "<if test='division != 0'> and division_id = #{division}</if>"
            + "<if test='major != 0'> and major_id = #{major}</if>"
            + "<if test='orderParam != null and orderDir != null'> ORDER BY ${orderParam} ${orderDir}</if>"
            + "</script>")
        //@formatter on
    List<Course> findByCourseManagement(Searchable searchable);

    @Update("UPDATE `course` SET"+
            "`year` = #{year},"+
            "`semester` = #{semester},"+
            "`code` = #{code},"+
            "`title` = #{title},"+
            "`category` = #{category},"+
            "`credit` = #{credit},"+
            "`major_id` = #{majorId},"+
            "`division_id` = #{divisionId},"+
            "`school_year` = #{schoolYear},"+
            "`lang` = #{lang},"+
            "`lecture_time` = #{lectureTime},"+
            "`comp_category` = #{compCategory},"+
            "`subj_category` = #{subjCategory},"+
            "`enabled` = #{enabled} "+
            "WHERE `id` = #{id}")
    @Options(flushCache = true)
    void update(Course course);
}
