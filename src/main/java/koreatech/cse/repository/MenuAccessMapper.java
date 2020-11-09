package koreatech.cse.repository;


import koreatech.cse.domain.univ.MenuAccess;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuAccessMapper {
    @Insert("INSERT INTO `menu_access` (grade, assessment, cqi, syllabus) VALUES (#{grade}, #{assessment}, #{cqi}, #{syllabus})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
    void insert(MenuAccess menuAccess);


    @Select("SELECT * FROM `menu_access` limit 1")
    MenuAccess findOne();

    @Update("UPDATE `menu_access` SET"+
            "`grade` = #{grade},"+
            "`cqi` = #{cqi},"+
            "`assessment` = #{assessment},"+
            "`syllabus` = #{syllabus} "+
            "WHERE `id` = #{id}")
    void update(MenuAccess menuAccess);

}
