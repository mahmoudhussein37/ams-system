package koreatech.cse.repository;


import koreatech.cse.domain.role.student.StudentCourse;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StudentCourseMapper {


    @Insert("INSERT INTO student_course (user_id, course_id, acquire, retake) VALUES (#{userId}, #{courseId}, #{acquire}, #{retake})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
    void insert(StudentCourse studentCourse);

    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "course_id", property = "courseId"),
            @Result(column = "course_id", property = "course", one = @One(select = "koreatech.cse.repository.CourseMapper.findOne")),
    })
    @Select("SELECT * FROM student_course where id=#{id}")
    StudentCourse findOne(@Param("id") int id);

    @ResultMap("findOne-int")
    @Select("SELECT * FROM student_course where user_id = #{userId}")
    List<StudentCourse> findByUserId(@Param("userId") int userId);

}
