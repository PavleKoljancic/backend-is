package com.app.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.backend.services.TransporterService;


@RestController
@RequestMapping("/transporters")
public class TransportersController {
    
    @Autowired
    TransporterService transporterService;

    
}
