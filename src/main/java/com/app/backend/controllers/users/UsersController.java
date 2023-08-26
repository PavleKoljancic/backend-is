package com.app.backend.controllers.users;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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

import com.app.backend.models.tickets.UserTicket;
import com.app.backend.models.users.User;
import com.app.backend.models.users.UserWithPassword;
import com.app.backend.security.JwtGenerator;
import com.app.backend.security.SecurityUtil;
import com.app.backend.services.users.AdminWithPasswordService;
import com.app.backend.services.users.SupervisorService;
import com.app.backend.services.users.UserService;

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
    public ResponseEntity<List<User>> getUsers(@PathVariable("pagesize") int page, @PathVariable("size") int size) {
        return ResponseEntity.ok().body(userService.getUsers(PageRequest.of(page, size)));
    }

    @PostMapping("/find/name")
    public List<User> findByNameAndLastName(@RequestBody User user) {
        return userService.findByFirstNameAndLastName(user.getFirstName(), user.getLastName());
    }

    @PostMapping("/find/email")
    public List<User> findByEmail(@RequestBody User user) {
        return userService.findByEmail(user.getEmail());
    }

    @PostMapping("/getUserTickets")
    public ResponseEntity<List<UserTicket>> getUserTickets(@RequestBody User user, HttpServletRequest request) {

        String role = SecurityUtil.getRoleFromAuthToken(request);
        Integer id = SecurityUtil.getIdFromAuthToken(request);

        if ("USER".compareTo(role) == 0) {
            if (id == user.getId())
                return ResponseEntity.status(HttpStatus.OK).body(userService.getUserTickets(user));
            else
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserTickets(user));

    }

    @PostMapping("/register")
    public Integer register(@RequestBody UserWithPassword user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    ResponseEntity<?> loginUser(@RequestBody UserWithPassword user, HttpServletRequest request) {

        String token = null;

        try {
            AuthenticationManager authenticationManager = authenticationManagerResolver.resolve(request);
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            user.getPasswordHash()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String role = ((org.springframework.security.core.userdetails.User) authentication.getPrincipal())
                    .getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()).get(0);

            Integer id = null;

            if ("USER".compareTo(role) == 0)
                id = userService.findExactByEmail(user.getEmail()).getId();

            if ("ADMIN".compareTo(role) == 0)
                id = adminWithPasswordService.findByEmail(user.getEmail()).getId();

            if ("SUPERVISOR".compareTo(role) == 0)
                id = supervisorService.findByEmail(user.getEmail()).getId();

            token = jwtGenerator.generateToken(authentication, id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @GetMapping("/getUserById={Id}")
    public ResponseEntity<?> getUser(@PathVariable("Id") Integer Id, HttpServletRequest request) {

        String role = SecurityUtil.getRoleFromAuthToken(request);
        Integer id = SecurityUtil.getIdFromAuthToken(request);

        if ("USER".compareTo(role) == 0) {
            if (id == Id)
                return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(Id));
            else
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(false);
        } else
            return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(Id));
    }

    @GetMapping("/addCreditUserId={UserId}andAmount={Amount}andSupervisorId={SupervisorId}")
    public ResponseEntity<?> addCredit(@PathVariable("UserId") Integer UserId,
            @PathVariable("Amount") BigDecimal Amount, @PathVariable("SupervisorId") Integer SupervisorId,
            HttpServletRequest request) {

        Integer id = SecurityUtil.getIdFromAuthToken(request);

        if (id != SupervisorId)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(false);
        else
            return ResponseEntity.status(HttpStatus.OK).body(userService.addCredit(UserId, Amount, SupervisorId));
    }

    @PostMapping("/user/edit/getUserById={Id}andemail={email}andfirstName={firstName}andlastName={lastName}andoldPasswordHash={oldPasswordHash}andnewPasswordHash={newPasswordHash}")
    public ResponseEntity<?> editUser(@PathVariable("Id") Integer userId, @PathVariable("email") String email,
            @PathVariable("firstName") String firstName,
            @PathVariable("lastName") String lastName,
            @PathVariable("oldPasswordHash") String oldPasswordHash,
            @PathVariable("newPasswordHash") String newPasswordHash,
            HttpServletRequest request) {

        String role = SecurityUtil.getRoleFromAuthToken(request);
        Integer id = SecurityUtil.getIdFromAuthToken(request);

        if ("USER".compareTo(role) == 0) {
            if (id == userId) {
                if (userService.updateUserAccount(userId, oldPasswordHash, email, firstName, lastName,
                        newPasswordHash.length() == 0 ? null : newPasswordHash)) {
                    return ResponseEntity.status(HttpStatus.OK).body(false);
                }
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(false);
    }
}