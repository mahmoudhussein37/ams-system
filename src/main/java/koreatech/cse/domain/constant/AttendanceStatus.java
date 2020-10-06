package koreatech.cse.domain.constant;

import java.util.EnumSet;
import java.util.Map;
import java.util.TreeMap;

public enum AttendanceStatus {
    attendance(2), lateness(1), absence(0);

    private int number;

    private static Map<Integer, AttendanceStatus> idToEnumObjectMapping = new TreeMap<>();

    static {
        for (AttendanceStatus s : EnumSet.allOf(AttendanceStatus.class)) {
            idToEnumObjectMapping.put(s.toNumber(), s);
        }
    }

    AttendanceStatus(int number) {
        this.number = number;
    }

    public static AttendanceStatus getType(int id) {
        return idToEnumObjectMapping.get(id);
    }

    public Integer toNumber() {
        return number;
    }

    public static AttendanceStatus fromNumber(Integer number) {
        return idToEnumObjectMapping.get(number);
    }
}