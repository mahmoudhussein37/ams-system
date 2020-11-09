package koreatech.cse.util;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.Locale;

@Component
public class SystemUtil {
    public static boolean setObjectFieldValue(Object object, String fieldName, Object fieldValue) {
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                if(field.getType().isAssignableFrom(String.class) )
                    field.set(object, fieldValue);
                else if(field.getType().isAssignableFrom(Integer.TYPE) )
                    field.set(object, Integer.parseInt((String)fieldValue));
                else if(field.getType().isAssignableFrom(Double.TYPE) )
                    field.set(object, Double.parseDouble((String)fieldValue));
                else if(field.getType().isAssignableFrom(Boolean.TYPE) )
                    field.set(object, Boolean.parseBoolean((String)fieldValue));
                return true;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        return false;
    }

    public Locale getLocaleByLang(String lang) {
        switch (lang) {
            case "en":
                return Locale.ENGLISH;
            case "ar":
                return new Locale("ar");
            default:
                return Locale.ENGLISH;
        }
    }

    public static String getDocName(HttpServletRequest request, String filename) {
        String header = request.getHeader("User-Agent");
        try {
            String docName;
            if (header.contains("MSIE") || header.contains("Trident"))
                docName = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
            else if (header.contains("Chrome")) {
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < filename.length(); i++){
                    char c = filename.charAt(i);
                    if (c > '~') {
                        sb.append(URLEncoder.encode("" + c, "UTF-8"));
                    } else {
                        sb.append(c);
                    }
                }
                docName = sb.toString();
            } else
                docName = "\"" + new String(filename.getBytes("UTF-8"), "ISO-8859-1") + "\"";

            return docName;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }
}