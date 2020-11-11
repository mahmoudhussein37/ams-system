package koreatech.cse.repository;


import koreatech.cse.domain.role.professor.ProfessorCourse;
import koreatech.cse.domain.role.student.StudentCourse;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StudentCourseMapper {


    @Insert("INSERT INTO student_course (user_id, course_id, acquire, prof_course_id) VALUES (#{userId}, #{courseId}, #{acquire}, #{profCourseId})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
    void insert(StudentCourse studentCourse);

    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "course_id", property = "courseId"),
            @Result(column = "prof_course_id", property = "profCourseId"),
            @Result(column = "course_id", property = "course", one = @One(select = "koreatech.cse.repository.CourseMapper.findOne")),
            @Result(column = "prof_course_id", property = "professorCourse", one = @One(select = "koreatech.cse.repository.ProfessorCourseMapper.findOne")),
            @Result(column = "user_id", property = "studentUser", one = @One(select = "koreatech.cse.repository.UserMapper.findOne")),
    })
    @Select("SELECT * FROM student_course where id=#{id}")
    StudentCourse findOne(@Param("id") int id);

    @ResultMap("findOne-int")
    @Select("SELECT * FROM student_course where user_id = #{userId}")
    List<StudentCourse> findByUserId(@Param("userId") int userId);

    @ResultMap("findOne-int")
    @Select("SELECT * FROM student_course where prof_course_id = #{profCourseId}")
    List<StudentCourse> findByProfCourseId(@Param("profCourseId") int profCourseId);

    @ResultMap("findOne-int")
    @Select("SELECT * FROM student_course where user_id = #{userId} and prof_course_id = #{profCourseId} limit 1")
    StudentCourse findByUserIdProfCourseId(@Param("userId") int userId, @Param("profCourseId") int profCourseId);


    @Update("UPDATE `student_course` SET"+
            "`user_id` = #{userId},"+
            "`course_id` = #{courseId},"+
            "`prof_course_id` = #{profCourseId},"+
            "`acquire` = #{acquire} "+
            "WHERE `id` = #{id}")
    @Options(flushCache = true)
    void update(StudentCourse studentCourse);

    @Delete("DELETE FROM `student_course` WHERE ID = #{id}")
    void delete(StudentCourse studentCourse);

}
