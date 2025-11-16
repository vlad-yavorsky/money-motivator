package com.bepsik.moneymotivator.service;

import com.bepsik.moneymotivator.dto.BalanceHistoryDto;
import com.bepsik.moneymotivator.dto.OAuth2UserInfo;
import com.bepsik.moneymotivator.dto.UserAuthStateDto;
import com.bepsik.moneymotivator.dto.UserDto;
import com.bepsik.moneymotivator.entity.BalanceHistory;
import com.bepsik.moneymotivator.entity.User;
import com.bepsik.moneymotivator.enumeration.AuthProvider;
import com.bepsik.moneymotivator.enumeration.BalanceOperation;
import com.bepsik.moneymotivator.enumeration.Role;
import com.bepsik.moneymotivator.exception.ConflictException;
import com.bepsik.moneymotivator.exception.NotFoundException;
import com.bepsik.moneymotivator.mapper.BalanceHistoryMapper;
import com.bepsik.moneymotivator.mapper.UserMapper;
import com.bepsik.moneymotivator.repository.BalanceHistoryRepository;
import com.bepsik.moneymotivator.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CurrentUserService currentUserService;
    private final BalanceHistoryRepository balanceHistoryRepository;
    private final BalanceHistoryMapper balanceHistoryMapper;

    public UserDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User id " + id + " not found"));
        return userMapper.toDto(user);
    }

    public UserAuthStateDto findByCurrentUserAuthState() {
        boolean isAuthenticated = currentUserService.isUserAuthenticatedAndNotAnonymous();
        UserDto userDto = isAuthenticated ? userMapper.toDto(findByCurrentUser()) : null;
        return new UserAuthStateDto(isAuthenticated, userDto);
    }

    public User findByCurrentUser() {
        return findByEmail(currentUserService.getEmail());
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Email " + email + " not found"));
    }

    @Transactional
    public UserDto create(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ConflictException("Email " + user.getEmail() + " already exist");
        }
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Email " + email + " not found"));
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void addToBalance(User assignee, BigDecimal price) {
        BalanceHistory balanceHistory = BalanceHistory.builder()
                .user(assignee)
                .operation(BalanceOperation.DEPOSIT)
                .value(price)
                .remaining(assignee.getBalance())
                .build();
        balanceHistoryRepository.save(balanceHistory);

        assignee.setBalance(assignee.getBalance().add(price));
        userRepository.save(assignee);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void withdrawFunds() {
        User user = findByCurrentUser();
        try {
            // monobankService.sendMoney(user.getCard(), user.getBalance());
            BalanceHistory balanceHistory = BalanceHistory.builder()
                    .user(user)
                    .operation(BalanceOperation.WITHDRAW)
                    .value(user.getBalance())
                    .remaining(BigDecimal.ZERO)
                    .build();
            balanceHistoryRepository.save(balanceHistory);

            user.setBalance(BigDecimal.ZERO);
            userRepository.save(user);
        } catch (Exception e) {
            log.error("Money did not sent to user " + user.getEmail());
        }
    }

    @Transactional
    public List<BalanceHistoryDto> getBalanceHistory() {
        var balanceHistory = balanceHistoryRepository.findByUserEmailOrderByDateDesc(currentUserService.getEmail());
        return balanceHistoryMapper.toDto(balanceHistory);
    }

    @Transactional
    public User processOAuthPostLogin(AuthProvider provider, OAuth2UserInfo userInfo) {
        return userRepository
                .findByProviderAndProviderId(provider, userInfo.getId())
                .orElseGet(() -> userRepository.findByEmail(userInfo.getEmail())
                        .map(existingUser -> {
                            existingUser.setProvider(provider);
                            existingUser.setProviderId(userInfo.getId());
                            return userRepository.save(existingUser);
                        })
                        .orElseGet(() -> {
                            User newUser = User.builder()
                                    .email(userInfo.getEmail())
                                    .firstName(userInfo.getFirstName())
                                    .lastName(userInfo.getLastName())
                                    .provider(provider)
                                    .providerId(userInfo.getId())
                                    .roles(List.of(Role.ROLE_USER))
                                    .balance(BigDecimal.ZERO)
                                    .build();
                            return userRepository.save(newUser);
                        }));
    }

}
