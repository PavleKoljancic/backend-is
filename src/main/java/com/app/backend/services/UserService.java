package com.app.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.app.backend.models.User;
import com.app.backend.repositories.UserRepository;



@Service
public class UserService {
    @Autowired
    UserRepository userRepo;

    public List<User> getUsers(PageRequest pageRequest ){
        return  userRepo.findAll(pageRequest).toList();
    }


    public List<User> findByFirstNameAndLastName(String firstName, String lastName) {
        return userRepo.findByFirstNameStartingWithAndLastNameStartingWith(firstName, lastName);
    }
     public List<User> findByEmail(String email) {
        return userRepo.findByEmailStartingWith(email);
    }
}
