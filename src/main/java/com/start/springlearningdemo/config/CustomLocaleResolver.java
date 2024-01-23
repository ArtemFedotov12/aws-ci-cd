package com.start.springlearningdemo.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

@Slf4j
public class CustomLocaleResolver implements LocaleResolver {

    private final Locale defaultLocale;

    public CustomLocaleResolver(Locale defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        Locale resolvedLocale = defaultLocale;
        try {
            String localeParam = request.getParameter("locale");
            String lang = request.getParameter("lang");
            Locale locale1 = new Locale(lang, localeParam);
            if (localeParam != null && !localeParam.isEmpty()) {

                Optional<Locale> requestedLocale = Arrays.stream(Locale.getAvailableLocales())
                        .filter(locale -> locale.toString().equals(locale1.toString()))
                        .findFirst();
                if (requestedLocale.isPresent()) {
                    log.info("Locale is found");
                    resolvedLocale = requestedLocale.get();
                } else {
                    log.info("Locale don't found");
                }
            }
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
        }

        return resolvedLocale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        // method is not used because the locale is determined on a per-request basis
    }

}
