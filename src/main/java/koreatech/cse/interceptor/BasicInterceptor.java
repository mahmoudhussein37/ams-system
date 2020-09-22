package koreatech.cse.interceptor;

import koreatech.cse.domain.User;
import koreatech.cse.domain.constant.SupportedLanguage;
import koreatech.cse.util.UrlHelper;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class BasicInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getRequestURI().contains("/resources/")) {
            return true;
        }
        if (!request.getMethod().equals("GET")) {
            return true;
        }

        if (request.getRequestURI().contains("/user/confirm")) {
            return true;
        }

        User user = User.current();
        if (user != null && user.getAuthorities().size() > 0) {

            if(!user.isConfirm()) {
                ModelAndView mav = new ModelAndView();
                mav = UrlHelper.getRedirectView(mav, "/user/confirm-needed");
                //mav.setView("/user/confirm-needed");
                throw new ModelAndViewDefiningException(mav);
            }

        }

        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //super.postHandle(request, response, handler, modelAndView);
        if(modelAndView != null) {
            HttpSession session = request.getSession();
            session.setAttribute("supportedLanguages", SupportedLanguage.values());
            String lang = (String) session.getAttribute("selectedLang");
            LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
            if (lang != null) {
                if (lang.equals("er")) {
                    Locale locales[] = Locale.getAvailableLocales();
                    for (Locale locale : locales) {
                        if(locale.getCountry().equals("EG"))
                            localeResolver.setLocale(request, response, locale);
                    }
                } else if (lang.equals("en")) {
                    localeResolver.setLocale(request, response, Locale.ENGLISH);
                } else {
                    localeResolver.setLocale(request, response, Locale.ENGLISH);
                }
                session.setAttribute("defaultLanguage", lang);
            } else {
                User user = User.current();
                Locale locale = Locale.ENGLISH;
                session.setAttribute("defaultLanguage", locale.getLanguage());
                localeResolver.setLocale(request, response, locale);
            }
        }

    }
}
