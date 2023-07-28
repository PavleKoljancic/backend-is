package com.app.backend.repositories.transporters;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.backend.models.transporters.Route;

public interface RouteRepo extends JpaRepository<Route,Integer>{
    
    public List<Route> findAllByTransporterIdAndIsActiveTrue(Integer transporterId);
}
