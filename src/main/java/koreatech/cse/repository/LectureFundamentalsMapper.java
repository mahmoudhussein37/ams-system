package koreatech.cse.repository;


import koreatech.cse.domain.role.professor.LectureFundamentals;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureFundamentalsMapper {
    @Insert("INSERT INTO lecture_fundamentals (course_id, user_id, intro, achieve1, achieve2, achieve3, achieve4, achieve5, achieve6, achieve7, achieve8, achieve9, achieve10, rate_attendance, rate_assignment, rate_mid, rate_final, rate_optional) " +
            "VALUES (#{courseId}, #{userId}, #{intro}, #{achieve1}, #{achieve2}, #{achieve3}, #{achieve4}, #{achieve5}, #{achieve6}, #{achieve7}, #{achieve8}, #{achieve9}, #{achieve10}, #{rateAttendance}, #{rateAssignment}, #{rateMid}, #{rateFinal}, #{rateOptional})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
    void insert(LectureFundamentals lectureFundamentals);

}
