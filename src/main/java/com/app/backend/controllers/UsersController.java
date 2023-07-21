package com.app.backend.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.backend.models.User;
import com.app.backend.models.UserTicket;
import com.app.backend.models.UserWithPassword;
import com.app.backend.security.JwtGenerator;
import com.app.backend.services.UserService;

import jakarta.servlet.http.HttpServletRequest;



@RestController
@RequestMapping("/api/users")
public class UsersController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private JwtGenerator jwtGenerator;

    @Autowired
    private AuthenticationManagerResolver<HttpServletRequest> authenticationManagerResolver;
   
    @GetMapping("/getUsers/pagesize={pagesize}size={size}")
    public ResponseEntity<List<User>> getUsers(@PathVariable("pagesize") int page, @PathVariable("size") int size){
        return ResponseEntity.ok().body(userService.getUsers(PageRequest.of(page, size)));
    }

    @PostMapping("/find/name")
    public List<User> findByNameAndLastName(@RequestBody User user){
        return userService.findByFirstNameAndLastName(user.getFirstName(),user.getLastName());
    }
    @PostMapping("/find/email")
    public List<User> findByEmail(@RequestBody User user){
        return userService.findByEmail(user.getEmail());
    }
    @PostMapping("/getUserTickets")
    public List<UserTicket> getUserTickets(@RequestBody User user){
        return userService.getUserTickets(user);
    }

    @PostMapping("/register")
    public Integer register(@RequestBody UserWithPassword user){
        return userService.registerUser(user);
    }


    @PostMapping("/login")
    ResponseEntity<?> loginUser(@RequestBody UserWithPassword user, HttpServletRequest request) {

        AuthenticationManager authenticationManager = authenticationManagerResolver.resolve(request);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPasswordHash()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
    @GetMapping("/getUserById={Id}")
    public User getUser(@PathVariable("Id")Integer Id) 
    {
        return userService.getUserById(Id);
    } 
    

}
