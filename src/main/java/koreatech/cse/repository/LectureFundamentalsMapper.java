package koreatech.cse.repository;


import koreatech.cse.domain.role.professor.LectureFundamentals;
import koreatech.cse.domain.univ.Course;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureFundamentalsMapper {
    @Insert("INSERT INTO `lecture_fundamentals`("+
            "`course_id`,"+
            "`user_id`,"+
            "`prof_course_id`,"+
            "`intro`,"+
            "`rate_assignment`,"+
            "`rate_mid`,"+
            "`rate_final`,"+
            "`rate_optional`,"+
            "`clo1`,"+
            "`la1`,"+
            "`reflect1`,"+
            "`obj1`,"+
            "`comp1`,"+
            "`pl1`,"+
            "`pl2`,"+
            "`pl3`,"+
            "`clo2`,"+
            "`la2`,"+
            "`reflect2`,"+
            "`obj2`,"+
            "`comp2`,"+
            "`clo3`,"+
            "`la3`,"+
            "`reflect3`,"+
            "`obj3`,"+
            "`comp3`,"+
            "`clo4`,"+
            "`la4`,"+
            "`reflect4`,"+
            "`obj4`,"+
            "`comp4`,"+
            "`clo5`,"+
            "`la5`,"+
            "`reflect5`,"+
            "`obj5`,"+
            "`comp5`,"+
            "`clo6`,"+
            "`la6`,"+
            "`reflect6`,"+
            "`obj6`,"+
            "`comp6`"+
            ")VALUES("+
            "#{courseId},"+
            "#{userId},"+
            "#{profCourseId},"+
            "#{intro},"+
            "#{rateAssignment},"+
            "#{rateMid},"+
            "#{rateFinal},"+
            "#{rateOptional},"+
            "#{clo1},"+
            "#{la1},"+
            "#{reflect1},"+
            "#{obj1},"+
            "#{comp1},"+
            "#{pl1},"+
            "#{pl2},"+
            "#{pl3},"+
            "#{clo2},"+
            "#{la2},"+
            "#{reflect2},"+
            "#{obj2},"+
            "#{comp2},"+
            "#{clo3},"+
            "#{la3},"+
            "#{reflect3},"+
            "#{obj3},"+
            "#{comp3},"+
            "#{clo4},"+
            "#{la4},"+
            "#{reflect4},"+
            "#{obj4},"+
            "#{comp4},"+
            "#{clo5},"+
            "#{la5},"+
            "#{reflect5},"+
            "#{obj5},"+
            "#{comp5},"+
            "#{clo6},"+
            "#{la6},"+
            "#{reflect6},"+
            "#{obj6},"+
            "#{comp6}"+
            ")")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
    void insert(LectureFundamentals lectureFundamentals);

    @Select("SELECT * FROM lecture_fundamentals where id=#{id}")
    LectureFundamentals findOne(@Param("id") int id);

    @Select("SELECT * FROM lecture_fundamentals where prof_course_id=#{profCourseId} limit 1")
    LectureFundamentals findByProfCourseId(@Param("profCourseId") int profCourseId);


    @Update("UPDATE `lecture_fundamentals` SET"+
            "`course_id` = #{courseId},"+
            "`user_id` = #{userId},"+
            "`prof_course_id` = #{profCourseId},"+
            "`intro` = #{intro},"+
            "`rate_assignment` = #{rateAssignment},"+
            "`rate_mid` = #{rateMid},"+
            "`rate_final` = #{rateFinal},"+
            "`rate_optional` = #{rateOptional},"+
            "`clo1` = #{clo1},"+
            "`la1` = #{la1},"+
            "`reflect1` = #{reflect1},"+
            "`obj1` = #{obj1},"+
            "`comp1` = #{comp1},"+
            "`pl1` = #{pl1},"+
            "`pl2` = #{pl2},"+
            "`pl3` = #{pl3},"+
            "`clo2` = #{clo2},"+
            "`la2` = #{la2},"+
            "`reflect2` = #{reflect2},"+
            "`obj2` = #{obj2},"+
            "`comp2` = #{comp2},"+
            "`clo3` = #{clo3},"+
            "`la3` = #{la3},"+
            "`reflect3` = #{reflect3},"+
            "`obj3` = #{obj3},"+
            "`comp3` = #{comp3},"+
            "`clo4` = #{clo4},"+
            "`la4` = #{la4},"+
            "`reflect4` = #{reflect4},"+
            "`obj4` = #{obj4},"+
            "`comp4` = #{comp4},"+
            "`clo5` = #{clo5},"+
            "`la5` = #{la5},"+
            "`reflect5` = #{reflect5},"+
            "`obj5` = #{obj5},"+
            "`comp5` = #{comp5},"+
            "`clo6` = #{clo6},"+
            "`la6` = #{la6},"+
            "`reflect6` = #{reflect6},"+
            "`obj6` = #{obj6},"+
            "`comp6` = #{comp6} "+
            "WHERE `id` = #{id}")
    @Options(flushCache = true)
    void update(LectureFundamentals lectureFundamentals);

}
