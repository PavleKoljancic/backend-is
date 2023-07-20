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

import com.app.backend.models.SupervisorWithPassword;
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
    UserService userService;

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
    ResponseEntity<?> loginUser(@RequestBody SupervisorWithPassword user, HttpServletRequest request) {

        AuthenticationManager authenticationManager = authenticationManagerResolver.resolve(request);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPasswordHash()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
    
 /* 
    //Ovo je improvizacija dodavanja ticket-a od strane administratora radi testiranja funkcionalnosti
    @PostMapping("/addTicket")
    public ResponseEntity<?> addTicket(@RequestBody Ticket ticket){
        return ResponseEntity.ok().body(userService.addTicket(ticket));
    }

    @GetMapping("/getTickets")
    public ResponseEntity<?> getTickets(){
        return ResponseEntity.ok().body(userService.getTickets());
    }

    //Ovo je ekvivalent registraciji user-a, ali improvizovano radi testiranja ostalih funkcionalnosti
    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody User user){
        return ResponseEntity.ok().body(userService.addUser(user));
    }

    @PostMapping("/payment/{userId}/{input}/{supervisorId}")
    public ResponseEntity<?> creditPayment(@PathVariable("userId") String userId, @PathVariable("input") double input, @PathVariable("supervisorId") String supervisorId){
        if(userService.creditPayment(userId, input, supervisorId))
            return ResponseEntity.ok().body("Success");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Input error");
    }

    @PostMapping("/approval/{approval}/{comment}/{reqId}/{supervisorId}")
    public ResponseEntity<String> approval(@PathVariable("approval") boolean approval, @PathVariable("comment") String comment, 
    @PathVariable("reqId") String reqId, @PathVariable("supervisorId") String supervisorId){
        if(userService.approval(approval, comment, reqId, supervisorId) != null)
            return ResponseEntity.ok().body("");
        return ResponseEntity.badRequest().body("");
    }*/
}
