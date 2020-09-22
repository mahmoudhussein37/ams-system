package koreatech.cse.util;


import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class UrlHelper {

    public static String getNameIdFromUrl(String prefix, String url) {
        int fromIndex = url.indexOf(prefix) + prefix.length();
        String nameId;
        if (url.contains("?")) {
            nameId = url.substring(fromIndex, url.indexOf("?"));
        } else {
            nameId = url.substring(fromIndex);
            if (nameId.endsWith("/")) {
                nameId = nameId.substring(0, nameId.length() - 1);
            }
        }
        return nameId;
    }

    public static ModelAndView getRedirectView(ModelAndView mav, String redirectViewName) {
        RedirectView rv = new RedirectView(redirectViewName);
        rv.setExposeModelAttributes(false);
        mav.setView(rv);
        return mav;
    }

    public static String urlEncodeUTF8(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static String urlEncodeUTF8(Map<?,?> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?,?> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s",
                    urlEncodeUTF8(entry.getKey().toString()),
                    urlEncodeUTF8(entry.getValue().toString())
            ));
        }
        return sb.toString();
    }
}
