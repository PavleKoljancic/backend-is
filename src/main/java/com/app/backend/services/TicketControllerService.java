package com.app.backend.services;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.backend.models.TicketController;
import com.app.backend.repositories.TicketControllerRepo;

@Service
public class TicketControllerService implements UserDetailsService{
    
    @Autowired
    TicketControllerRepo ticketControllerRepo;

    @Override
    public UserDetails loadUserByUsername(String pin) throws UsernameNotFoundException {
        TicketController user = ticketControllerRepo.findByPin(pin);
        if(user.getIsActive()){
            List<String> roles = List.of("CONTROLLER");
            org.springframework.security.core.userdetails.User tmp = new org.springframework.security.core.userdetails.User(user.getPin(), user.getPassword(), mapRoleToAuhtorities(roles));
            return tmp;
        }
        else
            return null;
    }

    private Collection<GrantedAuthority> mapRoleToAuhtorities(List<String> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
    }
    
    public Integer registerController(TicketController ticketController) {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        ticketController.setPassword(passwordEncoder.encode(ticketController.getPin()));
        return ticketControllerRepo.save(ticketController).getId();
    }
}
