package com.app.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.backend.models.Transporter;
import com.app.backend.services.TransporterService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/transporters")
public class TransporterController {
 
    @Autowired
    TransporterService transporterService;

    @PostMapping("addTransporter")
    public Integer postMethodName(@RequestBody Transporter transporter) {
        
        
        return transporterService.addTransporter(transporter);
    }
    
    @GetMapping("/getTransporters")
    public List<Transporter> getTransporters(){
        
        return transporterService.getTransporters();
    }
}
