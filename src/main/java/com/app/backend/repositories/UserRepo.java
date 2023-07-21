package com.app.backend.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.app.backend.models.User;



public interface UserRepo extends JpaRepository<User,Integer>  {

    List<User> findByFirstNameStartingWithAndLastNameStartingWith(String firstName,String lastName);
    List<User> findByEmailStartingWith(String email);
}
