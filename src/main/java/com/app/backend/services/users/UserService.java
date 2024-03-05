package com.app.backend.services.users;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.backend.models.tickets.UserTicket;
import com.app.backend.models.transactions.CreditTransaction;
import com.app.backend.models.users.User;
import com.app.backend.models.users.UserWithPassword;
import com.app.backend.repositories.tickets.UserTicketRepo;
import com.app.backend.repositories.transactions.CreditTransactionRepo;
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

    @Autowired
    private CreditTransactionRepo creditTransactionRepo;

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

        User userToUpdate = getUserById(userId);
        if(userToUpdate != null && userToUpdate.getPictureHash() != null){
            if(amount.compareTo(new BigDecimal(0)) > 0){
                CreditTransaction creditTransaction = new CreditTransaction(amount, new Timestamp(System.currentTimeMillis()), userId, supervisorId);
                creditTransactionRepo.save(creditTransaction);

                userToUpdate.setCredit(userToUpdate.getCredit().add(amount));
                if(userRepo.save(userToUpdate).getId() == userId)
                    return true;
                else return false;
            }
        }
        else 
            return false;
        return false;
    }
}
