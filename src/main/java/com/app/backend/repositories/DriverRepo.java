package com.app.backend.repositories;


import org.springframework.data.repository.CrudRepository;

import com.app.backend.models.Driver;
import com.app.backend.models.PinUser;
import java.util.List;


public interface DriverRepo extends CrudRepository<Driver,Integer>{
    
    Driver findByPin(String pin);
}
