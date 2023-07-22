package com.app.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.backend.models.Route;

public interface RouteRepo extends JpaRepository<Route,Integer>{
    
}
