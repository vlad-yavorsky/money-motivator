package com.bepsik.moneymotivator.security;

import com.bepsik.moneymotivator.dto.OAuth2UserInfo;
import com.bepsik.moneymotivator.entity.User;
import com.bepsik.moneymotivator.enumeration.AuthProvider;
import com.bepsik.moneymotivator.service.JwtService;
import com.bepsik.moneymotivator.service.UserService;
import com.bepsik.moneymotivator.util.CookieService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${app.oauth2.redirect-uri:http://localhost:4200/oauth2/redirect}")
    private String redirectUri;

    private final JwtService jwtService;
    private final UserService userService;
    private final OAuth2UserInfoExtractor oAuth2UserInfoExtractor;
    private final CookieService cookieService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        OAuth2UserInfo userInfo = oAuth2UserInfoExtractor.extractUserInfo(oAuth2User);

        String registrationId = request.getRequestURI().contains("google") ? "google" : "local";
        AuthProvider provider = AuthProvider.valueOf(registrationId.toUpperCase());

        User user = userService.processOAuthPostLogin(provider, userInfo);

        String token = jwtService.generateToken(user);

        cookieService.addCookie(response, token);

        String targetUrl = UriComponentsBuilder.fromUriString(redirectUri)
                .queryParam("token", token)
                .build().toUriString();

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}