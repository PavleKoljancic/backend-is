package com.app.backend.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.app.backend.services.users.AdminWithPasswordService;
import com.app.backend.services.users.DriverService;
import com.app.backend.services.users.SupervisorWithPasswordService;
import com.app.backend.services.users.TicketControllerService;
import com.app.backend.services.users.UserWithPasswordService;

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

    @Autowired
    private DriverService driverService;

    @Autowired
    private TicketControllerService ticketControllerService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authenticationManager(authenticationManager()).cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(authEntryPoint)
        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeHttpRequests()
        .requestMatchers("/api/pinusers/login", "/api/terminals/add/activationrequest", "/api/transporters/getTransporters", "/api/users/register", 
        "/api/users/login", "/api/terminals/getTerminalBySerialNumber={SerialNumber}").permitAll()
        .requestMatchers("/api/supervisors/getBySupervisorId={Id}", "/api/tickets/getInUseTicketsForTransporter/{TransporterId}").hasAnyAuthority("SUPERVISOR")
        .requestMatchers("/api/pinusers/register/controller", "/api/pinusers/controllers/**", "/api/supervisors/**",
        "/api/terminals/admin/**", "/api/transporters/**", "/api/routesHistory/GetRouteHistoriesByTerminalId**").hasAnyAuthority("ADMIN")
        .requestMatchers("/api/pinusers/register/driver**", "/api/pinusers/drivers/**", "/api/users/addCredit**").hasAnyAuthority("SUPERVISOR")
        .requestMatchers("/api/terminals/updateTerminalId**", "/api/terminals/CloseTerminalRouteHistory", 
        "/api/routesHistory/scanInteractionTerminalId={TerminalId}&UserId={UserId}", "/api/pinusers/getDriverByPIN={PIN}").hasAnyAuthority("DRIVER")
        .requestMatchers("/api/terminals/getScanInterractions**").hasAnyAuthority("CONTROLLER")
        .requestMatchers("/api/ticketRequests/addTicketRequest**", "/api/tickets/getInUseTickets/**", "/api/ticketRequests/getTicketResponseByUserId={userId}/**", 
        "/api/ticketRequests/getTicketRequestByUserId={userId}/**").hasAnyAuthority("USER")
        .requestMatchers("/api/routesHistory/checkRouteHistory**").hasAnyAuthority("CONTROLLER", "DRIVER")
        .requestMatchers("/api/transactions/**", "/api/users/getUsers/**", "/api/users/find/**").hasAnyAuthority("ADMIN", "SUPERVISOR")
        .requestMatchers("/api/users/getUserById={Id}", "/api/users/getUserTickets").hasAnyAuthority("ADMIN", "SUPERVISOR", "USER", "CONTROLLER", "DRIVER")
        .requestMatchers("/api/user/files/**").hasAnyAuthority("SUPERVISOR", "USER", "ADMIN", "CONTROLLER", "DRIVER")
        .requestMatchers("/api/routes/getAllRoutes**").hasAnyAuthority("ADMIN", "DRIVER", "CONTROLLER")
        .requestMatchers("/api/routes/**", "/api/tickets/**").hasAnyAuthority("ADMIN")
        .requestMatchers("/api/ticketRequests/**").hasAnyAuthority("SUPERVISOR")
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
    public DaoAuthenticationProvider getDriverDaoAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(driverService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

    @Bean
    public DaoAuthenticationProvider getControllerDaoAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(ticketControllerService);
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
        authenticationProviders.add(getDriverDaoAuthProvider());
        authenticationProviders.add(getControllerDaoAuthProvider());

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
