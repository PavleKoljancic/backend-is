package com.app.backend.services.tickets;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.app.backend.models.tickets.Accepted;
import com.app.backend.models.tickets.AcceptedPrimaryKey;
import com.app.backend.models.tickets.TicketType;
import com.app.backend.repositories.tickets.AcceptedRepo;
import com.app.backend.repositories.tickets.TicketTypeRepo;
import com.app.backend.repositories.transporters.TransportersRepo;

import jakarta.transaction.Transactional;

@Service
public class TicketTypeService {

    @Autowired
    TicketTypeRepo ticketTypeRepo;

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
        
        TicketType saveResult = ticketTypeRepo.save(ticketType);
        if(saveResult==null)
         return false;
        ArrayList<Accepted> acceptedList = new ArrayList<>(validForTransportersId.length);
        for(Integer id : validForTransportersId)
        {   Accepted temp = new Accepted();
            temp.setId(new AcceptedPrimaryKey(id,saveResult.getId()));
            acceptedList.add(temp);
        }
         acceptedRepo.saveAll(acceptedList);
         return true;

    }
    
    public TicketType getTicketTypeById(Integer TicketTypeId) 
    {
        Optional<TicketType> result = ticketTypeRepo.findById(TicketTypeId);
        if(result.isPresent())
            return result.get();
        return null;
    }

    public List<TicketType> getAvailableTicketsForTransporter(Integer transporterId) {
        List<Accepted> acceptedBy = acceptedRepo.findByIdTransporterId(transporterId);
        List<TicketType> result = ticketTypeRepo.findAllById(acceptedBy.stream().map(a->a.getId().getTicketTypeId()).toList());
         return result.stream().filter(t -> t.getInUse()).toList();
    }

    public boolean ChangeIsActiveTicketTypeId(Integer TicketTypeId, boolean isActive){

        Optional<TicketType> result = ticketTypeRepo.findById(TicketTypeId);
        if(result.isPresent() && result.get().getInUse() != isActive)
        {
            TicketType temp = result.get();
            temp.setInUse(isActive);
            ticketTypeRepo.save(temp);
            return true;
        }
        return false;
    }
}