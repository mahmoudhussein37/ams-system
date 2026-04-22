package koreatech.cse.repository;


import koreatech.cse.domain.Searchable;
import koreatech.cse.domain.role.professor.ProfessorCourse;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProfessorCourseMapper {

    @Insert("INSERT INTO professor_course (user_id, course_id, semester_id, limit_student, num_student, attendance, lateness, absence, divide, school_year, classroom, eng_accreditation, language, second_test) VALUES (#{userId}, #{courseId}, #{semesterId}, #{limitStudent}, #{numStudent}, #{attendance}, #{lateness}, #{absence}, #{divide}, #{schoolYear}, #{classroom}, #{engAccreditation}, #{language}, #{secondTest})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
    void insert(ProfessorCourse professorCourse);

    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "course_id", property = "courseId"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "classroom", property = "classroom"),
            @Result(column = "semester_id", property = "semesterId"),
            @Result(column = "course_id", property = "course", one = @One(select = "koreatech.cse.repository.CourseMapper.findOne")),
            @Result(column = "user_id", property = "professorUser", one = @One(select = "koreatech.cse.repository.UserMapper.findOne")),
            @Result(column = "semester_id", property = "semester", one = @One(select = "koreatech.cse.repository.SemesterMapper.findOne")),
            @Result(column = "classroom", property = "classroomObj", one = @One(select = "koreatech.cse.repository.ClassroomMapper.findOne")),
            @Result(column = "id", property = "attendanceFile", one = @One(select = "koreatech.cse.repository.UploadedFileMapper.findAttendanceFile")),
            @Result(column = "id", property = "classTimeList", many = @Many(select = "koreatech.cse.repository.ClassTimeMapper.findByProfCourseId")),
    })
    @Select("SELECT * FROM professor_course where id=#{id}")
    ProfessorCourse findOne(@Param("id") int id);

    @ResultMap("findOne-int")
    @Select("SELECT pc.* FROM professor_course pc " +
            "JOIN course c ON pc.course_id = c.id " +
            "WHERE pc.id = #{id} AND c.division_id = #{divisionId} LIMIT 1")
    ProfessorCourse findOneByIdAndDivision(@Param("id") int id, @Param("divisionId") int divisionId);

    @ResultMap("findOne-int")
    @Select("SELECT * FROM professor_course where user_id = #{userId}")
    List<ProfessorCourse> findByUserId(@Param("userId") int userId);

    @ResultMap("findOne-int")
    @Select("SELECT * FROM professor_course where course_id = #{courseId}")
    List<ProfessorCourse> findByCourseId(@Param("courseId") int courseId);

    @ResultMap("findOne-int")
    @Select("SELECT * FROM professor_course where classroom = #{classroomId}")
    List<ProfessorCourse> findByClassroomId(@Param("classroomId") int classroomId);

    @Select("SELECT count(*) FROM professor_course where course_id = #{courseId}")
    Integer countByCourseId(@Param("courseId") int courseId);

    @ResultMap("findOne-int")
    //@formatter off
    @Select("<script>"
            + "SELECT * FROM professor_course pc join course c on pc.course_id=c.id join semester s on pc.semester_id = s.id where 1=1 "
            + "<if test='year != 0'> and s.year = #{year}</if>"
            + "<if test='semester != 0'> and s.semester = #{semester}</if>"
            + "<if test='division != 0'> and c.division_id = #{division}</if>"
            + "<if test='courseId != 0'> and c.id = #{courseId}</if>"
            + "<if test='code != null'> and c.code LIKE CONCAT('%', #{code}, '%')</if>"
            + "<if test='title != null'> and c.title LIKE CONCAT('%', #{title}, '%')</if>"
            + "<if test='advisor != 0'> and pc.user_id = #{advisor}</if>"
            + "<if test='enabled == true'> and pc.enabled = 1</if>"
            + "<if test='orderParam != null and orderDir != null'> ORDER BY ${orderParam} ${orderDir}</if>"
            + "</script>")
        //@formatter on
    List<ProfessorCourse> findBy(Searchable searchable);

        // Lightweight variant for analytical views where nested relations are not required.
        //@formatter off
        @Select("<script>"
                        + "SELECT pc.* FROM professor_course pc join course c on pc.course_id=c.id join semester s on pc.semester_id = s.id where 1=1 "
                        + "<if test='year != 0'> and s.year = #{year}</if>"
                        + "<if test='semester != 0'> and s.semester = #{semester}</if>"
                        + "<if test='division != 0'> and c.division_id = #{division}</if>"
                        + "<if test='courseId != 0'> and c.id = #{courseId}</if>"
                        + "<if test='code != null'> and c.code LIKE CONCAT('%', #{code}, '%')</if>"
                        + "<if test='title != null'> and c.title LIKE CONCAT('%', #{title}, '%')</if>"
                        + "<if test='advisor != 0'> and pc.user_id = #{advisor}</if>"
                        + "<if test='enabled == true'> and pc.enabled = 1</if>"
                        + "<if test='orderParam != null and orderDir != null'> ORDER BY ${orderParam} ${orderDir}</if>"
                        + "</script>")
        //@formatter on
        List<ProfessorCourse> findByFlat(Searchable searchable);

        @Results({
                    @Result(column = "prof_course_id", property = "id"),
                    @Result(column = "prof_course_user_id", property = "userId"),
                    @Result(column = "prof_course_course_id", property = "courseId"),
                    @Result(column = "prof_course_divide", property = "divide"),
                    @Result(column = "prof_course_semester_id", property = "semesterId"),
                    @Result(column = "prof_course_limit_student", property = "limitStudent"),
                    @Result(column = "course_id", property = "course.id"),
                    @Result(column = "course_code", property = "course.code"),
                    @Result(column = "course_title", property = "course.title"),
                    @Result(column = "course_subj_category", property = "course.subjCategory"),
                    @Result(column = "course_enabled", property = "course.enabled"),
                    @Result(column = "course_division_id", property = "course.division.id"),
                    @Result(column = "course_division_name", property = "course.division.name"),
                    @Result(column = "professor_user_id", property = "professorUser.id"),
                    @Result(column = "professor_first_name", property = "professorUser.contact.firstName"),
                    @Result(column = "professor_last_name", property = "professorUser.contact.lastName"),
            })
            //@formatter off
            @Select("<script>"
                    + "SELECT pc.id AS prof_course_id, pc.user_id AS prof_course_user_id, pc.course_id AS prof_course_course_id, "
                    + "pc.divide AS prof_course_divide, pc.semester_id AS prof_course_semester_id, pc.limit_student AS prof_course_limit_student, "
                    + "c.id AS course_id, c.code AS course_code, c.title AS course_title, c.subj_category AS course_subj_category, "
                    + "c.enabled AS course_enabled, c.division_id AS course_division_id, d.name AS course_division_name, "
                    + "pu.id AS professor_user_id, pct.first_name AS professor_first_name, pct.last_name AS professor_last_name "
                    + "FROM professor_course pc "
                    + "JOIN course c ON pc.course_id = c.id "
                    + "JOIN semester s ON pc.semester_id = s.id "
                    + "LEFT JOIN division d ON c.division_id = d.id "
                    + "LEFT JOIN `user` pu ON pc.user_id = pu.id "
                    + "LEFT JOIN contact pct ON pu.id = pct.user_id "
                    + "WHERE 1=1 "
                    + "<if test='year != 0'> and s.year = #{year}</if>"
                    + "<if test='semester != 0'> and s.semester = #{semester}</if>"
                    + "<if test='division != 0'> and c.division_id = #{division}</if>"
                    + "<if test='title != null'> and c.title LIKE CONCAT('%', #{title}, '%')</if>"
                    + "<if test='enabled == true'> and pc.enabled = 1</if>"
                    + "ORDER BY s.year DESC, s.semester DESC, c.code ASC"
                    + "</script>")
            //@formatter on
            List<ProfessorCourse> findSyllabusForStudent(Searchable searchable);


    @Update("UPDATE `professor_course` SET "+
            "`user_id` = #{userId},"+
            "`course_id` = #{courseId},"+
            "`semester_id` = #{semesterId},"+
            "`limit_student` = #{limitStudent},"+
            "`num_student` = #{numStudent},"+
            "`attendance` = #{attendance},"+
            "`lateness` = #{lateness},"+
            "`enabled` = #{enabled},"+
            "`second_test` = #{secondTest},"+
            "`divide` = #{divide},"+
            "`school_year` = #{schoolYear},"+
            "`eng_accreditation` = #{engAccreditation},"+
            "`classroom` = #{classroom},"+
            "`language` = #{language},"+
            "`absence` = #{absence} "+
            "WHERE `id` = #{id}")
    @Options(flushCache = true)
    void update(ProfessorCourse professorCourse);

    @Delete("DELETE FROM `professor_course` WHERE ID = #{id}")
    void delete(ProfessorCourse professorCourse);

}
