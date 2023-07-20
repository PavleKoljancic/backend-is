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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.backend.models.User;
import com.app.backend.models.UserTicket;
import com.app.backend.models.UserWithPassword;
import com.app.backend.repositories.UserRepo;
import com.app.backend.repositories.UserTicketRepo;
import com.app.backend.repositories.UserWithPasswordRepo;



@Service
public class UserService{
    @Autowired
    UserRepo userRepo;
    
    @Autowired
    UserTicketRepo userTicketRepo;

    @Autowired
	private PasswordEncoder passwordEncoder;

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
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        return userWithPasswordRepo.save(user).getId();
        
    }
}