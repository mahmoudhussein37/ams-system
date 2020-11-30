package koreatech.cse.repository;


import koreatech.cse.domain.role.professor.Assessment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssessmentMapper {
    @Insert("INSERT INTO `assessment`("+
            "`user_id`,"+
            "`prof_course_id`,"+
            "`comment`,"+
            "`score1`,"+
            "`score2`,"+
            "`score3`,"+
            "`score4`,"+
            "`score5`,"+
            "`score6`,"+
            "`score7`,"+
            "`score8`,"+
            "`score9`,"+
            "`score10`,"+
            "`score11`,"+
            "`score12`,"+
            "`score13`,"+
            "`score14`,"+
            "`score15`,"+
            "`score16`,"+
            "`score17`,"+
            "`score18`,"+
            "`score19`,"+
            "`score20`,"+
            "`item1`,"+
            "`item2`,"+
            "`item3`,"+
            "`item4`,"+
            "`item5`,"+
            "`item6`,"+
            "`item7`,"+
            "`item8`,"+
            "`item9`,"+
            "`item10`,"+
            "`item11`,"+
            "`item12`,"+
            "`item13`,"+
            "`item14`,"+
            "`item15`,"+
            "`item16`,"+
            "`item17`,"+
            "`item18`,"+
            "`item19`,"+
            "`item20`"+
            ")VALUES("+
            "#{userId},"+
            "#{profCourseId},"+
            "#{comment},"+
            "#{score1},"+
            "#{score2},"+
            "#{score3},"+
            "#{score4},"+
            "#{score5},"+
            "#{score6},"+
            "#{score7},"+
            "#{score8},"+
            "#{score9},"+
            "#{score10},"+
            "#{score11},"+
            "#{score12},"+
            "#{score13},"+
            "#{score14},"+
            "#{score15},"+
            "#{score16},"+
            "#{score17},"+
            "#{score18},"+
            "#{score19},"+
            "#{score20},"+
            "#{item1},"+
            "#{item2},"+
            "#{item3},"+
            "#{item4},"+
            "#{item5},"+
            "#{item6},"+
            "#{item7},"+
            "#{item8},"+
            "#{item9},"+
            "#{item10},"+
            "#{item11},"+
            "#{item12},"+
            "#{item13},"+
            "#{item14},"+
            "#{item15},"+
            "#{item16},"+
            "#{item17},"+
            "#{item18},"+
            "#{item19},"+
            "#{item20}"+
            ")")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
    void insert(Assessment assessment);

    @Select("SELECT * FROM `assessment`")
    List<Assessment> findAll();

    @Select("SELECT * FROM `assessment` where prof_course_id=#{profCourseId}")
    List<Assessment> findByProfCourseId(@Param("profCourseId") int profCourseId);

    @Select("SELECT * FROM `assessment` where (" +
            "item1=#{assessmentFactorId} or " +
            "item2=#{assessmentFactorId} or " +
            "item3=#{assessmentFactorId} or " +
            "item4=#{assessmentFactorId} or " +
            "item5=#{assessmentFactorId} or " +
            "item6=#{assessmentFactorId} or " +
            "item7=#{assessmentFactorId} or " +
            "item8=#{assessmentFactorId} or " +
            "item9=#{assessmentFactorId} or " +
            "item10=#{assessmentFactorId} or " +
            "item11=#{assessmentFactorId} or " +
            "item12=#{assessmentFactorId} or " +
            "item13=#{assessmentFactorId} or " +
            "item14=#{assessmentFactorId} or " +
            "item15=#{assessmentFactorId} or " +
            "item16=#{assessmentFactorId} or " +
            "item17=#{assessmentFactorId} or " +
            "item18=#{assessmentFactorId} or " +
            "item19=#{assessmentFactorId} or " +
            "item20=#{assessmentFactorId} " +
            ")")
    List<Assessment> findByAssessmentFactorId(@Param("assessmentFactorId") int assessmentFactorId);

    @Select("SELECT * FROM `assessment` where id=#{id}")
    Assessment findOne(@Param("id") int id);

    @Update("UPDATE `assessment` SET"+
            "`user_id` = #{userId},"+
            "`prof_course_id` = #{profCourseId},"+
            "`comment` = #{comment},"+
            "`score1` = #{score1},"+
            "`score2` = #{score2},"+
            "`score3` = #{score3},"+
            "`score4` = #{score4},"+
            "`score5` = #{score5},"+
            "`score6` = #{score6},"+
            "`score7` = #{score7},"+
            "`score8` = #{score8},"+
            "`score9` = #{score9},"+
            "`score10` = #{score10},"+
            "`score11` = #{score11},"+
            "`score12` = #{score12},"+
            "`score13` = #{score13},"+
            "`score14` = #{score14},"+
            "`score15` = #{score15},"+
            "`score16` = #{score16},"+
            "`score17` = #{score17},"+
            "`score18` = #{score18},"+
            "`score19` = #{score19},"+
            "`score20` = #{score20},"+
            "`item1` = #{item1},"+
            "`item2` = #{item2},"+
            "`item3` = #{item3},"+
            "`item4` = #{item4},"+
            "`item5` = #{item5},"+
            "`item6` = #{item6},"+
            "`item7` = #{item7},"+
            "`item8` = #{item8},"+
            "`item9` = #{item9},"+
            "`item10` = #{item10},"+
            "`item11` = #{item11},"+
            "`item12` = #{item12},"+
            "`item13` = #{item13},"+
            "`item14` = #{item14},"+
            "`item15` = #{item15},"+
            "`item16` = #{item16},"+
            "`item17` = #{item17},"+
            "`item18` = #{item18},"+
            "`item19` = #{item19},"+
            "`item20` = #{item20} "+
            "WHERE `id` = #{id}")
    void update(Assessment assessment);

    @Delete("DELETE FROM `assessment` WHERE ID = #{id}")
    void delete(Assessment assessment);

}
