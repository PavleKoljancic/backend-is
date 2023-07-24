package com.app.backend.repositories;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import com.app.backend.models.User;



public interface UserRepo extends JpaRepository<User,Integer>  {

    List<User> findByFirstNameStartingWithAndLastNameStartingWith(String firstName,String lastName);
    List<User> findByEmailStartingWith(String email);
    User findByEmail(String email);
    @Procedure(name = "eticket.add_credit")
    void addCredit(Integer userId, BigDecimal amount, Integer supervisorId);
}
