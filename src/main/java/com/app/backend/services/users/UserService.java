package com.app.backend.services.users;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.backend.models.tickets.UserTicket;
import com.app.backend.models.users.User;
import com.app.backend.models.users.UserWithPassword;
import com.app.backend.repositories.tickets.UserTicketRepo;
import com.app.backend.repositories.users.UserRepo;
import com.app.backend.repositories.users.UserWithPasswordRepo;



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

    public User findExactByEmail(String email) {
        return userRepo.findByEmail(email);
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

    public User getUserById(Integer Id) 
    {
        Optional<User> result = userRepo.findById(Id);
        if(result.isPresent())
            return result.get();    
        return null;
    }

    public boolean addCredit(Integer userId, BigDecimal amount, Integer supervisorId){

        try{
            userRepo.addCredit(userId, amount, supervisorId);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
}
