package koreatech.cse.repository;


import koreatech.cse.domain.univ.Major;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MajorMapper {
    @Insert("INSERT INTO major (name, division_id) VALUES (#{name}, #{divisionId})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
    void insert(Major major);

    @Select("SELECT * FROM major")
    List<Major> findAll();

    @Select("SELECT * FROM major where division_id = #{divisionId}")
    List<Major> findAllByDivisionId(@Param("divisionId") int divisionId);

    @Select("SELECT * FROM major where enabled = 1")
    List<Major> findAllEnabled();

    @Select("SELECT * FROM major where enabled = 1 and division_id = #{divisionId}")
    List<Major> findAllEnabledByDivisionId(@Param("divisionId") int divisionId);

    @Select("SELECT * FROM major where id=#{id}")
    Major findOne(@Param("id") int id);



}
