package koreatech.cse.repository;


import koreatech.cse.domain.Searchable;
import koreatech.cse.domain.role.professor.ProfessorCourse;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProfessorCourseMapper {

    @Insert("INSERT INTO professor_course (user_id, course_id, limit_student, num_student, attendance, lateness, absence, lec, tut, lab, ws, prerequisite, alternative, credit) VALUES (#{userId}, #{courseId}, #{limitStudent}, #{numStudent}, #{attendance}, #{lateness}, #{absence}, #{lec}, #{tut}, #{lab}, #{ws}, #{prerequisite}, #{alternative}, #{credit})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
    void insert(ProfessorCourse professorCourse);

    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "course_id", property = "courseId"),
            @Result(column = "course_id", property = "course", one = @One(select = "koreatech.cse.repository.CourseMapper.findOne")),
    })
    @Select("SELECT * FROM professor_course where id=#{id}")
    ProfessorCourse findOne(@Param("id") int id);

    @ResultMap("findOne-int")
    @Select("SELECT * FROM professor_course where user_id = #{userId}")
    List<ProfessorCourse> findByUserId(@Param("userId") int userId);

    @ResultMap("findOne-int")
    //@formatter off
    @Select("<script>"
            + "SELECT * FROM professor_course p join course c on p.course_id=c.id where p.user_id = #{userId} "

            + "<if test='year != 0'> and c.year = #{year}</if>"
            + "<if test='semester != 0'> and c.semester = #{semester}</if>"
            + "<if test='orderParam != null and orderDir != null'> ORDER BY ${orderParam} ${orderDir}</if>"
            + "</script>")
        //@formatter on
    List<ProfessorCourse> findByAttendance(Searchable searchable);

    @Update("UPDATE `professor_course` SET "+
            "`user_id` = #{userId},"+
            "`course_id` = #{courseId},"+
            "`limit_student` = #{limitStudent},"+
            "`num_student` = #{numStudent},"+
            "`attendance` = #{attendance},"+
            "`lateness` = #{lateness},"+
            "`absence` = #{absence},"+
            "`lec` = #{lec},"+
            "`lab` = #{lab},"+
            "`tut` = #{tut},"+
            "`ws` = #{ws},"+
            "`credit` = #{credit},"+
            "`alternative` = #{alternative},"+
            "`prerequisite` = #{prerequisite} "+
            "WHERE `id` = #{id}")
    @Options(flushCache = true)
    void update(ProfessorCourse professorCourse);

    @Delete("DELETE FROM `professor_course` WHERE ID = #{id}")
    void delete(ProfessorCourse professorCourse);

}
