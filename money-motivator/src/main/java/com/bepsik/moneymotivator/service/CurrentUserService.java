package com.bepsik.moneymotivator.service;

import com.bepsik.moneymotivator.enumeration.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    public String getEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public boolean isUserAuthenticatedAndNotAnonymous() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"));
        }
        return false;
    }

    public boolean isAdmin() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(Role.ROLE_ADMIN);
    }

}
