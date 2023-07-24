package com.app.backend.controllers;

import java.io.StringReader;
import java.util.Base64;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.backend.models.Terminal;
import com.app.backend.models.TerminalActivationRequest;
import com.app.backend.services.RouteHistoryService;
import com.app.backend.services.TerminalService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/terminals")
public class TerminalController {

    @Autowired
    TerminalService terminalService;

    @Autowired
    RouteHistoryService routeHistoryService;

    @GetMapping("/getARByTransporterdId={transporterID}")
    public List<TerminalActivationRequest> findTerminalActivationRequestByTransporterId(@PathVariable("transporterID") Integer TRANSPORTER_Id) 
    {
        return terminalService.getTerminalActivationRequestByTransporterId(TRANSPORTER_Id);
    }    

    @GetMapping("/getAllPendingAR")
    public List<TerminalActivationRequest> getAllPending() 
    {
        return terminalService.getAllPendingActivationRequests();
    }

    @PostMapping("add/activationrequest")
    public Integer  addTerminalActivationRequest(@RequestBody TerminalActivationRequest terminalActivationRequest)
    {
        return terminalService.addTerminalActivationRequest(terminalActivationRequest);
    }

    @GetMapping("/getAllAR")    
    public List<TerminalActivationRequest> getAll() 
    {
        return terminalService.getAllActivationRequests();
    }

    @GetMapping("/getAllTerminalByTransporterdId={transporterID}")
    List<Terminal> getTerminalByTransporterId(@PathVariable("transporterID")Integer TransporterId) 
    {
        return terminalService.getTerminalByTransporterId(TransporterId);
    }
    @GetMapping("/getTerminalNotInUseByTransporterdId={transporterID}")
    List<Terminal> getNotInUSeByTransporterId(@PathVariable("transporterID")Integer TransporterId) 
    {
        return terminalService.getNotInUSeByTransporterId(TransporterId);
    }
    @GetMapping("/getTerminalInUseByTransporterdId={transporterID}")
    List<Terminal> getInUseByTransporterId(@PathVariable("transporterID")Integer TransporterId)
    {

        return terminalService.getInUseByTransporterId(TransporterId);
    }

    @GetMapping("/ProcessTerminalAR{ActivationRequestId}approval={approval}")
    boolean processTerminalActivationRequest(@PathVariable("ActivationRequestId")Integer ARId, @PathVariable("approval") Boolean approval)
    {

        return terminalService.processTerminalActivationRequest(ARId,approval);
    }

    @GetMapping("/ChangeisActiveTerminalId={TerminalId}andIsActive={isActive}")
    public boolean postMethodName(@PathVariable("TerminalId") Integer TerminalId,@PathVariable("isActive") Boolean isActive) 
    {
        return terminalService.ChangeIsActiveTerminalId(TerminalId,isActive);

    }

    @PostMapping("/updateTerminalId={TerminalId}andRouteId={RouteId}andDriverId={DriverId}")
    public ResponseEntity<?> updateTerminal(@PathVariable("TerminalId") Integer TerminalId, @PathVariable("RouteId") Integer RouteId, @PathVariable("DriverId") Integer DriverId,
    HttpServletRequest request){

        String bearerToken = request.getHeader("Authorization");
        
        bearerToken = bearerToken.substring(7, bearerToken.length());
        String[] chunks = bearerToken.split("\\.");

        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        Integer id = null;

        try (JsonReader jsonReader = Json.createReader(new StringReader(payload))) {
    
            JsonObject jsonObject = jsonReader.readObject();

            id = jsonObject.getInt("id");
        }

        if(id != DriverId)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(false);

        return ResponseEntity.status(HttpStatus.OK).body(routeHistoryService.updateTerminal(TerminalId, RouteId, DriverId));
    }
    
}
