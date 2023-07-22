package com.app.backend.controllers;


import java.math.BigDecimal;
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

import com.app.backend.models.Supervisor;
import com.app.backend.models.User;
import com.app.backend.models.UserTicket;
import com.app.backend.models.UserWithPassword;
import com.app.backend.security.JwtGenerator;
import com.app.backend.services.SupervisorService;
import com.app.backend.services.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

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
   
    @Autowired
    private SupervisorService supervisorService;

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
    
    @PostMapping("/addCreditUserId={UserId}andAmount={Amount}andSupervisorId={SupervisorId}")
    public ResponseEntity<?> addCredit(@PathVariable("UserId") Integer UserId, @PathVariable("Amount") BigDecimal Amount, @PathVariable("SupervisorId") Integer SupervisorId,
     HttpServletRequest request){

        String bearerToken = request.getHeader("Authorization");
        bearerToken = bearerToken.substring(7, bearerToken.length());
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
					JWTVerifier verifier = JWT.require(algorithm).build();
					DecodedJWT decodedJWT = verifier.verify(bearerToken);
					String username = decodedJWT.getSubject();
        Supervisor supervisor = supervisorService.findByEmail(username);

        if(supervisor.getId().compareTo(SupervisorId) != 0)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(false);
        else
            return ResponseEntity.status(HttpStatus.OK).body(userService.addCredit(UserId, Amount, SupervisorId));
    }

}
