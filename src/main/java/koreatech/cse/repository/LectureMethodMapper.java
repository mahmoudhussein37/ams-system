package koreatech.cse.repository;


import koreatech.cse.domain.univ.LectureMethod;
import koreatech.cse.domain.univ.Semester;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureMethodMapper {
    @Insert("INSERT INTO `lecture_method` (name) VALUES (#{name})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
    void insert(LectureMethod lectureMethod);

    @Select("SELECT * FROM `lecture_method`")
    List<LectureMethod> findAll();

    @Select("SELECT * FROM `lecture_method` where enabled = 1")
    List<LectureMethod> findAllEnabled();

    @Select("SELECT * FROM `lecture_method` where id=#{id}")
    LectureMethod findOne(@Param("id") int id);

    @Update("UPDATE `lecture_method` SET"+
            "`name` = #{name},"+
            "`enabled` = #{enabled} "+
            "WHERE `id` = #{id}")
    void update(LectureMethod lectureMethod);

    @Delete("DELETE FROM `lecture_method` WHERE ID = #{id}")
    void delete(LectureMethod lectureMethod);

}
