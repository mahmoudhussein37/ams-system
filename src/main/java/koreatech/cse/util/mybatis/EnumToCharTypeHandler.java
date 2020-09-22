package koreatech.cse.util.mybatis;

import koreatech.cse.domain.constant.Role;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes({ Role.class})
public class EnumToCharTypeHandler<E extends Enum<E>> extends BaseTypeHandler<E> {

    private Class<E> type;

    public EnumToCharTypeHandler(final Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }

    @Override
    public void setNonNullParameter(final PreparedStatement ps, final int i, final E parameter, final JdbcType jdbcType) throws SQLException {
        try {
            Method method = type.getMethod("toCode");

            if (jdbcType == null) {
                ps.setString(i, (String) method.invoke(parameter));
            } else {
                ps.setObject(i, method.invoke(parameter), jdbcType.TYPE_CODE);
            }
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public E getNullableResult(final ResultSet rs, final String columnName) throws SQLException {
        return getEnum(rs.getString(columnName));
    }

    @Override
    public E getNullableResult(final ResultSet rs, final int columnIndex) throws SQLException {
        return getEnum(rs.getString(columnIndex));
    }

    @Override
    public E getNullableResult(final CallableStatement cs, final int columnIndex) throws SQLException {
        return getEnum(cs.getString(columnIndex));
    }

    @SuppressWarnings("unchecked")
    private E getEnum(final String value) throws SQLException {
        try {
            return (E) type.getMethod("fromString", String.class).invoke(null, value);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new SQLException(e);
        }
    }

}
