package koreatech.cse.repository;


import koreatech.cse.domain.Searchable;
import koreatech.cse.domain.User;
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
            "`year`,"+
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
            "#{year},"+
            "#{impl},"+
            "#{ref},"+
            "#{etc},"+
            "#{submitDate}"+
            ")")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
    void insert(GraduationResearchPlan graduationResearchPlan);

    @Select("SELECT * FROM `graduation_research_plan`")
    List<GraduationResearchPlan> findAll();

    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "user_id", property = "user", one = @One(select = "koreatech.cse.repository.UserMapper.findOne")),
    })
    @Select("SELECT * FROM `graduation_research_plan` where id=#{id}")
    GraduationResearchPlan findOne(@Param("id") int id);

    @Select("SELECT * FROM `graduation_research_plan` where user_id=#{id} limit 1")
    GraduationResearchPlan findByUserId(@Param("id") int id);

    @ResultMap("findOne-int")
    //@formatter off
    @Select("<script>"
            + "SELECT * FROM `graduation_research_plan` g join user u on g.user_id=u.id join contact c on u.id=c.user_id join authority a on u.id = a.user_id where a.role = 'ROLE_STUDENT' "
            + "<if test='name != null'> and (c.last_name LIKE CONCAT('%', #{name}, '%') or c.first_name LIKE CONCAT('%', #{name}, '%'))</if>"
            + "<if test='number != null'> and u.number LIKE CONCAT('%', #{number}, '%')</if>"
            + "<if test='division != 0'> and u.division_id = #{division}</if>"
            + "<if test='advisor != 0'> and u.advisor_id = #{advisor}</if>"
            + "<if test='year != 0'> and g.year = #{year}</if>"
            + "<if test='orderParam != null and orderDir != null'> ORDER BY ${orderParam} ${orderDir}</if>"
            + "</script>")
        //@formatter on
    List<GraduationResearchPlan> findBySearchable(Searchable searchable);

    @ResultMap("findOne-int")
    //@formatter:off
    @Select("<script>"
            + "SELECT * FROM `graduation_research_plan` WHERE 1=1"
            + "<if test='ids != null and !ids.empty'> AND id IN <foreach item='item' collection='ids' open='(' separator=',' close=')'>#{item}</foreach></if>"
            + "</script>")
        //@formatter:on
    List<GraduationResearchPlan> findByIds(@Param("ids") List<Integer> ids);

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
            "`year` = #{year},"+
            "`etc` = #{etc},"+
            "`enabled` = #{enabled},"+
            "`submit_date` = #{submitDate} "+
            "WHERE `id` = #{id}")
    void update(GraduationResearchPlan classroom);

    @Delete("DELETE FROM `graduation_research_plan` WHERE ID = #{id}")
    void delete(GraduationResearchPlan classroom);

}
