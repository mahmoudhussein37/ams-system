package koreatech.cse.repository;

import koreatech.cse.domain.Contact;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactMapper {


    @Insert("insert into contact ( user_id, first_name, last_name) values ( " + "#{userId}, #{firstName},  #{lastName})")
    @SelectKey(statement = "select @@identity", keyProperty = "id", before = false, resultType = int.class)
    @Options(flushCache = true)
    void insert(Contact Contact);
    
    @Update("update contact set " +
            "first_name = #{firstName}, " +
            "last_name = #{lastName} " +
            "where id = #{id}")
    @Options(flushCache = true)
    void update(Contact Contact);

    @Delete("delete from contact where id = #{id}")
    @Options(flushCache = true)
    void delete(Contact Contact);

    @Select("select * from contact where id = #{id}")
    Contact findOne(@Param("id") int id);

    @ResultMap("findOne-int")
    @Select("SELECT * FROM contact")
    List<Contact> findAll();

    @ResultMap("findOne-int")
    @Select("select * from contact where user_id = #{userId} limit 1")
    Contact findByUserId(@Param("userId") int userId);
}
