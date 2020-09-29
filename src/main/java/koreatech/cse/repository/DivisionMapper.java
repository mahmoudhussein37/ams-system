package koreatech.cse.repository;


import koreatech.cse.domain.univ.Division;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DivisionMapper {
    @Insert("INSERT INTO division (name) VALUES (#{name})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
    void insert(Division division);

    @Select("SELECT * FROM division")
    List<Division> findAll();

    @Select("SELECT * FROM division where enabled = 1")
    List<Division> findAllEnabled();

    @Select("SELECT * FROM division where id=#{id}")
    Division findOne(@Param("id") int id);



}
