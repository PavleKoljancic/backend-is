package com.app.backend.controllers.transporters;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.backend.models.transporters.Transporter;
import com.app.backend.services.transporters.TransporterService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/transporters")
public class TransporterController {
 
    @Autowired
    private TransporterService transporterService;

    @PostMapping("addTransporter")
    public Integer addTransporter(@RequestBody Transporter transporter) {
        
        return transporterService.addTransporter(transporter);
    }
    
    @GetMapping("/getTransporters")
    public List<Transporter> getTransporters(){
        
        return transporterService.getTransporters();
    }
}
