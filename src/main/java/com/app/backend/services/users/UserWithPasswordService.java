package com.app.backend.services.users;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.app.backend.models.users.UserWithPassword;
import com.app.backend.repositories.users.UserWithPasswordRepo;

@Service
public class UserWithPasswordService implements UserDetailsService {

    @Autowired
    UserWithPasswordRepo userWithPasswordRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserWithPassword user = userWithPasswordRepo.findByEmail(username);
        List<String> roles = List.of("USER");
        org.springframework.security.core.userdetails.User tmp = new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPasswordHash(), mapRoleToAuhtorities(roles));
        return tmp;
    }

    private Collection<GrantedAuthority> mapRoleToAuhtorities(List<String> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
    }

}
