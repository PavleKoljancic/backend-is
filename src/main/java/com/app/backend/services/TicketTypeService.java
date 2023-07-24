package com.app.backend.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.app.backend.models.Accepted;
import com.app.backend.models.AcceptedPrimaryKey;
import com.app.backend.models.AmountTicket;
import com.app.backend.models.PeriodicTicket;
import com.app.backend.models.TicketType;
import com.app.backend.repositories.AcceptedRepo;
import com.app.backend.repositories.AmountTicketRepo;
import com.app.backend.repositories.PeriodicTicketRepo;
import com.app.backend.repositories.TicketTypeRepo;
import com.app.backend.repositories.TransportersRepo;

import jakarta.transaction.Transactional;

@Service
public class TicketTypeService {
    @Autowired
    TicketTypeRepo ticketTypeRepo;
    @Autowired
    AmountTicketRepo amountTicketRepo;
    @Autowired
    PeriodicTicketRepo periodicTicketRepo;
    @Autowired
    TransportersRepo transportersRepo; 
    @Autowired
    AcceptedRepo acceptedRepo;
    public List<TicketType> getTickets(PageRequest pageRequest) 
    {
        return ticketTypeRepo.findAll(pageRequest).toList();
    }
    public List<TicketType> getAvailableTickets(PageRequest pageRequest) 
    {
        return ticketTypeRepo.findByInUseTrue(pageRequest);
    }
    @Transactional
    public boolean addTicketType(TicketType ticketType, Integer[] validForTransportersId) 
    {
        for(Integer id : validForTransportersId)
            if(!transportersRepo.existsById(id))
                return false;
        Integer TicketTypeId =null;
        
        if(ticketType instanceof AmountTicket)
            TicketTypeId = amountTicketRepo.save((AmountTicket)ticketType).getId();
        if(ticketType instanceof PeriodicTicket)
            TicketTypeId = periodicTicketRepo.save((PeriodicTicket)ticketType).getId();
        if(TicketTypeId ==null)
         return false;
        ArrayList<Accepted> acceptedList = new ArrayList<>(validForTransportersId.length);
        for(Integer id : validForTransportersId)
        {   Accepted temp = new Accepted();
            temp.setPrimaryKey(new AcceptedPrimaryKey(id,TicketTypeId));
            acceptedList.add(temp);
        }
         acceptedRepo.saveAll(acceptedList);
         return true;

    }

}
