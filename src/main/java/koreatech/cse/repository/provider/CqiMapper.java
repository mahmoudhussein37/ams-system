package koreatech.cse.repository.provider;


import koreatech.cse.domain.role.professor.Cqi;
import koreatech.cse.domain.univ.Semester;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CqiMapper {
    @Insert("INSERT INTO `cqi`("+
            "`user_id`,"+
            "`course_id`,"+
            "`prof_course_id`,"+
            "`semester_id`,"+
            "`divide`,"+
            "`score1`,"+
            "`score2`,"+
            "`score3`,"+
            "`score4`,"+
            "`score5`,"+
            "`score6`,"+
            "`problem`,"+
            "`plan`"+
            ")VALUES("+
            "#{userId},"+
            "#{courseId},"+
            "#{profCourseId},"+
            "#{semesterId},"+
            "#{divide},"+
            "#{score1},"+
            "#{score2},"+
            "#{score3},"+
            "#{score4},"+
            "#{score5},"+
            "#{score6},"+
            "#{problem},"+
            "#{plan}"+
            ")")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
    void insert(Cqi cqi);

    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "semester_id", property = "semesterId"),
            @Result(column = "user_id", property = "professorUser", one = @One(select = "koreatech.cse.repository.UserMapper.findOne")),
            @Result(column = "semester_id", property = "semester", one = @One(select = "koreatech.cse.repository.SemesterMapper.findOne")),
    })
    @Select("SELECT * FROM `cqi` where id=#{id}")
    Cqi findOne(@Param("id") int id);

    @ResultMap("findOne-int")
    @Select("SELECT * FROM cqi c join semester s on c.semester_id = s.id where s.year=#{year} and c.course_id = #{courseId} and c.divide = #{divide} limit 1")
    Cqi findByYearCourseIdDivide(@Param("year") int year, @Param("courseId") int courseId, @Param("divide") int divide);

    @ResultMap("findOne-int")
    @Select("SELECT * FROM cqi where prof_course_id=#{profCourseId} limit 1")
    Cqi findByProfCourseId(@Param("profCourseId") int profCourseId);

    @Update("UPDATE `cqi` SET"+
            "`user_id` = #{userId},"+
            "`course_id` = #{courseId},"+
            "`prof_course_id` = #{profCourseId},"+
            "`semester_id` = #{semesterId},"+
            "`divide` = #{divide},"+
            "`score1` = #{score1},"+
            "`score2` = #{score2},"+
            "`score3` = #{score3},"+
            "`score4` = #{score4},"+
            "`score5` = #{score5},"+
            "`score6` = #{score6},"+
            "`problem` = #{problem},"+
            "`plan` = #{plan} "+
            "WHERE `id` = #{id}")
    void update(Cqi cqi);

    @Delete("DELETE FROM `cqi` WHERE ID = #{id}")
    void delete(Cqi cqi);

}
