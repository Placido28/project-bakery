package com.placidotech.pasteleria.dto;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.placidotech.pasteleria.model.User;

public class CustomUserDetails implements UserDetails{

    private final String email;
    private final String password;
    private final boolean enabled;
    private final boolean accountNonLocked;
    private final boolean accountNonExpired;
    private final boolean credentialsNonExpired;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(User user){
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.enabled = user.isStateUser();
        this.accountNonLocked = user.isRemoved();
        this.accountNonExpired = user.isStateUser();
        this.credentialsNonExpired = true;

        // Convertimos el rol en un GrantedAuthority
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole()));

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

}
