package com.app.backend.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.backend.models.Transporter;
import com.app.backend.repositories.TransportersRepo;



@Service
public class TransporterService {

    @Autowired
    TransportersRepo transportersRepo;

    public Integer addTransporter(Transporter transporter) 
    {
        return transportersRepo.save(transporter).getId();
    }

}
