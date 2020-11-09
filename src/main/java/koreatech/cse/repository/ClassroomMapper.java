package koreatech.cse.repository;


import koreatech.cse.domain.univ.Classroom;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassroomMapper {
    @Insert("INSERT INTO `classroom` (code, name) VALUES (#{code}, #{name})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
    void insert(Classroom classroom);

    @Select("SELECT * FROM `classroom`")
    List<Classroom> findAll();

    @Select("SELECT * FROM `classroom` where enabled = 1")
    List<Classroom> findAllEnabled();

    @Select("SELECT * FROM `classroom` where id=#{id}")
    Classroom findOne(@Param("id") int id);

    @Update("UPDATE `classroom` SET"+
            "`name` = #{name},"+
            "`code` = #{code},"+
            "`enabled` = #{enabled} "+
            "WHERE `id` = #{id}")
    void update(Classroom classroom);

    @Delete("DELETE FROM `classroom` WHERE ID = #{id}")
    void delete(Classroom classroom);

}
