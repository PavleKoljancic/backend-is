package com.app.backend.services.tickets;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.app.backend.models.tickets.AmountTicket;
import com.app.backend.models.tickets.PeriodicTicket;
import com.app.backend.models.tickets.TicketRequest;
import com.app.backend.models.tickets.TicketRequestResponse;
import com.app.backend.models.tickets.TicketType;
import com.app.backend.models.tickets.UserTicket;
import com.app.backend.models.transactions.TicketTransaction;
import com.app.backend.models.transactions.Transaction;
import com.app.backend.models.users.User;
import com.app.backend.repositories.tickets.TicketRequestRepo;
import com.app.backend.repositories.tickets.TicketRequestResponseRepo;
import com.app.backend.repositories.tickets.TicketTypeRepo;
import com.app.backend.repositories.tickets.UserTicketRepo;
import com.app.backend.repositories.transactions.TransactionRepo;
import com.app.backend.repositories.users.UserRepo;

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
    @Autowired
    TransactionRepo transactionRepo;
    @Autowired
    UserTicketRepo userTicketRepo;

    @Transactional
    public String addTicketRequest(Integer ticketTypeId, Integer userId, Integer documentId) {
        Optional<User> userOptional = userRepo.findById(userId);
        Optional<TicketType> tickTypeOptional = ticketTypeRepo.findById(ticketTypeId);
        if (!userOptional.isPresent() || !tickTypeOptional.isPresent())
            return "UserId or TicketTypeId incorrect";
        TicketType ticketType = tickTypeOptional.get();
        if (!ticketType.getInUse())
            return "Requested Ticket not inUse";
        User user = userOptional.get();
        if (user.getCredit().compareTo(ticketType.getCost()) < 0)
            return "Insufficient funds";
        if (ticketType.getNeedsDocumentaion() && documentId == null)
            return "Requested ticket type requires but no document provided.";

        TicketRequest resultingRequest = ticketRequestRepo
                .save(new TicketRequest(new Timestamp(System.currentTimeMillis()), userId, ticketTypeId, documentId));
        if (ticketType.getNeedsDocumentaion())
            return "Successfully added request";

        TicketRequestResponse response = new TicketRequestResponse();
        response.setApproved(true);
        response.setComment("Automatska obrada " + ticketType.getName());
        response.setTicketRequestId(resultingRequest.getId());

        return addTicketResponse(response);

    }

    @Transactional
    public String addTicketResponse(TicketRequestResponse ticketRequestResponse) {

        if (ticketRequestResponseRepo.findByTicketRequestId(ticketRequestResponse.getTicketRequestId()).size() > 0) {
            // TicketRequest nije Ranije processiran
            String responseString = null;
            TicketRequestResponse dbResponse = ticketRequestResponseRepo.save(ticketRequestResponse);
            if (ticketRequestResponse.getApproved()) {
                TicketRequest ticketRequest = ticketRequestRepo.getById(ticketRequestResponse.getTicketRequestId());

                User user = userRepo.getReferenceById(ticketRequest.getUserId());
                TicketType ticketTypeRequested = ticketTypeRepo.getReferenceById(ticketRequest.getTicketTypeId());
                if (user.getCredit()
                        .compareTo(ticketTypeRequested.getCost()) > 0)

                {   
                    TicketTransaction ticketTransaction = new TicketTransaction(ticketTypeRequested.getCost(),
                     new Timestamp(System.currentTimeMillis()), 
                     user.getId(), dbResponse.getId());
                    Transaction transactionCreated =  transactionRepo.save(ticketTransaction); 
                    user.setCredit(user.getCredit().subtract(ticketTypeRequested.getCost()));
                    userRepo.save(user);
                    
                    Integer usage=null;
                    Timestamp validUntilDate=null;
                    if(ticketTypeRequested instanceof AmountTicket)
                        usage = ((AmountTicket)ticketTypeRequested).getAmount();
                    else 
                        validUntilDate = new Timestamp(System.currentTimeMillis() +  (((PeriodicTicket)ticketTypeRequested).getValidFor())*24l*60l*60l*1000
                        );

                    UserTicket userTicket = new UserTicket(transactionCreated.getId(),user.getId(),validUntilDate,usage,ticketTypeRequested);
                    userTicketRepo.save(userTicket);
                   
                    responseString = "Ticked Request Processed and Ticket bought";

                }

                else
                    responseString = "Insufficient funds!";

            } else

                responseString = "Ticked Request Processed and wasn't Ticket bought";
            
            return responseString;
        }
        return "Ticket has already been processed.";
    }

    public List<TicketRequest> getTicketRequestByTransporterId(Integer TRANSPORTER_Id, PageRequest pageRequest) {
        return ticketRequestRepo.getTicketRequestByTransporterId(TRANSPORTER_Id, pageRequest);
    }

    public List<TicketRequest> getTicketRequestByTransporterId(Integer TRANSPORTER_Id) {

        return ticketRequestRepo.getTicketRequestByTransporterId(TRANSPORTER_Id);
    }

    public List<TicketRequestResponse> getTicketResponses(Integer supervisorId, PageRequest pageRequest) {
        return ticketRequestResponseRepo.findBySupervisorId(supervisorId, pageRequest);
    }

    public List<TicketRequestResponse> getTicketResponsesByUserId(Integer userId, PageRequest of) {
        return ticketRequestResponseRepo.findByUserId(userId, of);
    }

    public List<TicketRequest> getTicketRequestByUserId(Integer userId, PageRequest of) {
        return ticketRequestRepo.findByUserId(userId, of);
    }
}
