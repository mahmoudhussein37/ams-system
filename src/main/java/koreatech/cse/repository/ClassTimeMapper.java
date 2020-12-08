package koreatech.cse.repository;


import koreatech.cse.domain.role.professor.ClassTime;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassTimeMapper {
    @Insert("INSERT INTO `class_time` (prof_course_id, w, s, e) VALUES (#{profCourseId}, #{w}, #{s}, #{e})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
    void insert(ClassTime classTime);


    @Select("SELECT * FROM `class_time` where id = #{id}")
    ClassTime findOne(@Param("id") int id);

    @Select("SELECT * FROM `class_time` where prof_course_id = #{profCourseId} order by w asc, s asc")
    List<ClassTime> findByProfCourseId(@Param("profCourseId") int profCourseId);

    @Delete("DELETE FROM `class_time` WHERE ID = #{id}")
    void delete(ClassTime classTime);



}
