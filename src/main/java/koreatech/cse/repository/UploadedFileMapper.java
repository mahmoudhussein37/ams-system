package koreatech.cse.repository;


import koreatech.cse.domain.UploadedFile;
import koreatech.cse.domain.constant.Designation;
import koreatech.cse.domain.univ.AltCourse;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UploadedFileMapper {
    @Insert("INSERT INTO `uploaded_file`("+
            "`designation`,"+
            "`name`,"+
            "`path`,"+
            "`uploader_id`"+
            ")VALUES("+
            "#{designation},"+
            "#{name},"+
            "#{path},"+
            "#{uploaderId}"+
            ")")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
    void insert(UploadedFile uploadedFile);


    @ResultMap("findOne-int")
    @Select("SELECT * FROM `uploaded_file` where designation = #{designation}")
    List<UploadedFile> findByDesignation(@Param("designation") Designation designation);


    @Select("SELECT * FROM `uploaded_file` where id=#{id}")
    UploadedFile findOne(@Param("id") int id);

    @Update("UPDATE `uploaded_file` SET"+
            "`designation` = #{designation},"+
            "`name` = #{name},"+
            "`path` = #{path},"+
            "`uploader_id` = #{uploaderId} "+
            "WHERE `id` = #{id}")
    void update(UploadedFile uploadedFile);

    @Delete("DELETE FROM `uploaded_file` WHERE ID = #{id}")
    void delete(UploadedFile uploadedFile);

}
