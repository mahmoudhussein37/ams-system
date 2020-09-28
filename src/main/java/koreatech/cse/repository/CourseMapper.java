package koreatech.cse.repository;


import koreatech.cse.domain.univ.Course;
import koreatech.cse.domain.univ.Major;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CourseMapper {
    @Insert("INSERT INTO course (year, semester, code, title, category, credit) VALUES (#{year}, #{semester}, #{code}, #{title}, #{category}, #{credit})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
    void insert(Course course);

    @Select("SELECT * FROM course")
    List<Course> findAll();

    @Select("SELECT * FROM course where id=#{id}")
    Course findOne(@Param("id") int id);



}
