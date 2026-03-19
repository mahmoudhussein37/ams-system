package koreatech.cse.converter;


import koreatech.cse.util.SecurityHelper;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.core.convert.converter.Converter;

public class StringTrimmer implements Converter<String, String> {

    @Override
    public String convert(String s) {
        if (s != null) {

            s = s.trim();
            s = StringEscapeUtils.escapeHtml4(s);
            //s = SecurityHelper.makeSecureString(s);
            return s;
        } else {
            return s;
        }
    }
}
