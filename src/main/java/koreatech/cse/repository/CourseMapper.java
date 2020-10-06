package koreatech.cse.repository;


import koreatech.cse.domain.Searchable;
import koreatech.cse.domain.User;
import koreatech.cse.domain.univ.Course;
import koreatech.cse.domain.univ.Major;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CourseMapper {
    @Insert("INSERT INTO course (year, semester, code, title, category, credit, division) VALUES (#{year}, #{semester}, #{code}, #{title}, #{category}, #{credit}, #{division})")
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


}
