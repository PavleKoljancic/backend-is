package com.app.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.app.backend.models.TicketRequest;
import com.app.backend.models.TicketRequestResponse;
import com.app.backend.models.TicketType;
import com.app.backend.models.User;
import com.app.backend.repositories.TicketRequestRepo;
import com.app.backend.repositories.TicketRequestResponseRepo;
import com.app.backend.repositories.TicketTypeRepo;
import com.app.backend.repositories.UserRepo;

import jakarta.transaction.Transactional;

@Service
public class TicketRequestService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    TicketTypeRepo ticketTypeRepo;
    @Autowired
    TicketRequestRepo ticketRequestRepo;
    @Autowired 
    TicketRequestResponseRepo ticketRequestResponseRepo;
    
    @Transactional
    public String addTicketRequest(Integer ticketTypeId, Integer userId) {
        Optional<User> userOptional = userRepo.findById(userId);
        Optional<TicketType> tickTypeOptional = ticketTypeRepo.findById(ticketTypeId);
        if (!userOptional.isPresent()||!tickTypeOptional.isPresent())
            return "UserId or TicketTypeId inccorect";
        TicketType ticketType = tickTypeOptional.get();
        if(!ticketType.getInUse())
            return "Requested Ticket not inUse";
        User user = userOptional.get();
        if(user.getCredit().compareTo(ticketType.getCost())<0)
            return "Insufficient funds";
        
        Integer resultId = ticketRequestRepo.addTicketRequest(userId, ticketTypeId);
        if(ticketType.getNeedsDocumentaion())
            return "Successfully added request";
        
        TicketRequestResponse response = new TicketRequestResponse();
        response.setApproved(true);
        response.setComment("Automatska obrada");
        response.setTicketRequestId(resultId);
        
        return addTicketResponse(response);
    

    }

    @Transactional
    public String addTicketResponse(TicketRequestResponse response) 
    {

        TicketRequestResponse dbResponse =  ticketRequestResponseRepo.save(response);
        if(ticketRequestResponseRepo.processTicketResponse(dbResponse.getId()))
            return "Ticked Request Processed and Ticket bought";
        return "Ticked Request Processed and wasn't Ticket bought";
    }

    public List<TicketRequest> getTicketRequestByTransporterId(Integer TRANSPORTER_Id, PageRequest pageRequest) 
    {
        return ticketRequestRepo.getTicketRequestByTransporterId(TRANSPORTER_Id);
    }
}
