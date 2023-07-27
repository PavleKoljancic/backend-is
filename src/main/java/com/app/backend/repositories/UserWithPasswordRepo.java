package com.app.backend.repositories;


import org.springframework.data.repository.CrudRepository;

import com.app.backend.models.UserWithPassword;



public interface UserWithPasswordRepo extends CrudRepository<UserWithPassword,Integer>  {

    UserWithPassword findByEmail(String email);

}
