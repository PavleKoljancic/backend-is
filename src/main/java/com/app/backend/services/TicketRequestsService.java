package com.app.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.backend.repositories.TicketRequestRepo;



@Service
public class TicketRequestsService {

    TicketRequestRepo ticketRequestRepo = new TicketRequestRepo();


}