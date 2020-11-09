package koreatech.cse.repository;


import koreatech.cse.domain.univ.AltCourse;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AltCourseMapper {
    @Insert("INSERT INTO `alt_course` (course_id, target_course_id, type) VALUES (#{courseId}, #{targetCourseId}, #{type})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
    void insert(AltCourse altCourse);


    @ResultMap("findOne-int")
    @Select("SELECT * FROM `alt_course` where target_course_id=#{id}")
    List<AltCourse> findByTargetCourseId(@Param("id") int id);


    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "course_id", property = "courseId"),
            @Result(column = "course_id", property = "course", one = @One(select = "koreatech.cse.repository.CourseMapper.findOne")),

    })
    @Select("SELECT * FROM `alt_course` where id=#{id}")
    AltCourse findOne(@Param("id") int id);

    @Update("UPDATE `alt_course` SET"+
            "`course_id` = #{courseId},"+
            "`target_course_id` = #{targetCourseId},"+
            "`type` = #{type} "+
            "WHERE `id` = #{id}")
    void update(AltCourse altCourse);

    @Delete("DELETE FROM `alt_course` WHERE ID = #{id}")
    void delete(AltCourse altCourse);

}
