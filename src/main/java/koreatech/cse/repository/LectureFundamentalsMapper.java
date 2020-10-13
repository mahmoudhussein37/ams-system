package koreatech.cse.repository;


import koreatech.cse.domain.role.professor.LectureFundamentals;
import koreatech.cse.domain.univ.Course;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureFundamentalsMapper {
    @Insert("INSERT INTO lecture_fundamentals (course_id, user_id, intro, achieve1, achieve2, achieve3, achieve4, achieve5, achieve6, achieve7, achieve8, achieve9, achieve10, rate_attendance, rate_assignment, rate_mid, rate_final, rate_optional) " +
            "VALUES (#{courseId}, #{userId}, #{intro}, #{achieve1}, #{achieve2}, #{achieve3}, #{achieve4}, #{achieve5}, #{achieve6}, #{achieve7}, #{achieve8}, #{achieve9}, #{achieve10}, #{rateAttendance}, #{rateAssignment}, #{rateMid}, #{rateFinal}, #{rateOptional})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
    void insert(LectureFundamentals lectureFundamentals);

    @Select("SELECT * FROM lecture_fundamentals where id=#{id}")
    LectureFundamentals findOne(@Param("id") int id);

    @Select("SELECT * FROM lecture_fundamentals where course_id=#{courseId} limit 1")
    LectureFundamentals findByCourseId(@Param("courseId") int courseId);


    @Update("UPDATE `lecture_fundamentals` SET"+
            "`course_id` = #{courseId},"+
            "`user_id` = #{userId},"+
            "`intro` = #{intro},"+
            "`achieve1` = #{achieve1},"+
            "`achieve2` = #{achieve2},"+
            "`achieve3` = #{achieve3},"+
            "`achieve4` = #{achieve4},"+
            "`achieve5` = #{achieve5},"+
            "`achieve6` = #{achieve6},"+
            "`achieve7` = #{achieve7},"+
            "`achieve8` = #{achieve8},"+
            "`achieve9` = #{achieve9},"+
            "`achieve10` = #{achieve10},"+
            "`rate_attendance` = #{rateAttendance},"+
            "`rate_assignment` = #{rateAssignment},"+
            "`rate_mid` = #{rateMid},"+
            "`rate_final` = #{rateFinal},"+
            "`rate_optional` = #{rateOptional} "+
            "WHERE `id` = #{id}")
    @Options(flushCache = true)
    void update(LectureFundamentals lectureFundamentals);

}
