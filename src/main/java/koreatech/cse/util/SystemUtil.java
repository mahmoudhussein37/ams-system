package koreatech.cse.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;

@Component
public class SystemUtil {
    private static final Logger logger = LoggerFactory.getLogger(SystemUtil.class);
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
            logger.error("Failed to encode download filename", e);
            return "";
        }
    }
}