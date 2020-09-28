package koreatech.cse.repository;


import koreatech.cse.domain.constant.StudentCourse;
import koreatech.cse.domain.univ.Course;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StudentCourseMapper {


    @Insert("INSERT INTO student_course (user_id, course_id, acquire, retake) VALUES (#{userId}, #{courseId}, #{acquire}, #{retake})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
    void insert(StudentCourse studentCourse);

    @Select("SELECT * FROM student_course where user_id = #{userId}")
    List<StudentCourse> findByUserId(@Param("userId") int userId);

    @Select("SELECT * FROM student_course where id=#{id}")
    StudentCourse findOne(@Param("id") int id);



}
