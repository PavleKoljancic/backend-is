package com.app.backend.services;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.text.html.parser.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.backend.models.User;
import com.app.backend.models.UserTicket;
import com.app.backend.models.UserWithPassword;
import com.app.backend.repositories.UserRepo;
import com.app.backend.repositories.UserTicketRepo;
import com.app.backend.repositories.UserWithPasswordRepo;



@Service
public class UserService implements UserDetailsService{
    @Autowired
    UserRepo userRepo;
    
    @Autowired
    UserTicketRepo userTicketRepo;
    
    @Autowired
    UserWithPasswordRepo userWithPasswordRepo;


    public List<User> getUsers(PageRequest pageRequest ){
        return  userRepo.findAll(pageRequest).toList();
    }


    public List<User> findByFirstNameAndLastName(String firstName, String lastName) {
        return userRepo.findByFirstNameStartingWithAndLastNameStartingWith(firstName, lastName);
    }
     public List<User> findByEmail(String email) {
        return userRepo.findByEmailStartingWith(email);
    }

    public List<UserTicket> getUserTickets(User user) 
    {
     return userTicketRepo.findByUSERId(user.getId());
    }

    public Integer registerUser(UserWithPassword user) 
    {
        return userWithPasswordRepo.save(user).getId();
        
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        List<String> roles = List.of(user.getRole());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRoleToAuhtorities(roles));
    }

    private Collection<GrantedAuthority> mapRoleToAuhtorities(List<String> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
    }
}
