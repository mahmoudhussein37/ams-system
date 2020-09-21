package koreatech.cse.converter;


import org.springframework.core.convert.converter.Converter;

public class StringTrimmer implements Converter<String, String> {

    public String convert(String s) {
        if (s != null) {
            return s.trim();
        } else {
            return s;
        }
    }
}
