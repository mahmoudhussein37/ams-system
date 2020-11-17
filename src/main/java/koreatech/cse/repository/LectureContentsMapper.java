package koreatech.cse.repository;


import koreatech.cse.domain.role.professor.LectureContents;
import koreatech.cse.domain.role.professor.LectureFundamentals;
import koreatech.cse.domain.univ.LectureMethod;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureContentsMapper {
    @Insert("INSERT INTO `lecture_contents`("+
            "`prof_course_id`,"+
            "`course_id`,"+
            "`user_id`,"+
            "`content1`,"+
            "`content2`,"+
            "`content3`,"+
            "`content4`,"+
            "`content5`,"+
            "`content6`,"+
            "`content7`,"+
            "`content8`,"+
            "`content9`,"+
            "`content10`,"+
            "`content11`,"+
            "`content12`,"+
            "`content13`,"+
            "`content14`,"+
            "`content15`,"+
            "`note1`,"+
            "`note2`,"+
            "`note3`,"+
            "`note4`,"+
            "`note5`,"+
            "`note6`,"+
            "`note7`,"+
            "`note8`,"+
            "`note9`,"+
            "`note10`,"+
            "`note11`,"+
            "`note12`,"+
            "`note13`,"+
            "`note14`,"+
            "`note15`"+
            ")VALUES("+
            "#{profCourseId},"+
            "#{courseId},"+
            "#{userId},"+
            "#{content1},"+
            "#{content2},"+
            "#{content3},"+
            "#{content4},"+
            "#{content5},"+
            "#{content6},"+
            "#{content7},"+
            "#{content8},"+
            "#{content9},"+
            "#{content10},"+
            "#{content11},"+
            "#{content12},"+
            "#{content13},"+
            "#{content14},"+
            "#{content15},"+
            "#{note1},"+
            "#{note2},"+
            "#{note3},"+
            "#{note4},"+
            "#{note5},"+
            "#{note6},"+
            "#{note7},"+
            "#{note8},"+
            "#{note9},"+
            "#{note10},"+
            "#{note11},"+
            "#{note12},"+
            "#{note13},"+
            "#{note14},"+
            "#{note15}"+
            ")")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
    void insert(LectureContents lectureContents);

    @Select("SELECT * FROM `lecture_contents`")
    List<LectureContents> findAll();

    @Select("SELECT * FROM `lecture_contents` where id=#{id}")
    LectureContents findOne(@Param("id") int id);

    @Select("SELECT * FROM `lecture_contents` where prof_course_id=#{profCourseId} limit 1")
    LectureContents findByProfCourseId(@Param("profCourseId") int profCourseId);

    @Update("UPDATE `lecture_contents` SET"+
            "`prof_course_id` = #{profCourseId},"+
            "`course_id` = #{courseId},"+
            "`user_id` = #{userId},"+
            "`content1` = #{content1},"+
            "`content2` = #{content2},"+
            "`content3` = #{content3},"+
            "`content4` = #{content4},"+
            "`content5` = #{content5},"+
            "`content6` = #{content6},"+
            "`content7` = #{content7},"+
            "`content8` = #{content8},"+
            "`content9` = #{content9},"+
            "`content10` = #{content10},"+
            "`content11` = #{content11},"+
            "`content12` = #{content12},"+
            "`content13` = #{content13},"+
            "`content14` = #{content14},"+
            "`content15` = #{content15},"+
            "`note1` = #{note1},"+
            "`note2` = #{note2},"+
            "`note3` = #{note3},"+
            "`note4` = #{note4},"+
            "`note5` = #{note5},"+
            "`note6` = #{note6},"+
            "`note7` = #{note7},"+
            "`note8` = #{note8},"+
            "`note9` = #{note9},"+
            "`note10` = #{note10},"+
            "`note11` = #{note11},"+
            "`note12` = #{note12},"+
            "`note13` = #{note13},"+
            "`note14` = #{note14},"+
            "`note15` = #{note15} "+
            "WHERE `id` = #{id}")
    void update(LectureContents lectureContents);

    @Delete("DELETE FROM `lecture_contents` WHERE ID = #{id}")
    void delete(LectureContents lectureContents);

}
