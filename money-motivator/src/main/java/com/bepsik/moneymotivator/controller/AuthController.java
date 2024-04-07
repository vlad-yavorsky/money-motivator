package com.bepsik.moneymotivator.controller;

import com.bepsik.moneymotivator.dto.*;
import com.bepsik.moneymotivator.service.AuthenticationService;
import com.bepsik.moneymotivator.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.bepsik.moneymotivator.security.JwtAuthenticationFilter.AUTH_TOKEN;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final int COOKIE_MAX_AGE_SECONDS = 7 * 24 * 60 * 60; // Week

    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/sign-up")
    public UserDto signUp(@RequestBody @Valid SignUpRequestDto request) {
        return authenticationService.signUp(request);
    }

    @PostMapping("/token")
    public JwtAuthenticationResponse token(@RequestBody @Valid SignInRequestDto request) {
        var token = authenticationService.generateToken(request);
        return new JwtAuthenticationResponse(token);
    }

    @PostMapping("/sign-in")
    public void signIn(@RequestBody @Valid SignInRequestDto request, HttpServletResponse response) {
        var token = authenticationService.generateToken(request);

        Cookie cookie = new Cookie(AUTH_TOKEN, token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setMaxAge(COOKIE_MAX_AGE_SECONDS);
        response.addCookie(cookie);
    }

    @PostMapping("/sign-out")
    public void signOut(HttpServletResponse response) {
        Cookie cookie = new Cookie(AUTH_TOKEN, null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    @GetMapping("/profile")
    public UserAuthStateDto findByCurrentUser() {
        return userService.findByCurrentUserAuthState();
    }

}
