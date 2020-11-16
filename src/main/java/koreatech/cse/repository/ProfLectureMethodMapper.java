package koreatech.cse.repository;


import koreatech.cse.domain.role.professor.LectureFundamentals;
import koreatech.cse.domain.role.professor.ProfLectureMethod;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfLectureMethodMapper {
    @Insert("INSERT INTO `prof_lecture_method`("+
            "`prof_course_id`,"+
            "`course_id`,"+
            "`user_id`,"+
            "`main_teaching1`,"+
            "`main_teaching2`,"+
            "`sub_teaching1`,"+
            "`sub_teaching2`,"+
            "`sub_teaching3`,"+
            "`sub_teaching4`,"+
            "`sub_teaching5`,"+
            "`sub_teaching6`,"+
            "`note`"+
            ")VALUES("+
            "#{profCourseId},"+
            "#{courseId},"+
            "#{userId},"+
            "#{mainTeaching1},"+
            "#{mainTeaching2},"+
            "#{subTeaching1},"+
            "#{subTeaching2},"+
            "#{subTeaching3},"+
            "#{subTeaching4},"+
            "#{subTeaching5},"+
            "#{subTeaching6},"+
            "#{note}"+
            ")")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
    void insert(ProfLectureMethod profLectureMethod);

    @Select("SELECT * FROM `prof_lecture_method` where id=#{id}")
    ProfLectureMethod findOne(@Param("id") int id);

    @Select("SELECT * FROM `prof_lecture_method` where prof_course_id=#{profCourseId} limit 1")
    ProfLectureMethod findByProfCourseId(@Param("profCourseId") int profCourseId);


    @Update("UPDATE `prof_lecture_method` SET"+
            "`prof_course_id` = #{profCourseId},"+
            "`course_id` = #{courseId},"+
            "`user_id` = #{userId},"+
            "`main_teaching1` = #{mainTeaching1},"+
            "`main_teaching2` = #{mainTeaching2},"+
            "`sub_teaching1` = #{subTeaching1},"+
            "`sub_teaching2` = #{subTeaching2},"+
            "`sub_teaching3` = #{subTeaching3},"+
            "`sub_teaching4` = #{subTeaching4},"+
            "`sub_teaching5` = #{subTeaching5},"+
            "`sub_teaching6` = #{subTeaching6},"+
            "`note` = #{note} "+
            "WHERE `id` = #{id}")
    @Options(flushCache = true)
    void update(ProfLectureMethod profLectureMethod);

}
