package com.bepsik.moneymotivator.controller;

import com.bepsik.moneymotivator.dto.*;
import com.bepsik.moneymotivator.service.AuthenticationService;
import com.bepsik.moneymotivator.service.UserService;
import com.bepsik.moneymotivator.service.CookieService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final CookieService cookieService;

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
        cookieService.addCookie(response, token);
    }

    @PostMapping("/sign-out")
    public void signOut(HttpServletResponse response) {
        cookieService.removeCookie(response);
    }

    @GetMapping("/profile")
    public UserAuthStateDto findByCurrentUser() {
        return userService.findByCurrentUserAuthState();
    }

}
