package com.app.backend.services;

import java.util.List;

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

    public List<Route> getRoutesByTransporterId(Integer transporterId){
        
        return routeRepo.findAllByTransporterId(transporterId);
    }
}
