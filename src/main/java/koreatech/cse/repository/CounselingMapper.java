package koreatech.cse.repository;


import koreatech.cse.domain.Searchable;
import koreatech.cse.domain.User;
import koreatech.cse.domain.role.professor.Counseling;
import koreatech.cse.domain.univ.Course;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CounselingMapper {
    @Insert("INSERT INTO `counseling`("+
            "`student_user_id`,"+
            "`prof_user_id`,"+
            "`number`,"+
            "`place`,"+
            "`contents`,"+
            "`suggestions`,"+
            "`year`,"+
            "`date`"+
            ")VALUES("+
            "#{studentUserId},"+
            "#{profUserId},"+
            "#{number},"+
            "#{place},"+
            "#{contents},"+
            "#{suggestions},"+
            "#{year},"+
            "#{date}"+
            ")")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
    void insert(Counseling counseling);

    @Update("UPDATE `counseling` SET"+

            "`year` = #{year},"+
            "`contents` = #{contents},"+
            "`suggestions` = #{suggestions},"+
            "`place` = #{place},"+
            "`date` = #{date} "+
            "WHERE `id` = #{id}")
    @Options(flushCache = true)
    void update(Counseling counseling);

    @Select("SELECT * FROM `counseling`")
    List<Counseling> findAll();

    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "student_user_id", property = "studentUserId"),
            @Result(column = "prof_user_id", property = "profUserId"),
            @Result(column = "student_user_id", property = "studentUser", one = @One(select = "koreatech.cse.repository.UserMapper.findOne")),
            @Result(column = "prof_user_id", property = "profUser", one = @One(select = "koreatech.cse.repository.UserMapper.findOne")),
    })
    @Select("SELECT * FROM `counseling` where id=#{id}")
    Counseling findOne(@Param("id") int id);


    @ResultMap("findOne-int")
    //@formatter off
    @Select("<script>"
            + "SELECT * FROM counseling co join contact c on co.student_user_id = c.user_id where 1=1 "

            + "<if test='year != 0'> and co.year = #{year}</if>"
            + "<if test='name != null'> and (c.last_name LIKE CONCAT('%', #{name}, '%') or c.first_name LIKE CONCAT('%', #{name}, '%'))</if>"
            + "<if test='orderParam != null and orderDir != null'> ORDER BY ${orderParam} ${orderDir}</if>"
            + "</script>")
        //@formatter on
    List<Counseling> findByCounseling(Searchable searchable);

    @ResultMap("findOne-int")
    //@formatter:off
    @Select("<script>"
            + "SELECT * FROM counseling WHERE 1=1"
            + "<if test='ids != null and !ids.empty'> AND id IN <foreach item='item' collection='ids' open='(' separator=',' close=')'>#{item}</foreach></if>"
            + "</script>")
        //@formatter:on
    List<Counseling> findByIds(@Param("ids") List<Integer> ids);



}
