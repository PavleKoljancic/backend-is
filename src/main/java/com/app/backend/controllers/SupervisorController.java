package com.app.backend.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.backend.models.Supervisor;
import com.app.backend.models.SupervisorWithPassword;
import com.app.backend.services.SupervisorService;

@RestController
@RequestMapping("/api/supervisors")
public class SupervisorController {
    
    @Autowired
    SupervisorService supervisorService;

    @PostMapping("/register")
    public Integer register(@RequestBody SupervisorWithPassword supervisor){
        return supervisorService.registerSupervisor(supervisor);
    }

    @GetMapping("/getByTransporterId={transporterId}")
    public List<Supervisor> getSupervisorsByTransporterId(@PathVariable("transporterId") Integer transporterId)     
    {
        return supervisorService.getSupervisorsByTransporterId(transporterId);
    }

    @GetMapping("/getSupervisors/pagesize={pagesize}size={size}")
    public ResponseEntity<List<Supervisor>> getAllSupervisors(@PathVariable("pagesize") int page, @PathVariable("size") int size) {
        return ResponseEntity.ok().body(supervisorService.getAllSupervisors(PageRequest.of(page, size)));
    }

    @GetMapping("/getBySupervisorId={Id}")
    public Optional<Supervisor> getSupervisorsById(@PathVariable("Id") Integer Id)     
    {
        return supervisorService.getSupervisorById(Id);
    }
}
