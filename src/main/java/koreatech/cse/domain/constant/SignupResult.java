package koreatech.cse.domain.constant;

public enum SignupResult {
    SUCCESS("success"),
    ERROR("error"),
    STUDENT_NOT_FOUND("student_not_found"),
    ALREADY_ACTIVE("already_active"),
    USERNAME_TAKEN("username_taken"),
    INVALID_INPUT("invalid_input"),
    IDENTITY_MISMATCH("identity_mismatch"),
    TOO_MANY_ATTEMPTS("too_many_attempts");

    private final String code;

    SignupResult(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
