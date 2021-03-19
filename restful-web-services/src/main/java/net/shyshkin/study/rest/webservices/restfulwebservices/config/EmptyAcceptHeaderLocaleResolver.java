package net.shyshkin.study.rest.webservices.restfulwebservices.config;

import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class EmptyAcceptHeaderLocaleResolver extends AcceptHeaderLocaleResolver {

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        Locale defaultLocale = getDefaultLocale();
        String localeHeader = request.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
        if (defaultLocale != null && localeHeader != null && !StringUtils.hasText(localeHeader))
            return defaultLocale;
        return super.resolveLocale(request);
    }
}
