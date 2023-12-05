package com.app.backend.repositories.users;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.app.backend.models.users.User;



public interface UserRepo extends JpaRepository<User,Integer>  {

    List<User> findByFirstNameStartingWithAndLastNameStartingWith(String firstName,String lastName);
    List<User> findByEmailStartingWith(String email);
    User findByEmail(String email);
}
