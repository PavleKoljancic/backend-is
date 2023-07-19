package com.app.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.app.backend.models.TicketType;
import com.app.backend.repositories.TicketRequestRepo;
import com.app.backend.repositories.TicketTypeRepo;

@Service
public class TicketTypeService {
    @Autowired
    TicketTypeRepo ticketTypeRepo;

    public List<TicketType> getTickets(PageRequest pageRequest) 
    {
        return ticketTypeRepo.findAll(pageRequest).toList();
    }
    public List<TicketType> getAvailableTickets(PageRequest pageRequest) 
    {
        return ticketTypeRepo.findByInUseTrue(pageRequest);
    }
}
