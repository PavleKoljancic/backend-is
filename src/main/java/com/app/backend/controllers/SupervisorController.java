package com.app.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.backend.models.SupervisorWithPassword;
import com.app.backend.models.UserWithPassword;
import com.app.backend.services.SupervisorService;

@RestController
@RequestMapping("/api/supervisor")
public class SupervisorController {
    
    @Autowired
    SupervisorService supervisorService;

    @PostMapping("/register")
    public Integer register(@RequestBody SupervisorWithPassword supervisor){
        return supervisorService.registerSupervisor(supervisor);
    }

}
