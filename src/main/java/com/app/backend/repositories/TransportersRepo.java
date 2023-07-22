package com.app.backend.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.app.backend.models.Transporter;



public interface TransportersRepo extends CrudRepository<Transporter,Integer> {

    List<Transporter> findAll();
}
