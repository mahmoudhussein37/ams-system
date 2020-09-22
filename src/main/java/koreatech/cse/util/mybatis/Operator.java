package koreatech.cse.util.mybatis;

public enum Operator {

    EQ("="), LT("<"), LT_EQ("<="), GT(">"), GT_EQ(">="), NOT("<>"), LIKE("LIKE"), LIKE_OR("LIKE"), IN("IN"), NOT_IN("NOT IN"), BETWEEN("BETWEEN"), BETWEEN_OR("BETWEEN"), IS("IS");

    private String symbol;

    Operator(String symbol) {
        this.symbol = symbol;
    }

    @Override()
    public String toString() {
        return symbol;
    }

}
