package koreatech.cse.domain.constant;

import com.neovisionaries.i18n.LanguageCode;
import org.springframework.context.MessageSource;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum SupportedLanguage {

    en(LanguageCode.en),
    ar(LanguageCode.ar);
    private LanguageCode code;

    private static final Map<String, SupportedLanguage> lookup = new HashMap<>();

    static {
        for (SupportedLanguage enumType : SupportedLanguage.values()) {
            lookup.put(enumType.name(), enumType);
        }
    }

    SupportedLanguage(LanguageCode code) {
        this.code = code;
    }

    public LanguageCode toCode() {
        return code;
    }

    public static SupportedLanguage getSupportedLanguage(String name) {
        switch (name) {
            case "ar":
                return SupportedLanguage.ar;
            default:
                return SupportedLanguage.en;
        }
    }

    public String getLabel(MessageSource messageSource, Locale locale) {
        return messageSource.getMessage("language." + name(), null, locale);
    }

    public static SupportedLanguage fromString(String string) {
        return lookup.get(string);
    }
}
