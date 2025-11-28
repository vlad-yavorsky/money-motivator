package com.bepsik.moneymotivator.controller;

import com.bepsik.moneymotivator.dto.*;
import com.bepsik.moneymotivator.service.AuthenticationService;
import com.bepsik.moneymotivator.service.UserService;
import com.bepsik.moneymotivator.service.CookieService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final CookieService cookieService;

    @PostMapping("/sign-up")
    public UserDto signUp(@RequestBody @Valid SignUpRequestDto request) {
        log.info("Request to sign up");
        return authenticationService.signUp(request);
    }

    @PostMapping("/token")
    public JwtAuthenticationResponse token(@RequestBody @Valid SignInRequestDto request) {
        log.info("Request to generate token");
        var token = authenticationService.generateToken(request);
        return new JwtAuthenticationResponse(token);
    }

    @PostMapping("/sign-in")
    public void signIn(@RequestBody @Valid SignInRequestDto request, HttpServletResponse response) {
        log.info("Request to sign in");
        var token = authenticationService.generateToken(request);
        cookieService.addCookie(response, token);
    }

    @PostMapping("/sign-out")
    public void signOut(HttpServletResponse response) {
        log.info("Request to sign out");
        cookieService.removeCookie(response);
    }

    @GetMapping("/profile")
    public UserAuthStateDto findByCurrentUser() {
        log.info("Request to get current user profile");
        return userService.findByCurrentUserAuthState();
    }

}
