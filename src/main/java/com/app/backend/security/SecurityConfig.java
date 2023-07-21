package com.app.backend.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.app.backend.models.AdminWithPassword;
import com.app.backend.repositories.UserWithPasswordRepo;
import com.app.backend.services.AdminWithPasswordService;
import com.app.backend.services.SupervisorWithPasswordService;
import com.app.backend.services.UserService;
import com.app.backend.services.UserWithPasswordService;

import jakarta.servlet.http.HttpServletRequest;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtAuthEntryPoint authEntryPoint;

    @Autowired
    private AdminWithPasswordService adminWithPasswordService;

    @Autowired
    private SupervisorWithPasswordService supervisorWithPasswordService;

    @Autowired
    private UserWithPasswordService userWithPasswordService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authenticationManager(authenticationManager()).cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(authEntryPoint)
        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeHttpRequests()
        .requestMatchers("/api/users/login", "/api/supervisor/register", "/api/users/register", "/api/**").permitAll()
        //.requestMatchers("/api/users/**").hasAnyAuthority("USER", "SUPERVISOR")
        .requestMatchers("/api/tickets/**").hasAnyAuthority("ADMIN")
        .requestMatchers("/api/get").hasAnyAuthority("NIKO")
        .and().httpBasic();
        
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    
    @Bean
    public DaoAuthenticationProvider getSupervisorDaoAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(supervisorWithPasswordService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

    @Bean
    public DaoAuthenticationProvider getAdminDaoAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(adminWithPasswordService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

    @Bean
    public DaoAuthenticationProvider getUserDaoAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userWithPasswordService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManagerResolver<HttpServletRequest> authenticationManagerResolver(AuthenticationManager authenticationManager) {
        return request -> authenticationManager;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        List<DaoAuthenticationProvider> authenticationProviders = new ArrayList<>();
        authenticationProviders.add(getAdminDaoAuthProvider());
        authenticationProviders.add(getSupervisorDaoAuthProvider());
        authenticationProviders.add(getUserDaoAuthProvider());

        CustomAuthenticationProvider customAuthProvider = new CustomAuthenticationProvider(authenticationProviders);

        ProviderManager providerManager = new ProviderManager(List.of(customAuthProvider));
        providerManager.setEraseCredentialsAfterAuthentication(false);
        return providerManager;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JWTFilter jwtAuthenticationFilter() {
        return new JWTFilter();
    }
}
