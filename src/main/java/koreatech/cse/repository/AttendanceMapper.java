package koreatech.cse.repository;


import koreatech.cse.domain.role.student.Attendance;
import koreatech.cse.domain.univ.Major;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceMapper {
    @Insert("INSERT INTO `attendance`("+
            "`user_id`,"+
            "`course_id`,"+
            "`date`,"+
            "`period`,"+
            "`attendance`"+
            ")VALUES("+
            "#{userId},"+
            "#{courseId},"+
            "#{date},"+
            "#{period},"+
            "#{attendance}"+
            ")")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
    void insert(Attendance attendance);

}
