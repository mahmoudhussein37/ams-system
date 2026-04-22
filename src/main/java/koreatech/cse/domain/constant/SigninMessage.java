package koreatech.cse.domain.constant;

import java.util.Locale;

public enum SigninMessage {
    SIGNUP_SUCCESS(SignupResult.SUCCESS.getCode(), "common.signin.success", null),
    SIGNUP_ERROR(SignupResult.ERROR.getCode(), null, SafeFailureCategory.GENERIC),
    STUDENT_NOT_FOUND(SignupResult.STUDENT_NOT_FOUND.getCode(), null, SafeFailureCategory.GENERIC),
    ALREADY_ACTIVE(SignupResult.ALREADY_ACTIVE.getCode(), null, SafeFailureCategory.GENERIC),
    USERNAME_TAKEN(SignupResult.USERNAME_TAKEN.getCode(), null, SafeFailureCategory.GENERIC),
    INVALID_INPUT(SignupResult.INVALID_INPUT.getCode(), null, SafeFailureCategory.GENERIC),
    IDENTITY_MISMATCH(SignupResult.IDENTITY_MISMATCH.getCode(), null, SafeFailureCategory.GENERIC),
    TOO_MANY_ATTEMPTS(SignupResult.TOO_MANY_ATTEMPTS.getCode(), null, SafeFailureCategory.BLOCKED),
    LOGIN_FAILED_GENERIC("fail", null, SafeFailureCategory.GENERIC),
    LOGIN_FAILED_ASSISTANCE("assist", null, SafeFailureCategory.ASSISTANCE),
    LEGACY_NUMBER("number", null, SafeFailureCategory.GENERIC),
    LEGACY_INFO("info", null, SafeFailureCategory.GENERIC),
    LEGACY_ACTIVATED("activated", null, SafeFailureCategory.GENERIC);

    private final String code;
    private final String explicitMessageKey;
    private final SafeFailureCategory safeFailureCategory;

    SigninMessage(String code, String explicitMessageKey, SafeFailureCategory safeFailureCategory) {
        this.code = code;
        this.explicitMessageKey = explicitMessageKey;
        this.safeFailureCategory = safeFailureCategory;
    }

    public String getCode() {
        return code;
    }

    public String getSafeMessageKey() {
        return safeFailureCategory == null ? explicitMessageKey : safeFailureCategory.getMessageKey();
    }

    public String getMessageKey() {
        return getSafeMessageKey();
    }

    public String getSafeHintKey() {
        return safeFailureCategory == null ? null : safeFailureCategory.getHintKey();
    }

    public static SigninMessage fromCode(String code) {
        if (code == null) {
            return null;
        }

        for (SigninMessage value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }

    public enum SafeFailureCategory {
        GENERIC("auth.failure.generic"),
        ASSISTANCE("auth.failure.assistance"),
        BLOCKED("auth.failure.blocked");

        private final String messageKey;

        SafeFailureCategory(String messageKey) {
            this.messageKey = messageKey;
        }

        public String getMessageKey() {
            return messageKey;
        }

        public String getHintKey() {
            return messageKey + ".hint";
        }

        public static SafeFailureCategory fromInternalReason(String reasonCode) {
            if (reasonCode == null) {
                return GENERIC;
            }

            String normalized = reasonCode.trim().toUpperCase(Locale.ROOT);
            switch (normalized) {
                case "BAD_CREDENTIALS":
                case "USER_NOT_FOUND":
                case "WRONG_PASSWORD":
                case "ACCOUNT_DISABLED":
                    return GENERIC;
                case "ACCOUNT_PENDING":
                    return ASSISTANCE;
                case "TOO_MANY_ATTEMPTS":
                    return BLOCKED;
                default:
                    return GENERIC;
            }
        }
    }
}
