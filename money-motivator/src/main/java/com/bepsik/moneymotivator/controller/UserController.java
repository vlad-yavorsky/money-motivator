package com.bepsik.moneymotivator.controller;

import com.bepsik.moneymotivator.dto.BalanceHistoryDto;
import com.bepsik.moneymotivator.dto.UserDto;
import com.bepsik.moneymotivator.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserDto findById(@PathVariable Long id) {
        log.info("Request to find user by id {}", id);
        return userService.findById(id);
    }

    @GetMapping("/withdraw-funds")
    public void withdrawFunds() {
        log.info("Request to withdraw user funds");
        userService.withdrawFunds();
    }

    @GetMapping("/balance/history")
    public List<BalanceHistoryDto> getBalance() {
        log.info("Request to get user balance");
        return userService.getBalanceHistory();
    }

}
