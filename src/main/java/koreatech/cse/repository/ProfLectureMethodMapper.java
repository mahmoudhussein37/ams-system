package koreatech.cse.repository;


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
            "`ref1`,"+
            "`ref2`,"+
            "`ref3`,"+
            "`ref4`,"+
            "`ref5`,"+
            "`ref6`,"+
            "`note`,"+
            "`lecture_methods`,"+
            "`lecture_method_other`,"+
            "`evaluation_methods`,"+
            "`evaluation_method_other`,"+
            "`educational_mediums`,"+
            "`educational_medium_other`,"+
            "`equipments`,"+
            "`equipment_other`"+
            ")VALUES("+
            "#{profCourseId},"+
            "#{courseId},"+
            "#{userId},"+
            "#{mainTeaching1},"+
            "#{mainTeaching2},"+
            "#{subTeaching1},"+
            "#{subTeaching2},"+
            "#{ref1},"+
            "#{ref2},"+
            "#{ref3},"+
            "#{ref4},"+
            "#{ref5},"+
            "#{ref6},"+
            "#{note},"+
            "#{lectureMethods},"+
            "#{lectureMethodOther},"+
            "#{evaluationMethods},"+
            "#{evaluationMethodOther},"+
            "#{educationalMediums},"+
            "#{educationalMediumOther},"+
            "#{equipments},"+
            "#{equipmentOther}"+
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
            "`ref1` = #{ref1},"+
            "`ref2` = #{ref2},"+
            "`ref3` = #{ref3},"+
            "`ref4` = #{ref4},"+
            "`ref5` = #{ref5},"+
            "`ref6` = #{ref6},"+
            "`note` = #{note},"+
            "`lecture_methods` = #{lectureMethods},"+
            /*"`lecture_method_checkbox` = #{lectureMethodCheckbox},"+*/
            "`lecture_method_other` = #{lectureMethodOther},"+
            "`evaluation_methods` = #{evaluationMethods},"+
            "`evaluation_method_other` = #{evaluationMethodOther},"+
            "`educational_mediums` = #{educationalMediums},"+
            "`educational_medium_other` = #{educationalMediumOther},"+
            "`equipments` = #{equipments},"+
            "`equipment_other` = #{equipmentOther} "+
            "WHERE `id` = #{id}")
    @Options(flushCache = true)
    void update(ProfLectureMethod profLectureMethod);

}
