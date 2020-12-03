package koreatech.cse.repository;


import koreatech.cse.domain.univ.AssessmentFactor;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssessmentFactorMapper {
    @Insert("INSERT INTO `assessment_factor`("+
            "`title`,"+
            "`question`,"+
            "`course_id`,"+
            "`enabled`"+
            ")VALUES("+
            "#{title},"+
            "#{question},"+
            "#{courseId},"+
            "#{enabled}"+
            ")")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
    void insert(AssessmentFactor assessmentFactor);

    @Select("SELECT * FROM `assessment_factor`")
    List<AssessmentFactor> findAll();

    @Select("SELECT * FROM `assessment_factor` where course_id=#{id}")
    List<AssessmentFactor> findByCourseId(@Param("id") int id);

    @Select("SELECT * FROM `assessment_factor` where course_id=#{id} and enabled = 1")
    List<AssessmentFactor> findByCourseIdEnabled(@Param("id") int id);

    @Select("SELECT * FROM `assessment_factor` where enabled = 1")
    List<AssessmentFactor> findAllEnabled();

    @Select("SELECT * FROM `assessment_factor` where id=#{id}")
    AssessmentFactor findOne(@Param("id") int id);

    @Update("UPDATE `assessment_factor` SET"+
            "`title` = #{title},"+
            "`question` = #{question},"+
            "`course_id` = #{courseId},"+
            "`enabled` = #{enabled} "+
            "WHERE `id` = #{id}")
    void update(AssessmentFactor assessmentFactor);

    @Delete("DELETE FROM `assessment_factor` WHERE ID = #{id}")
    void delete(AssessmentFactor assessmentFactor);

}
