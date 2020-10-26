package koreatech.cse.interceptor;

import koreatech.cse.domain.User;
import koreatech.cse.domain.constant.SupportedLanguage;
import koreatech.cse.util.UrlHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BasicInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getRequestURI().contains("/resources/")) {
            return true;
        }
        if (!request.getMethod().equals("GET")) {
            return true;
        }

//        String userInput = request.getQueryString();
//        if(StringUtils.isBlank(userInput)) {
//            return super.preHandle(request, response, handler);
//        }
//
//
//
//        Pattern SpecialChars = Pattern.compile("['\"\\-#()%@;=*/+]");
//        userInput = SpecialChars.matcher(userInput).replaceAll("");
//
//        /* SQL injection 처리 */
//        String regex = "(select|delete|update|insert|create|alter|drop)";
//
//        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
//        Matcher matcher = pattern.matcher(userInput);
//
//        if(matcher.find()) {
//            ModelAndView mav = new ModelAndView("common/error/sql");
//            throw new ModelAndViewDefiningException(mav);
//        }

        return super.preHandle(request, response, handler);
    }

}
