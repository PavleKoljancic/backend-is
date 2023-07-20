package com.app.backend.repositories;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.app.backend.models.User;



public interface UserRepo extends JpaRepository<User,Integer>  {

    List<User> findByFirstNameStartingWithAndLastNameStartingWith(String firstName,String lastName);
    List<User> findByEmailStartingWith(String email);
}