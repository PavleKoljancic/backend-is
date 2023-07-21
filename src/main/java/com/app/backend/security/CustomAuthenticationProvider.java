package com.app.backend.security;

import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final List<DaoAuthenticationProvider> authenticationProviders;

    public CustomAuthenticationProvider(List<DaoAuthenticationProvider> authenticationProviders) {
        this.authenticationProviders = authenticationProviders;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AuthenticationException lastException = null;

        for (DaoAuthenticationProvider provider : authenticationProviders) {
            try {
                return provider.authenticate(authentication);
            } catch (AuthenticationException e) {
                lastException = e;
            }
        }
        throw lastException;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        for (DaoAuthenticationProvider provider : authenticationProviders) {
            if (provider.supports(authentication)) {
                return true;
            }
        }
        return false;
    }
}
