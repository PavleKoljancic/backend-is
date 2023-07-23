package com.app.backend.controllers;

import java.util.List;

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

    @PostMapping("/ChangeisActiveSupervisorId={SupervisorId}andIsActive={isActive}")
    public boolean changeSupervisorStatus(@PathVariable("SupervisorId") Integer TerminalId,@PathVariable("isActive") Boolean isActive) 
    {
        return supervisorService.ChangeIsActiveSupervisorId(TerminalId,isActive);

    }

    @GetMapping("/getSupervisors/pagesize={pagesize}size={size}")
    public ResponseEntity<List<Supervisor>> getAllSupervisors(@PathVariable("pagesize") int page, @PathVariable("size") int size) {
        return ResponseEntity.ok().body(supervisorService.getAllSupervisors(PageRequest.of(page, size)));
    }

    @GetMapping("/getBySupervisorId={Id}")
    public Supervisor getSupervisorsById(@PathVariable("Id") Integer Id)     
    {
        return supervisorService.getSupervisorById(Id);
    }

    @GetMapping("/getByisActiveTrue/pagesize={pagesize}size={size}")
    public ResponseEntity<List<Supervisor>>getActivSupervisors(@PathVariable("pagesize") int page, @PathVariable("size") int size)
    {
        return ResponseEntity.ok().body(supervisorService.getActiveSupervisors(PageRequest.of(page, size)));
    }

    @GetMapping("/getByisActiveFalse/pagesize={pagesize}size={size}")
    public ResponseEntity<List<Supervisor>>getInactiveSupervisors(@PathVariable("pagesize") int page, @PathVariable("size") int size)
    {
        return ResponseEntity.ok().body(supervisorService.getInactiveSupervisors(PageRequest.of(page, size)));
    }

    @GetMapping("/getByTransporterSupervisorisActiveTrue={transporterId}/pagesize={pagesize}size={size}")
    public ResponseEntity<List<Supervisor>>getActiveSupervisorsByTransporterId(@PathVariable("transporterId") Integer transporterId, 
    @PathVariable("pagesize") int page, @PathVariable("size") int size)
    {
        return ResponseEntity.ok().body(supervisorService.getActiveSupervisorsByTransporter(transporterId, PageRequest.of(page, size)));
    }

    @GetMapping("/getByTransporterSupervisorisActiveFalse={transporterId}/pagesize={pagesize}size={size}")
    public ResponseEntity<List<Supervisor>>getInactiveSupervisorsByTransporterId(@PathVariable("transporterId") Integer transporterId,
    @PathVariable("pagesize") int page, @PathVariable("size") int size)
    {
        return ResponseEntity.ok().body(supervisorService.getInactiveSupervisorsByTransporter(transporterId, PageRequest.of(page,size)));
    }
}
