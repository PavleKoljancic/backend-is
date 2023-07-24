package com.app.backend.repositories;


import org.springframework.data.repository.CrudRepository;

import com.app.backend.models.Driver;


public interface DriverRepo extends CrudRepository<Driver,Integer>{
    
    Driver findByPin(String pin);
}
