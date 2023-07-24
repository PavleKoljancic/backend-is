package com.app.backend.controllers;


import java.io.StringReader;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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
import com.app.backend.services.AdminWithPasswordService;
import com.app.backend.services.SupervisorService;
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
   
    @Autowired
    private SupervisorService supervisorService;

    @Autowired
    private AdminWithPasswordService adminWithPasswordService;

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

        String token = null;

        try{
            AuthenticationManager authenticationManager = authenticationManagerResolver.resolve(request);
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            user.getPasswordHash()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String role = ((org.springframework.security.core.userdetails.User)authentication.getPrincipal()).getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()).get(0);

            Integer id = null;

            if("USER".compareTo(role) == 0)
                id = userService.findExactByEmail(user.getEmail()).getId();
            
            if("ADMIN".compareTo(role) == 0)
                id = adminWithPasswordService.findByEmail(user.getEmail()).getId();
            
            if("SUPERVISOR".compareTo(role) == 0)
                id = supervisorService.findByEmail(user.getEmail()).getId();
            
            token = jwtGenerator.generateToken(authentication, id);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @GetMapping("/getUserById={Id}")
    public ResponseEntity<?> getUser(@PathVariable("Id")Integer Id, HttpServletRequest request) 
    {   
        String bearerToken = request.getHeader("Authorization");
        
        bearerToken = bearerToken.substring(7, bearerToken.length());
        String[] chunks = bearerToken.split("\\.");

        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        Integer id = null;
        String role = null;

        try (JsonReader jsonReader = Json.createReader(new StringReader(payload))) {
    
            JsonObject jsonObject = jsonReader.readObject();

            id = jsonObject.getInt("id");
            role = jsonObject.getString(role);
        }

        if("USER".compareTo(role) == 0){
            if(id == Id)
                return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(Id));
            else
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(false);
        }
        else
            return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(Id));
    } 
    
    @GetMapping("/addCreditUserId={UserId}andAmount={Amount}andSupervisorId={SupervisorId}")
    public ResponseEntity<?> addCredit(@PathVariable("UserId") Integer UserId, @PathVariable("Amount") BigDecimal Amount, @PathVariable("SupervisorId") Integer SupervisorId,
     HttpServletRequest request){

        String bearerToken = request.getHeader("Authorization");
        
        bearerToken = bearerToken.substring(7, bearerToken.length());
        String[] chunks = bearerToken.split("\\.");

        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        Integer id = null;

        try (JsonReader jsonReader = Json.createReader(new StringReader(payload))) {
    
            JsonObject jsonObject = jsonReader.readObject();

            id = jsonObject.getInt("id");
        }

        if(id != SupervisorId)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(false);
        else
            return ResponseEntity.status(HttpStatus.OK).body(userService.addCredit(UserId, Amount, SupervisorId));
    }
}
