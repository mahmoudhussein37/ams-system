package koreatech.cse.repository;


import koreatech.cse.domain.univ.LangCert;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LangCertMapper {
    @Insert("INSERT INTO `lang_cert`("+
            "`user_id`,"+
            "`type`,"+
            "`l_type`,"+
            "`level`,"+
            "`agency`,"+
            "`date`,"+
            "`approve`"+
            ")VALUES("+
            "#{userId},"+
            "#{type},"+
            "#{lType},"+
            "#{level},"+
            "#{agency},"+
            "#{date},"+
            "#{approve}"+
            ")")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
    void insert(LangCert langCert);


    @Select("SELECT * FROM `lang_cert` where id=#{id}")
    LangCert findOne(@Param("id") int id);

    @Select("SELECT * FROM `lang_cert` where user_id=#{userId} and type = #{type}")
    List<LangCert> findByUserIdType(@Param("userId") int userId, @Param("type") String type);

    @Select("SELECT * FROM `lang_cert` where user_id=#{userId} order by date desc")
    List<LangCert> findByUserId(@Param("userId") int userId);

    @Update("UPDATE `lang_cert` SET"+
            "`user_id` = #{userId},"+
            "`type` = #{type},"+
            "`l_type` = #{lType},"+
            "`level` = #{level},"+
            "`agency` = #{agency},"+
            "`date` = #{date},"+
            "`approve` = #{approve} "+
            "WHERE `id` = #{id}")
    void update(LangCert langCert);

    @Delete("DELETE FROM `lang_cert` WHERE ID = #{id}")
    void delete(LangCert langCert);

}
