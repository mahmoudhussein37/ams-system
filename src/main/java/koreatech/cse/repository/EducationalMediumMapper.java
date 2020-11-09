package koreatech.cse.repository;


import koreatech.cse.domain.univ.EducationalMedium;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EducationalMediumMapper {
    @Insert("INSERT INTO `educational_medium` (name) VALUES (#{name})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
    void insert(EducationalMedium educationalMedium);

    @Select("SELECT * FROM `educational_medium`")
    List<EducationalMedium> findAll();

    @Select("SELECT * FROM `educational_medium` where enabled = 1")
    List<EducationalMedium> findAllEnabled();

    @Select("SELECT * FROM `educational_medium` where id=#{id}")
    EducationalMedium findOne(@Param("id") int id);

    @Update("UPDATE `educational_medium` SET"+
            "`name` = #{name},"+
            "`enabled` = #{enabled} "+
            "WHERE `id` = #{id}")
    void update(EducationalMedium educationalMedium);

    @Delete("DELETE FROM `educational_medium` WHERE ID = #{id}")
    void delete(EducationalMedium educationalMedium);

}
