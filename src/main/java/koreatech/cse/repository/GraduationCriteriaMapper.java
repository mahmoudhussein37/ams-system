package koreatech.cse.repository;


import koreatech.cse.domain.Searchable;
import koreatech.cse.domain.User;
import koreatech.cse.domain.role.professor.ProfessorCourse;
import koreatech.cse.domain.univ.GraduationCriteria;
import koreatech.cse.domain.univ.MenuAccess;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GraduationCriteriaMapper {
    @Insert("INSERT INTO `graduation_criteria`("+
            "`division_id`,"+
            "`year`,"+
            "`msc`,"+
            "`liberal`,"+
            "`major`"+
            ")VALUES("+
            "#{divisionId},"+
            "#{year},"+
            "#{msc},"+
            "#{liberal},"+
            "#{major}"+
            ")")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
    void insert(GraduationCriteria graduationCriteria);


    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "division_id", property = "divisionId"),
            @Result(column = "division_id", property = "division", one = @One(select = "koreatech.cse.repository.DivisionMapper.findOne")),
    })
    @Select("SELECT * FROM `graduation_criteria` where id=#{id}")
    GraduationCriteria findOne(@Param("id") int id);

    @ResultMap("findOne-int")
    //@formatter off
    @Select("<script>"
            + "SELECT * FROM `graduation_criteria` where 1=1 "
            + "<if test='year != 0'> and year = #{year}</if>"
            + "<if test='division != 0'> and division_id = #{division}</if>"
            + "<if test='orderParam != null and orderDir != null'> ORDER BY ${orderParam} ${orderDir}</if>"
            + "</script>")
        //@formatter on
    List<GraduationCriteria> findByYearDivision(Searchable searchable);

    @ResultMap("findOne-int")
    @Select("SELECT * FROM `graduation_criteria` where year=#{year} and division_id = #{divisionId} limit 1")
    GraduationCriteria findOneByYearDivision(@Param("year") int year, @Param("divisionId") int divisionId);

    @Update("UPDATE `graduation_criteria` SET"+
            "`division_id` = #{divisionId},"+
            "`year` = #{year},"+
            "`msc` = #{msc},"+
            "`liberal` = #{liberal},"+
            "`enabled` = #{enabled},"+
            "`major` = #{major} "+
            "WHERE `id` = #{id}")
    void update(GraduationCriteria graduationCriteria);

    @Delete("DELETE FROM `graduation_criteria` WHERE ID = #{id}")
    void delete(GraduationCriteria graduationCriteria);

}
