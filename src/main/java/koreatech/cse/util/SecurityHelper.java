package koreatech.cse.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SecurityHelper {
    private final static String UNSECURED_CHAR_REGULAR_EXPRESSION = "[^\\p{Alnum}]|select|delete|update|insert|create|alter|drop";
    private static Pattern unsecuredCharPattern = Pattern.compile(UNSECURED_CHAR_REGULAR_EXPRESSION, Pattern.CASE_INSENSITIVE);

    private static SecurityHelper instance = new SecurityHelper();

    private SecurityHelper() {}

    public static SecurityHelper getInstance() {
        return instance;
    }



    public static String makeSecureString(final String str) {
        Matcher matcher = unsecuredCharPattern.matcher(str);
        return matcher.replaceAll("");
    }
}
