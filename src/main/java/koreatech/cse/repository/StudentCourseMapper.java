package koreatech.cse.repository;

import koreatech.cse.domain.Searchable;
import koreatech.cse.domain.role.professor.ProfessorCourse;
import koreatech.cse.domain.role.student.StudentCourse;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public interface StudentCourseMapper {

        @Insert("INSERT INTO `student_course`(" +
                        "`user_id`," +
                        "`course_id`," +
                        "`prof_course_id`," +
                        "`acquire`," +
                        "`score_assignment`," +
                        "`score_mid`," +
                        "`score_final`," +
                        "`score_options`," +
                        "`score_total`," +
                        "`grade`" +
                        ")VALUES(" +
                        "#{userId}," +
                        "#{courseId}," +
                        "#{profCourseId}," +
                        "#{acquire}," +
                        "#{scoreAssignment}," +
                        "#{scoreMid}," +
                        "#{scoreFinal}," +
                        "#{scoreOptions}," +
                        "#{scoreTotal}," +
                        "#{grade}" +
                        ")")
        @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
        void insert(StudentCourse studentCourse);

        @Results({
                        @Result(column = "id", property = "id"),
                        @Result(column = "user_id", property = "userId"),
                        @Result(column = "course_id", property = "courseId"),
                        @Result(column = "prof_course_id", property = "profCourseId"),
                        @Result(column = "course_id", property = "course", one = @One(select = "koreatech.cse.repository.CourseMapper.findOne")),
                        @Result(column = "prof_course_id", property = "professorCourse", one = @One(select = "koreatech.cse.repository.ProfessorCourseMapper.findOne")),
                        @Result(column = "user_id", property = "studentUser", one = @One(select = "koreatech.cse.repository.UserMapper.findOne")),
        })
        @Select("SELECT * FROM student_course where id=#{id}")
        StudentCourse findOne(@Param("id") int id);

        @Results({
                        @Result(column = "id", property = "id"),
                        @Result(column = "user_id", property = "userId"),
                        @Result(column = "course_id", property = "courseId"),
                        @Result(column = "prof_course_id", property = "profCourseId"),
                        @Result(column = "acquire", property = "acquire"),
                        @Result(column = "score_assignment", property = "scoreAssignment"),
                        @Result(column = "score_mid", property = "scoreMid"),
                        @Result(column = "score_final", property = "scoreFinal"),
                        @Result(column = "score_options", property = "scoreOptions"),
                        @Result(column = "score_total", property = "scoreTotal"),
                        @Result(column = "school_year", property = "schoolYear"),
                        @Result(column = "valid", property = "valid"),
                        @Result(column = "grade", property = "grade"),
        })
        @Select("SELECT * FROM student_course where id=#{id}")
        StudentCourse findFlatOne(@Param("id") int id);

        @Results({
                        @Result(column = "id", property = "id"),
                        @Result(column = "user_id", property = "userId"),
                        @Result(column = "course_id", property = "courseId"),
                        @Result(column = "prof_course_id", property = "profCourseId"),
                        @Result(column = "acquire", property = "acquire"),
                        @Result(column = "score_assignment", property = "scoreAssignment"),
                        @Result(column = "score_mid", property = "scoreMid"),
                        @Result(column = "score_final", property = "scoreFinal"),
                        @Result(column = "score_options", property = "scoreOptions"),
                        @Result(column = "score_total", property = "scoreTotal"),
                        @Result(column = "school_year", property = "schoolYear"),
                        @Result(column = "valid", property = "valid"),
                        @Result(column = "grade", property = "grade"),
                        @Result(column = "course_id", property = "course.id"),
                        @Result(column = "course_code", property = "course.code"),
                        @Result(column = "course_title", property = "course.title"),
                        @Result(column = "course_subj_category", property = "course.subjCategory"),
                        @Result(column = "course_credit", property = "course.credit"),
                        @Result(column = "prof_course_id", property = "professorCourse.id"),
                        @Result(column = "professor_divide", property = "professorCourse.divide"),
                        @Result(column = "semester_id", property = "professorCourse.semesterId"),
                        @Result(column = "professor_user_id", property = "professorCourse.professorUser.id"),
                        @Result(column = "professor_first_name", property = "professorCourse.professorUser.contact.firstName"),
                        @Result(column = "professor_last_name", property = "professorCourse.professorUser.contact.lastName"),
        })
        @Select("SELECT sc.id, sc.user_id, sc.course_id, sc.prof_course_id, sc.acquire, " +
                        "sc.score_assignment, sc.score_mid, sc.score_final, sc.score_options, sc.score_total, " +
                        "sc.school_year, sc.valid, sc.grade, pc.semester_id, pc.divide AS professor_divide, " +
                        "pc.user_id AS professor_user_id, c.code AS course_code, c.title AS course_title, " +
                        "c.subj_category AS course_subj_category, c.credit AS course_credit, " +
                        "ct.first_name AS professor_first_name, ct.last_name AS professor_last_name " +
                        "FROM student_course sc " +
                        "JOIN professor_course pc ON sc.prof_course_id = pc.id " +
                        "JOIN semester s ON pc.semester_id = s.id " +
                        "JOIN course c ON sc.course_id = c.id " +
                        "LEFT JOIN `user` u ON pc.user_id = u.id " +
                        "LEFT JOIN contact ct ON u.id = ct.user_id " +
                        "WHERE sc.user_id = #{userId} AND sc.valid = 1 " +
                        "ORDER BY s.year DESC, s.semester DESC")
        List<StudentCourse> findInquiryByUserIdValid(@Param("userId") int userId);

        @ResultMap("findInquiryByUserIdValid-int")
        @Select("SELECT sc.id, sc.user_id, sc.course_id, sc.prof_course_id, sc.acquire, " +
                        "sc.score_assignment, sc.score_mid, sc.score_final, sc.score_options, sc.score_total, " +
                        "sc.school_year, sc.valid, sc.grade, pc.semester_id, pc.divide AS professor_divide, " +
                        "pc.user_id AS professor_user_id, c.code AS course_code, c.title AS course_title, " +
                        "c.subj_category AS course_subj_category, c.credit AS course_credit, " +
                        "ct.first_name AS professor_first_name, ct.last_name AS professor_last_name " +
                        "FROM student_course sc " +
                        "JOIN professor_course pc ON sc.prof_course_id = pc.id " +
                        "JOIN semester s ON pc.semester_id = s.id " +
                        "JOIN course c ON sc.course_id = c.id " +
                        "LEFT JOIN `user` u ON pc.user_id = u.id " +
                        "LEFT JOIN contact ct ON u.id = ct.user_id " +
                        "WHERE sc.user_id = #{userId} AND sc.valid = 1 AND s.id = #{semesterId} " +
                        "ORDER BY s.year DESC, s.semester DESC")
        List<StudentCourse> findInquiryByUserIdSemesterIdValid(@Param("userId") int userId,
                        @Param("semesterId") int semesterId);

        @ResultMap("findOne-int")
        @Select("SELECT * FROM student_course where user_id = #{userId}")
        List<StudentCourse> findByUserId(@Param("userId") int userId);

        @ResultMap("findOne-int")
        @Select("SELECT * FROM student_course sc join professor_course pc on sc.prof_course_id = pc.id join semester s on pc.semester_id = s.id where sc.user_id = #{userId} and sc.valid = 1 order by s.year desc, s.semester desc")
        List<StudentCourse> findByUserIdValid(@Param("userId") int userId);

        @ResultMap("findOne-int")
        // @formatter off
        @Select("<script>"
                        + "SELECT * FROM student_course sc join professor_course pc on sc.prof_course_id=pc.id join semester s on pc.semester_id = s.id where sc.user_id = #{userId} "
                        + "<if test='year != 0'> and s.year = #{year}</if>"
                        + "<if test='semester != 0'> and s.semester = #{semester}</if>"
                        + "<if test='orderParam != null and orderDir != null'> ORDER BY ${orderParam} ${orderDir}</if>"
                        + "</script>")
        // @formatter on
        List<StudentCourse> findByUserIdYearSemester(Searchable searchable);

        @Results({
                        @Result(column = "student_course_id", property = "id"),
                        @Result(column = "student_user_id", property = "userId"),
                        @Result(column = "student_course_fk", property = "courseId"),
                        @Result(column = "student_prof_course_fk", property = "profCourseId"),
                        @Result(column = "acquire", property = "acquire"),
                        @Result(column = "score_assignment", property = "scoreAssignment"),
                        @Result(column = "score_mid", property = "scoreMid"),
                        @Result(column = "score_final", property = "scoreFinal"),
                        @Result(column = "score_options", property = "scoreOptions"),
                        @Result(column = "score_total", property = "scoreTotal"),
                        @Result(column = "school_year", property = "schoolYear"),
                        @Result(column = "valid", property = "valid"),
                        @Result(column = "grade", property = "grade"),
                        @Result(column = "course_id", property = "course.id"),
                        @Result(column = "course_code", property = "course.code"),
                        @Result(column = "course_title", property = "course.title"),
                        @Result(column = "course_subj_category", property = "course.subjCategory"),
                        @Result(column = "course_enabled", property = "course.enabled"),
                        @Result(column = "course_division_id", property = "course.division.id"),
                        @Result(column = "course_division_name", property = "course.division.name"),
                        @Result(column = "professor_course_id", property = "professorCourse.id"),
                        @Result(column = "professor_divide", property = "professorCourse.divide"),
                        @Result(column = "professor_user_id", property = "professorCourse.professorUser.id"),
                        @Result(column = "professor_first_name", property = "professorCourse.professorUser.contact.firstName"),
                        @Result(column = "professor_last_name", property = "professorCourse.professorUser.contact.lastName"),
        })
        // @formatter off
        @Select("<script>"
                        + "SELECT sc.id AS student_course_id, sc.user_id AS student_user_id, sc.course_id AS student_course_fk, "
                        + "sc.prof_course_id AS student_prof_course_fk, sc.acquire, sc.score_assignment, sc.score_mid, sc.score_final, "
                        + "sc.score_options, sc.score_total, sc.school_year, sc.valid, sc.grade, "
                        + "c.id AS course_id, c.code AS course_code, c.title AS course_title, c.subj_category AS course_subj_category, "
                        + "c.enabled AS course_enabled, c.division_id AS course_division_id, d.name AS course_division_name, "
                        + "pc.id AS professor_course_id, pc.divide AS professor_divide, pu.id AS professor_user_id, "
                        + "pct.first_name AS professor_first_name, pct.last_name AS professor_last_name "
                        + "FROM student_course sc "
                        + "JOIN professor_course pc ON sc.prof_course_id = pc.id "
                        + "JOIN semester s ON pc.semester_id = s.id "
                        + "JOIN course c ON sc.course_id = c.id "
                        + "LEFT JOIN division d ON c.division_id = d.id "
                        + "LEFT JOIN `user` pu ON pc.user_id = pu.id "
                        + "LEFT JOIN contact pct ON pu.id = pct.user_id "
                        + "WHERE sc.user_id = #{userId} "
                        + "<if test='year != 0'> AND s.year = #{year}</if> "
                        + "<if test='semester != 0'> AND s.semester = #{semester}</if> "
                        + "ORDER BY s.year DESC, s.semester DESC, c.code ASC"
                        + "</script>")
        // @formatter on
        List<StudentCourse> findClassAssessmentByUserIdYearSemester(Searchable searchable);

        @ResultMap("findOne-int")
        @Select("SELECT * FROM student_course where prof_course_id = #{profCourseId}")
        List<StudentCourse> findByProfCourseId(@Param("profCourseId") int profCourseId);

        @ResultMap("findOne-int")
        @Select("SELECT * FROM student_course where prof_course_id = #{profCourseId} and valid = 1")
        List<StudentCourse> findByProfCourseIdValid(@Param("profCourseId") int profCourseId);

        @ResultMap("findFlatOne-int")
        @Select("<script>"
                        + "SELECT sc.* FROM student_course sc "
                        + "WHERE sc.valid = 1 "
                        + "AND sc.prof_course_id IN "
                        + "<foreach item='profCourseId' collection='profCourseIds' open='(' separator=',' close=')'>"
                        + "#{profCourseId}"
                        + "</foreach>"
                        + "</script>")
        List<StudentCourse> findFlatByProfCourseIdsValid(@Param("profCourseIds") List<Integer> profCourseIds);

        @ResultMap("findOne-int")
        @Select("<script>"
                        + "SELECT * FROM student_course "
                        + "WHERE valid = 1 "
                        + "AND prof_course_id IN "
                        + "<foreach item='profCourseId' collection='profCourseIds' open='(' separator=',' close=')'>"
                        + "#{profCourseId}"
                        + "</foreach>"
                        + "</script>")
        List<StudentCourse> findByProfCourseIdsValid(@Param("profCourseIds") List<Integer> profCourseIds);

        @Select("SELECT user_id FROM student_course where prof_course_id = #{profCourseId}")
        List<Integer> findUserIdsByProfCourseId(@Param("profCourseId") int profCourseId);

        @Select("SELECT user_id FROM student_course where course_id = #{courseId}")
        List<Integer> findUserIdsByCourseId(@Param("courseId") int courseId);

        @Select("SELECT semester_id FROM student_course sc join professor_course pc on sc.prof_course_id = pc.id join semester s on pc.semester_id = s.id where sc.user_id = #{userId} and sc.valid = 1 order by s.year desc, s.semester desc")
        LinkedHashSet<Integer> findSemesterIdByUserIdValid(@Param("userId") int userId);

        @Select("<script>"
                        + "SELECT pc.semester_id AS semesterId, COUNT(*) AS cnt "
                        + "FROM student_course sc "
                        + "JOIN professor_course pc ON sc.prof_course_id = pc.id "
                        + "WHERE sc.user_id = #{userId} AND sc.valid = 1 "
                        + "AND pc.semester_id IN "
                        + "<foreach item='id' collection='semesterIds' open='(' separator=',' close=')'>"
                        + "#{id}"
                        + "</foreach>"
                        + " GROUP BY pc.semester_id"
                        + "</script>")
        List<Map<String, Object>> countValidByUserIdGroupBySemester(
                        @Param("userId") int userId, @Param("semesterIds") List<Integer> semesterIds);

        @Select("<script>"
                        + "SELECT pc.semester_id AS semesterId, COUNT(*) AS cnt "
                        + "FROM student_course sc "
                        + "JOIN professor_course pc ON sc.prof_course_id = pc.id "
                        + "WHERE sc.user_id = #{userId} "
                        + "AND pc.semester_id IN "
                        + "<foreach item='id' collection='semesterIds' open='(' separator=',' close=')'>"
                        + "#{id}"
                        + "</foreach>"
                        + " GROUP BY pc.semester_id"
                        + "</script>")
        List<Map<String, Object>> countByUserIdGroupBySemester(
                        @Param("userId") int userId, @Param("semesterIds") List<Integer> semesterIds);

        @ResultMap("findOne-int")
        @Select("SELECT * FROM student_course sc join professor_course pc on sc.prof_course_id = pc.id join semester s on pc.semester_id = s.id where sc.user_id = #{userId} and sc.valid = 1 and s.id = #{semesterId} order by s.year desc, s.semester desc")
        List<StudentCourse> findByUserIdSemesterIdValid(@Param("userId") int userId,
                        @Param("semesterId") int semesterId);

        @ResultMap("findOne-int")
        @Select("SELECT * FROM student_course sc join professor_course pc on sc.prof_course_id = pc.id join semester s on pc.semester_id = s.id where sc.user_id = #{userId} and s.id = #{semesterId} order by s.year desc, s.semester desc")
        List<StudentCourse> findByUserIdSemesterId(@Param("userId") int userId, @Param("semesterId") int semesterId);

        @ResultMap("findOne-int")
        @Select("SELECT * FROM student_course where user_id = #{userId} and prof_course_id = #{profCourseId} limit 1")
        StudentCourse findByUserIdProfCourseId(@Param("userId") int userId, @Param("profCourseId") int profCourseId);

        @Select("SELECT count(*) FROM student_course where prof_course_id = #{profCourseId}")
        Integer countByProfCourseId(@Param("profCourseId") int profCourseId);

        @Select("SELECT COUNT(*) > 0 FROM student_course WHERE user_id = #{userId} AND prof_course_id = #{profCourseId}")
        boolean existsByUserIdAndProfCourseId(@Param("userId") int userId, @Param("profCourseId") int profCourseId);

        @Update("UPDATE `student_course` SET" +
                        "`user_id` = #{userId}," +
                        "`course_id` = #{courseId}," +
                        "`prof_course_id` = #{profCourseId}," +
                        "`acquire` = #{acquire}," +
                        "`score_assignment` = #{scoreAssignment}," +
                        "`score_mid` = #{scoreMid}," +
                        "`score_final` = #{scoreFinal}," +
                        "`score_options` = #{scoreOptions}," +
                        "`score_total` = #{scoreTotal}," +
                        "`school_year` = #{schoolYear}," +
                        "`valid` = #{valid}," +
                        "`grade` = #{grade} " +
                        "WHERE `id` = #{id}")
        @Options(flushCache = true)
        void update(StudentCourse studentCourse);

        @Delete("DELETE FROM `student_course` WHERE ID = #{id}")
        void delete(StudentCourse studentCourse);

}
