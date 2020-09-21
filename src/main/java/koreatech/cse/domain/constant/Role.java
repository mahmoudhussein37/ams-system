package koreatech.cse.domain.constant;

import java.util.HashMap;
import java.util.Map;

public enum Role {
    //common
    user("ROLE_USER"),
    student("ROLE_STUDENT"), //parent list
    professor("ROLE_PROFESSOR"), //student list

    superManager("ROLE_SUPER_MANAGER");




    private String code;

    Role(String code) {
        this.code = code;
    }

    private static final Map<String, Role> lookup = new HashMap<String, Role>();

    static {
        for (Role enumType : Role.values())
            lookup.put(enumType.toCode(), enumType);
    }

    public String toCode() {
        return code;
    }


    public static Role fromString(String string) {
        return lookup.get(string);
    }
}
