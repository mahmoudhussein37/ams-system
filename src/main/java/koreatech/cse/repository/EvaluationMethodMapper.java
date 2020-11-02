package koreatech.cse.repository;


import koreatech.cse.domain.univ.EvaluationMethod;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationMethodMapper {
    @Insert("INSERT INTO `evaluation_method` (name) VALUES (#{name})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
    void insert(EvaluationMethod evaluationMethod);

    @Select("SELECT * FROM `evaluation_method`")
    List<EvaluationMethod> findAll();

    @Select("SELECT * FROM `evaluation_method` where enabled = 1")
    List<EvaluationMethod> findAllEnabled();

    @Select("SELECT * FROM `evaluation_method` where id=#{id}")
    EvaluationMethod findOne(@Param("id") int id);

    @Update("UPDATE `evaluation_method` SET"+
            "`name` = #{name},"+
            "`enabled` = #{enabled} "+
            "WHERE `id` = #{id}")
    void update(EvaluationMethod evaluationMethod);

    @Delete("DELETE FROM `evaluation_method` WHERE ID = #{id}")
    void delete(EvaluationMethod evaluationMethod);

}
