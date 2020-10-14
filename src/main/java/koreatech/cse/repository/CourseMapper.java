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

    @Insert("INSERT INTO course (year, semester, code, title, category, credit, major_id, division_id, prof_user_id, school_year, lang, lecture_time, max_student, comp_category, subj_category) VALUES " +
            "(#{year}, #{semester}, #{code}, #{title}, #{category}, #{credit}, #{majorId}, #{divisionId}, #{profUserId}, #{schoolYear}, #{lang}, #{lectureTime}, #{maxStudent}, #{compCategory}, #{subjCategory})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
    void insert(Course course);

    @ResultMap("findOne-int")
    @Select("SELECT * FROM course")
    List<Course> findAll();

    @ResultMap("findOne-int")
    @Select("SELECT * FROM course where enabled = 1")
    List<Course> findAllEnabled();


    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "prof_user_id", property = "profUserId"),
            @Result(column = "division_id", property = "divisionId"),
            @Result(column = "major_id", property = "majorId"),
            @Result(column = "division_id", property = "division", one = @One(select = "koreatech.cse.repository.DivisionMapper.findOne")),
            @Result(column = "major_id", property = "major", one = @One(select = "koreatech.cse.repository.MajorMapper.findOne")),
            @Result(column = "prof_user_id", property = "profUser", one = @One(select = "koreatech.cse.repository.UserMapper.findOne")),
            @Result(column = "id", property = "lectureFundamentals", one = @One(select = "koreatech.cse.repository.LectureFundamentalsMapper.findByCourseId")),
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

    @ResultMap("findOne-int")
    //@formatter off
    @Select("<script>"
            + "SELECT * FROM course where 1=1 "

            + "<if test='year != 0'> and year = #{year}</if>"
            + "<if test='semester != 0'> and semester = #{semester}</if>"
            + "<if test='division != 0'> and division_id = #{division}</if>"
            + "<if test='userId != 0'> and prof_user_id = #{userId}</if>"
            + "<if test='major != 0'> and major_id = #{major}</if>"
            + "<if test='orderParam != null and orderDir != null'> ORDER BY ${orderParam} ${orderDir}</if>"
            + "</script>")
        //@formatter on
    List<Course> findBySyllabus(Searchable searchable);

    @Update("UPDATE `course` SET"+
            "`year` = #{year},"+
            "`semester` = #{semester},"+
            "`code` = #{code},"+
            "`title` = #{title},"+
            "`category` = #{category},"+
            "`credit` = #{credit},"+
            "`major_id` = #{majorId},"+
            "`division_id` = #{divisionId},"+
            "`prof_user_id` = #{profUserId},"+
            "`school_year` = #{schoolYear},"+
            "`lang` = #{lang},"+
            "`lecture_time` = #{lectureTime},"+
            "`max_student` = #{maxStudent},"+
            "`comp_category` = #{compCategory},"+
            "`subj_category` = #{subjCategory},"+
            "`enabled` = #{enabled} "+
            "WHERE `id` = #{id}")
    @Options(flushCache = true)
    void update(Course course);
}
