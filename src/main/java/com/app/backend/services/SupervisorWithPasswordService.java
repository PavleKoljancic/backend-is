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
import org.springframework.stereotype.Service;

import com.app.backend.models.SupervisorWithPassword;
import com.app.backend.repositories.SupervisorWithPasswordRepo;
@Service
public class SupervisorWithPasswordService implements UserDetailsService{

    @Autowired
    SupervisorWithPasswordRepo supervisorWithPasswordRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SupervisorWithPassword user = supervisorWithPasswordRepo.findByEmail(username);
        if(user.getIsActive()){
        List<String> roles = List.of("SUPERVISOR");
        org.springframework.security.core.userdetails.User tmp = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPasswordHash(), mapRoleToAuhtorities(roles));
        return tmp;
        }
        else
        return null;
    }

    private Collection<GrantedAuthority> mapRoleToAuhtorities(List<String> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
    }
}