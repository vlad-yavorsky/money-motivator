package com.bepsik.moneymotivator.enumeration;

import org.springframework.security.core.GrantedAuthority;

public enum ProjectRole implements GrantedAuthority {
    SUPERVISOR,
    SUBORDINATE;

    @Override
    public String getAuthority() {
        return name();
    }
}
