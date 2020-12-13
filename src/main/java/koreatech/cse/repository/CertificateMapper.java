package koreatech.cse.repository;


import koreatech.cse.domain.univ.Certificate;
import koreatech.cse.domain.univ.MenuAccess;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificateMapper {
    @Insert("INSERT INTO `certificate`("+
            "`user_id`,"+
            "`request_id`"+
            ")VALUES("+
            "#{userId},"+
            "#{requestId}"+
            ")")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
    void insert(Certificate certificate);


    @Select("SELECT * FROM `certificate` where id=#{id}")
    Certificate findOne(@Param("id") int id);

    @Select("SELECT * FROM `certificate` where user_id=#{userId} limit 1")
    Certificate findByUserId(@Param("userId") int userId);



}
