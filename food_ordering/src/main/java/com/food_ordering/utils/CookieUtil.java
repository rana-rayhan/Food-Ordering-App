package com.food_ordering.utils;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class CookieUtil {
    // Create a cookie ---------****
    public Cookie setUserCookie(String token) {
        Cookie cookie = new Cookie("access-token", token);
        cookie.setHttpOnly(true);
        // cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(86400);
        return cookie;
    }

    public String getTokenFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("access-token".equals(cookie.getName())) {
                    return "Bearer " + cookie.getValue();
                }
            }
        }
        return null;
    }
}
