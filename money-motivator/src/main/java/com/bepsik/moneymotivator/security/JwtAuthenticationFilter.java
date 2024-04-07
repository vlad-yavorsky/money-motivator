package com.bepsik.moneymotivator.security;

import com.bepsik.moneymotivator.service.JwtService;
import com.bepsik.moneymotivator.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String AUTH_TOKEN = "auth_token";

    public static final String BEARER_PREFIX = "Bearer ";
    public static final String HEADER_NAME = "Authorization";
    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        var authHeader = request.getHeader(HEADER_NAME);

        if (!StringUtils.isEmpty(authHeader) && StringUtils.startsWith(authHeader, BEARER_PREFIX)) {
            var jwt = authHeader.substring(BEARER_PREFIX.length());
            authenticate(request, jwt);
        } else {
            String tokenFromCookies = extractTokenFromCookies(request.getCookies());
            if (tokenFromCookies != null) {
                authenticate(request, tokenFromCookies);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String extractTokenFromCookies(Cookie[] cookies) {
        return Arrays.stream(cookies == null ? new Cookie[0] : cookies)
                .filter(cookie -> AUTH_TOKEN.equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }

    private void authenticate(HttpServletRequest request, String jwt) {
        var username = jwtService.extractUserName(jwt);
        UserDetails userDetails = userService.loadUserByUsername(username);

        if (jwtService.isTokenValid(jwt, userDetails)) {
            SecurityContext context = SecurityContextHolder.createEmptyContext();

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            context.setAuthentication(authToken);
            SecurityContextHolder.setContext(context);
        }
    }
}
