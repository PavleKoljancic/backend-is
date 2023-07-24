package com.app.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.backend.models.Route;
import com.app.backend.repositories.RouteRepo;

@Service
public class RouteService {
    
    @Autowired
    private RouteRepo routeRepo;

    public Integer addNewRoute(Route route){

        return routeRepo.save(route).getId();
    }

    public List<Route> getAll(){

        return routeRepo.findAll();
    }

    public List<Route> getRoutesByTransporterId(Integer transporterId){
        
        return routeRepo.findAllByTransporterIdAndIsActiveTrue(transporterId);
    }

    public boolean ChangeIsActiveRouteId(Integer routeId, Boolean isActive) {
        
        Optional<Route> result = routeRepo.findById(routeId);
        if(result.isPresent() && result.get().getIsActive() != isActive) {
            Route temp = result.get();
            temp.setIsActive(isActive);
            routeRepo.save(temp);
            return true;
        }
        return false;
    }

    public Optional<Route> getById(Integer routeId){
        
        return routeRepo.findById(routeId);
    }
}
