package koreatech.cse.repository.provider;

import koreatech.cse.domain.Searchable;
import org.apache.ibatis.jdbc.SQL;

public class UserSqlProvider {

    public String findAllByProvider(final Searchable searchable) {
        return new SQL() {
            {
                SELECT("*");
                FROM("USER");
                if(searchable.getName() != null) {
                    WHERE("NAME = #{name}");
                    if(searchable.getNumber() != null) {
                        OR();
                        WHERE("number = #{number}");
                    }
                }
                if(searchable.getOrderParam() != null && searchable.getOrderDir() != null) {

                    ORDER_BY(searchable.getOrderParam() + " " + searchable.getOrderDir());
                }
            }
        }.toString();
    }
}
