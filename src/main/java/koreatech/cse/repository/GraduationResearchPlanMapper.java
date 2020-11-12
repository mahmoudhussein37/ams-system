package koreatech.cse.repository;


import koreatech.cse.domain.role.student.GraduationResearchPlan;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GraduationResearchPlanMapper {
    @Insert("INSERT INTO `graduation_research_plan`("+
            "`user_id`,"+
            "`title`,"+
            "`type`,"+
            "`start_date`,"+
            "`end_date`,"+
            "`purpose`,"+
            "`scope`,"+
            "`method`,"+
            "`impl`,"+
            "`ref`,"+
            "`etc`,"+
            "`submit_date`"+
            ")VALUES("+
            "#{userId},"+
            "#{title},"+
            "#{type},"+
            "#{startDate},"+
            "#{endDate},"+
            "#{purpose},"+
            "#{scope},"+
            "#{method},"+
            "#{impl},"+
            "#{ref},"+
            "#{etc},"+
            "#{submitDate}"+
            ")")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
    void insert(GraduationResearchPlan graduationResearchPlan);

    @Select("SELECT * FROM `graduation_research_plan`")
    List<GraduationResearchPlan> findAll();

    @Select("SELECT * FROM `graduation_research_plan` where id=#{id}")
    GraduationResearchPlan findOne(@Param("id") int id);

    @Select("SELECT * FROM `graduation_research_plan` where user_id=#{id} limit 1")
    GraduationResearchPlan findByUserId(@Param("id") int id);

    @Update("UPDATE `graduation_research_plan` SET"+
            "`user_id` = #{userId},"+
            "`title` = #{title},"+
            "`type` = #{type},"+
            "`start_date` = #{startDate},"+
            "`end_date` = #{endDate},"+
            "`purpose` = #{purpose},"+
            "`scope` = #{scope},"+
            "`method` = #{method},"+
            "`impl` = #{impl},"+
            "`ref` = #{ref},"+
            "`etc` = #{etc},"+
            "`enabled` = #{enabled},"+
            "`submit_date` = #{submitDate} "+
            "WHERE `id` = #{id}")
    void update(GraduationResearchPlan classroom);

    @Delete("DELETE FROM `graduation_research_plan` WHERE ID = #{id}")
    void delete(GraduationResearchPlan classroom);

}
