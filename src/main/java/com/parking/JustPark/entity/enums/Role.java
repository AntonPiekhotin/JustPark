package com.parking.JustPark.entity.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER, ADMIN, CUSTOMER;

    @Override
    public String getAuthority() {
        return name();
    }
}
