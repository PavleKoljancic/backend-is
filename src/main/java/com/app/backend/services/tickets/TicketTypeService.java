package com.app.backend.services.tickets;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.app.backend.models.tickets.Accepted;
import com.app.backend.models.tickets.AcceptedPrimaryKey;
import com.app.backend.models.tickets.AmountTicket;
import com.app.backend.models.tickets.AmountTicketWrapper;
import com.app.backend.models.tickets.PeriodicTicket;
import com.app.backend.models.tickets.PeriodicTicketWrapper;
import com.app.backend.models.tickets.TicketType;
import com.app.backend.models.tickets.TicketTypeAcceptsDocumentType;
import com.app.backend.models.tickets.TicketTypeAcceptsDocumentTypePrimaryKey;
import com.app.backend.models.tickets.TicketsStatistics;
import com.app.backend.models.tickets.TransporterTicketsStatistics;
import com.app.backend.models.transporters.Transporter;
import com.app.backend.repositories.tickets.AcceptedRepo;
import com.app.backend.repositories.tickets.AmountTicketRepo;
import com.app.backend.repositories.tickets.DocumentTypeRepo;
import com.app.backend.repositories.tickets.PeriodicTicketRepo;
import com.app.backend.repositories.tickets.TicketTypeAcceptsDocumentTypeRepo;
import com.app.backend.repositories.tickets.TicketTypeRepo;
import com.app.backend.repositories.transactions.TicketTransactionRepo;
import com.app.backend.repositories.transporters.TransportersRepo;
import com.app.backend.services.users.SupervisorService;

import jakarta.transaction.Transactional;

@Service
public class TicketTypeService {

    @Autowired
    TicketTypeRepo ticketTypeRepo;

    @Autowired
    DocumentTypeRepo documentTypeRepo;

    @Autowired
    TransportersRepo transportersRepo;

    @Autowired
    AcceptedRepo acceptedRepo;

    @Autowired
    private AmountTicketRepo amountTicketRepo;

    @Autowired
    private TicketTransactionRepo ticketTransactionRepo;

    @Autowired
    private PeriodicTicketRepo periodicTicketRepo;

    @Autowired
    private SupervisorService supervisorService;

    @Autowired
    private TicketTypeAcceptsDocumentTypeRepo ticketTypeAcceptsDocumentTypeRepo;
    
    public List<TicketType> getTickets(PageRequest pageRequest) {
        return ticketTypeRepo.findAll(pageRequest).toList();
    }

    public List<TicketType> getAvailableTickets(PageRequest pageRequest) {
        return ticketTypeRepo.findByInUseTrue(pageRequest);
    }

    @Transactional
    public boolean addTicketType(TicketType ticketType, Integer[] validForTransportersId,
            Integer[] acceptedDocumentTypesIds) {
        for (Integer id : validForTransportersId)
            if (!transportersRepo.existsById(id))
                return false;

        

        TicketType saveResult = ticketTypeRepo.save(ticketType);
        if (saveResult == null)
            return false;
        ArrayList<Accepted> acceptedList = new ArrayList<>(validForTransportersId.length);
        for (Integer id : validForTransportersId) {
            Accepted temp = new Accepted();
            temp.setId(new AcceptedPrimaryKey(id, saveResult.getId()));
            acceptedList.add(temp);
        }
        acceptedRepo.saveAll(acceptedList);

        if(acceptedDocumentTypesIds!=null)
        {
            for (Integer id : acceptedDocumentTypesIds)
            if (!documentTypeRepo.existsById(id))
                return false;
            ArrayList<TicketTypeAcceptsDocumentType> acceptedDocumentsList = new ArrayList<>(acceptedDocumentTypesIds.length);
            for(Integer documentTypeId : acceptedDocumentTypesIds)
            {   
                TicketTypeAcceptsDocumentType temp = new TicketTypeAcceptsDocumentType();
                temp.setId(new TicketTypeAcceptsDocumentTypePrimaryKey(documentTypeId,saveResult.getId()));
                acceptedDocumentsList.add(temp);
            }
            
            ticketTypeAcceptsDocumentTypeRepo.saveAll(acceptedDocumentsList);

        }


        return true;

    }

    public TicketType getTicketTypeById(Integer TicketTypeId) {
        Optional<TicketType> result = ticketTypeRepo.findById(TicketTypeId);
        if (result.isPresent())
            return result.get();
        return null;
    }

    public List<TicketType> getAvailableTicketsForTransporter(Integer transporterId) {
        List<Accepted> acceptedBy = acceptedRepo.findByIdTransporterId(transporterId);
        List<TicketType> result = ticketTypeRepo
                .findAllById(acceptedBy.stream().map(a -> a.getId().getTicketTypeId()).toList());
        return result.stream().filter(t -> t.getInUse()).toList();
    }

    public boolean ChangeIsActiveTicketTypeId(Integer TicketTypeId, boolean isActive) {

        Optional<TicketType> result = ticketTypeRepo.findById(TicketTypeId);
        if (result.isPresent() && result.get().getInUse() != isActive) {
            TicketType temp = result.get();
            temp.setInUse(isActive);
            ticketTypeRepo.save(temp);
            return true;
        }
        return false;
    }

    public TicketsStatistics getTicketsStatisticsForAdmin(Integer days){

        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime startDate = currentDateTime.minusDays(days);

        Timestamp timestamp = Timestamp.valueOf(startDate);

        TicketsStatistics ticketsStatistics = new TicketsStatistics();

        List<Transporter> transporters = transportersRepo.findAll();
        for(Transporter trans : transporters){
            TransporterTicketsStatistics tts = new TransporterTicketsStatistics();
            tts.setTransporterId(trans.getId());
            List<AmountTicketWrapper> amountTicketsMap = new ArrayList<>();
            List<PeriodicTicketWrapper> periodicTicketsMap = new ArrayList<>();

            List<AmountTicket> amountTickets = amountTicketRepo.findAmountTicketsForTransporter(trans.getId());
            if(amountTickets.size() > 0){
                for(AmountTicket amountTicket : amountTickets){
                    AmountTicketWrapper atw = new AmountTicketWrapper();
                    atw.setAmountTicket(amountTicket);
                    atw.setCount(ticketTransactionRepo.findTicketTransactionsCountByTicketTypeId(amountTicket.getId(), timestamp));
                    amountTicketsMap.add(atw);
                }
            }
            tts.setAmountTickets(amountTicketsMap);

            List<PeriodicTicket> periodicTickets = periodicTicketRepo.findPeriodicTicketsForTransporter(trans.getId());
            if(periodicTickets.size() > 0){
                for(PeriodicTicket periodicTicket : periodicTickets){
                    PeriodicTicketWrapper ptw = new PeriodicTicketWrapper();
                    ptw.setPeriodicTicket(periodicTicket);
                    ptw.setCount(ticketTransactionRepo.findTicketTransactionsCountByTicketTypeId(periodicTicket.getId(), timestamp));
                    periodicTicketsMap.add(ptw);
                }
            }
            tts.setPeriodicTickets(periodicTicketsMap);
            ticketsStatistics.getTransporterTicketsStatistics().add(tts);
        }
        return ticketsStatistics;
    }

    public TicketsStatistics getTicketsStatisticsForTransporter(Integer days, Integer supervisorId){

        Integer transporterId = supervisorService.findTransporterId(supervisorId);
        if(transporterId == null)
            return null;
        
        else{
            LocalDateTime currentDateTime = LocalDateTime.now();
            LocalDateTime startDate = currentDateTime.minusDays(days);
    
            Timestamp timestamp = Timestamp.valueOf(startDate);
    
            TicketsStatistics ticketsStatistics = new TicketsStatistics();
            
            TransporterTicketsStatistics tts = new TransporterTicketsStatistics();
            tts.setTransporterId(transporterId);
            List<AmountTicketWrapper> amountTicketsMap = new ArrayList<>();
            List<PeriodicTicketWrapper> periodicTicketsMap = new ArrayList<>();

            List<AmountTicket> amountTickets = amountTicketRepo.findAmountTicketsForTransporter(transporterId);
            if(amountTickets.size() > 0){
                for(AmountTicket amountTicket : amountTickets){
                    AmountTicketWrapper atw = new AmountTicketWrapper();
                    atw.setAmountTicket(amountTicket);
                    atw.setCount(ticketTransactionRepo.findTicketTransactionsCountByTicketTypeId(amountTicket.getId(), timestamp));
                    amountTicketsMap.add(atw);
                }
            }
            tts.setAmountTickets(amountTicketsMap);

            List<PeriodicTicket> periodicTickets = periodicTicketRepo.findPeriodicTicketsForTransporter(transporterId);
            if(periodicTickets.size() > 0){
                for(PeriodicTicket periodicTicket : periodicTickets){
                    PeriodicTicketWrapper ptw = new PeriodicTicketWrapper();
                    ptw.setPeriodicTicket(periodicTicket);
                    ptw.setCount(ticketTransactionRepo.findTicketTransactionsCountByTicketTypeId(periodicTicket.getId(), timestamp));
                    periodicTicketsMap.add(ptw);
                }
            }
            tts.setPeriodicTickets(periodicTicketsMap);
            ticketsStatistics.getTransporterTicketsStatistics().add(tts);

            return ticketsStatistics;
        }
    }
}
