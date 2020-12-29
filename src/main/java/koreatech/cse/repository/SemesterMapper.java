package koreatech.cse.repository;


import koreatech.cse.domain.univ.Semester;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SemesterMapper {
    @Insert("INSERT INTO semester (year, semester, current) VALUES (#{year}, #{semester}, #{current})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
    void insert(Semester semester);

    @Select("SELECT * FROM semester order by year desc")
    List<Semester> findAll();

    @Select("SELECT * FROM semester where id=#{id}")
    Semester findOne(@Param("id") int id);

    @Select("SELECT * FROM semester where year=#{year} and semester=#{semester} limit 1")
    Semester findByYearSemester(@Param("year") int year, @Param("semester") int semester);

    @Select("SELECT distinct year FROM semester order by year desc")
    List<Integer> findYears();

    @Update("UPDATE `semester` SET"+
            "`year` = #{year},"+
            "`current` = #{current},"+
            "`semester` = #{semester} "+
            "WHERE `id` = #{id}")
    void update(Semester semester);

    @Delete("DELETE FROM semester WHERE ID = #{id}")
    void delete(Semester semester);

}
