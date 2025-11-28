package com.bepsik.moneymotivator.service;

import com.bepsik.moneymotivator.dto.SignInRequestDto;
import com.bepsik.moneymotivator.dto.SignUpRequestDto;
import com.bepsik.moneymotivator.dto.UserDto;
import com.bepsik.moneymotivator.entity.User;
import com.bepsik.moneymotivator.enumeration.AuthProvider;
import com.bepsik.moneymotivator.enumeration.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UserDto signUp(SignUpRequestDto request) {
        log.info("Register new user {}", request.getEmail());
        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .roles(List.of(Role.ROLE_USER))
                .provider(AuthProvider.LOCAL)
                .balance(BigDecimal.ZERO)
                .build();

        return userService.create(user);
    }

    public String generateToken(SignInRequestDto request) {
        log.info("Generate token for user {}", request.getEmail());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        var user = userService.loadUserByUsername(request.getEmail());

        return jwtService.generateToken(user);
    }
}
