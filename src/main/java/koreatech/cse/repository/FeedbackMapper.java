package koreatech.cse.repository;


import koreatech.cse.domain.Feedback;
import koreatech.cse.domain.univ.AltCourse;
import koreatech.cse.domain.univ.MenuAccess;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackMapper {
    @Insert("INSERT INTO `feedback` (number, name, contents, email, datetime) VALUES (#{number}, #{name}, #{contents}, #{email}, CURRENT_TIMESTAMP)")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
    void insert(Feedback feedback);


    @Select("SELECT * FROM `feedback` order by datetime desc limit 30")
    List<Feedback> findRecent();

    @Select("SELECT * FROM `feedback` where id=#{id}")
    Feedback findOne(@Param("id") int id);

    @Delete("DELETE FROM `feedback` WHERE ID = #{id}")
    void delete(Feedback feedback);

}
