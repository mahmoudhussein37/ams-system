package koreatech.cse.util.mybatis;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public final class Term implements Serializable {

    private static final long serialVersionUID = -7772248471407173785L;

    private Operator operator;
    private String prefix;
    private String property;
    private Object value;

    private Term() {}

    public static Term newInstance(String property, Object value, Operator operator) {
        return newInstance("", property, value, operator);
    }

    public static Term newInstance(String prefix, String property, Object value, Operator operator) {
        Term newOne = new Term();
        newOne.prefix = prefix;
        newOne.property = property;
        newOne.value = value;
        newOne.operator = operator;
        return newOne;
    }

    public String getPrefix() {
        return prefix;
    }

    public Operator getOperator() {
        return operator;
    }

    public String getProperty() {
        return property;
    }

    public Object getValue() {
        return value;
    }

    public Term eq() {
        return newInstance(property, value, Operator.EQ);
    }

    public Term lt() {
        return newInstance(property, value, Operator.LT);
    }

    public Term ltEq() {
        return newInstance(property, value, Operator.LT_EQ);
    }

    public Term gt() {
        return newInstance(property, value, Operator.GT);
    }

    public Term gtEq() {
        return newInstance(property, value, Operator.GT_EQ);
    }

    public Term not() {
        return newInstance(property, value, Operator.NOT);
    }

    public Term like() {
        return newInstance(property, value, Operator.LIKE);
    }

    public Term in() {
        return newInstance(property, value, Operator.IN);
    }

    public Term notIn() {
        return newInstance(property, value, Operator.NOT_IN);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
