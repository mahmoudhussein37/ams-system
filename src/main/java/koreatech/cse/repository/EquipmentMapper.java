package koreatech.cse.repository;


import koreatech.cse.domain.univ.Equipment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentMapper {
    @Insert("INSERT INTO `equipment` (code, name) VALUES (#{code}, #{name})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
    void insert(Equipment equipment);

    @Select("SELECT * FROM `equipment`")
    List<Equipment> findAll();

    @Select("SELECT * FROM `equipment` where enabled = 1")
    List<Equipment> findAllEnabled();

    @Select("SELECT * FROM `equipment` where id=#{id}")
    Equipment findOne(@Param("id") int id);

    @Update("UPDATE `equipment` SET"+
            "`name` = #{name},"+
            "`code` = #{code},"+
            "`enabled` = #{enabled} "+
            "WHERE `id` = #{id}")
    void update(Equipment equipment);

    @Delete("DELETE FROM `equipment` WHERE ID = #{id}")
    void delete(Equipment equipment);

}
