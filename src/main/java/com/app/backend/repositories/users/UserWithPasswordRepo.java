package com.app.backend.repositories.users;


import org.springframework.data.repository.CrudRepository;

import com.app.backend.models.users.UserWithPassword;



public interface UserWithPasswordRepo extends CrudRepository<UserWithPassword,Integer>  {

    UserWithPassword findByEmail(String email);

}
