package koreatech.cse.repository;

import koreatech.cse.domain.Contact;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactMapper {


    @Insert("INSERT INTO `contact`("+
            "`user_id`,"+
            "`first_name`,"+
            "`last_name`,"+
            "`cell_phone`,"+
            "`phone`,"+
            "`post_code`,"+
            "`address`,"+
            "`parent_name`,"+
            "`relation`,"+
            "`parent_cell_phone`,"+
            "`parent_phone`,"+
            "`parent_post_code`,"+
            "`parent_address`,"+
            "`admission_year`,"+
            "`admission_date`,"+
            "`high_school`,"+
            "`h_grad_year`,"+
            "`grad_year`,"+
            "`grad_semester`,"+
            "`grad_date`,"+
            "`grad_degree`,"+
            "`degree_number`,"+
            "`cert_number`"+
            ")VALUES("+
            "#{userId},"+
            "#{firstName},"+
            "#{lastName},"+
            "#{cellPhone},"+
            "#{phone},"+
            "#{postCode},"+
            "#{address},"+
            "#{parentName},"+
            "#{relation},"+
            "#{parentCellPhone},"+
            "#{parentPhone},"+
            "#{parentPostCode},"+
            "#{parentAddress},"+
            "#{admissionYear},"+
            "#{admissionDate},"+
            "#{highSchool},"+
            "#{hGradYear},"+
            "#{gradYear},"+
            "#{gradSemester},"+
            "#{gradDate},"+
            "#{gradDegree},"+
            "#{degreeNumber},"+
            "#{certNumber}"+
            ")")
    @SelectKey(statement = "select @@identity", keyProperty = "id", before = false, resultType = int.class)
    @Options(flushCache = true)
    void insert(Contact Contact);
    
    @Update("UPDATE `contact` SET"+
            "`user_id` = #{userId},"+
            "`first_name` = #{firstName},"+
            "`last_name` = #{lastName},"+
            "`cell_phone` = #{cellPhone},"+
            "`phone` = #{phone},"+
            "`post_code` = #{postCode},"+
            "`address` = #{address},"+
            "`parent_name` = #{parentName},"+
            "`relation` = #{relation},"+
            "`parent_cell_phone` = #{parentCellPhone},"+
            "`parent_phone` = #{parentPhone},"+
            "`parent_post_code` = #{parentPostCode},"+
            "`parent_address` = #{parentAddress},"+
            "`admission_year` = #{admissionYear},"+
            "`admission_date` = #{admissionDate},"+
            "`high_school` = #{highSchool},"+
            "`h_grad_year` = #{hGradYear},"+
            "`grad_year` = #{gradYear},"+
            "`grad_semester` = #{gradSemester},"+
            "`grad_date` = #{gradDate},"+
            "`grad_degree` = #{gradDegree},"+
            "`degree_number` = #{degreeNumber},"+
            "`cert_number` = #{certNumber} "+
            "WHERE `id` = #{id}")
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
