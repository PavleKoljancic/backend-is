package com.app.backend.repositories.transporters;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.app.backend.models.transporters.Transporter;



public interface TransportersRepo extends CrudRepository<Transporter,Integer> {

    List<Transporter> findAll();
}
