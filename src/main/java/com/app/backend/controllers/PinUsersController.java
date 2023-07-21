package com.app.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.backend.models.Driver;
import com.app.backend.models.PinUser;
import com.app.backend.repositories.DriverRepo;

@RestController
@RequestMapping("/api/pinusers")
public class PinUsersController {

    // @Autowired
    // DriverRepo driverRepo;

    // @GetMapping("/brdjo")
    // public PinUser getBrdjo() {
    //     return driverRepo.findById(1).get();
    // }
}
