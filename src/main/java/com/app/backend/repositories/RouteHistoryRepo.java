package com.app.backend.repositories;

import org.springframework.data.repository.CrudRepository;

import com.app.backend.models.RouteHistory;

public interface RouteHistoryRepo extends CrudRepository<RouteHistory,Integer> {
    
}
