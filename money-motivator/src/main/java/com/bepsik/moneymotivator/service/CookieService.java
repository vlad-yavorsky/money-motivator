package com.bepsik.moneymotivator.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.bepsik.moneymotivator.security.JwtAuthenticationFilter.AUTH_TOKEN;

@Slf4j
@Service
public class CookieService {

    private static final int COOKIE_MAX_AGE_SECONDS = 7 * 24 * 60 * 60; // Week

    public void addCookie(HttpServletResponse response, String token) {
        log.info("Add cookie");
        Cookie cookie = new Cookie(AUTH_TOKEN, token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setMaxAge(COOKIE_MAX_AGE_SECONDS);
        response.addCookie(cookie);
    }

    public void removeCookie(HttpServletResponse response) {
        log.info("Remove cookie");
        Cookie cookie = new Cookie(AUTH_TOKEN, null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

}
