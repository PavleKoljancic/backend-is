package com.app.backend.repositories;


import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.app.backend.models.Driver;


public interface DriverRepo extends JpaRepository<Driver,Integer>{
    
    Driver findByPin(String pin);
    public List<Driver> findAllByTransporterId(Integer transporterId, PageRequest pageRequest);
    public List<Driver> findAllByTransporterIdAndIsActiveTrue(Integer transporterId, PageRequest pageRequest);
    public List<Driver> findAllByTransporterIdAndIsActiveFalse(Integer transporterId, PageRequest pageRequest);
}
