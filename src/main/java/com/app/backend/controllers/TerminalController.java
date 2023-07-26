package com.app.backend.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.backend.models.RouteHistory;
import com.app.backend.models.ScanInterraction;
import com.app.backend.models.Terminal;
import com.app.backend.models.TerminalActivationRequest;
import com.app.backend.security.SecurityUtil;
import com.app.backend.services.RouteHistoryService;
import com.app.backend.services.ScanInterractionService;
import com.app.backend.services.TerminalService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/terminals")
public class TerminalController {

    @Autowired
    private TerminalService terminalService;

    @Autowired
    private RouteHistoryService routeHistoryService;

    @Autowired
    private ScanInterractionService scanInterractionService;
    
    @GetMapping("/admin/getARByTransporterdId={transporterID}")
    public List<TerminalActivationRequest> findTerminalActivationRequestByTransporterId(@PathVariable("transporterID") Integer TRANSPORTER_Id) 
    {
        return terminalService.getTerminalActivationRequestByTransporterId(TRANSPORTER_Id);
    }    

    @GetMapping("/admin/getAllPendingAR")
    public List<TerminalActivationRequest> getAllPending() 
    {
        return terminalService.getAllPendingActivationRequests();
    }

    @PostMapping("add/activationrequest")
    public Integer  addTerminalActivationRequest(@RequestBody TerminalActivationRequest terminalActivationRequest)
    {
        return terminalService.addTerminalActivationRequest(terminalActivationRequest);
    }

    @GetMapping("/admin/getAllAR")    
    public List<TerminalActivationRequest> getAll() 
    {
        return terminalService.getAllActivationRequests();
    }

    @GetMapping("/admin/getAllTerminalByTransporterdId={transporterID}")
    List<Terminal> getTerminalByTransporterId(@PathVariable("transporterID")Integer TransporterId) 
    {
        return terminalService.getTerminalByTransporterId(TransporterId);
    }
    @GetMapping("/admin/getTerminalNotInUseByTransporterdId={transporterID}")
    List<Terminal> getNotInUSeByTransporterId(@PathVariable("transporterID")Integer TransporterId) 
    {
        return terminalService.getNotInUSeByTransporterId(TransporterId);
    }
    @GetMapping("/admin/getTerminalInUseByTransporterdId={transporterID}")
    List<Terminal> getInUseByTransporterId(@PathVariable("transporterID")Integer TransporterId)
    {

        return terminalService.getInUseByTransporterId(TransporterId);
    }

    @GetMapping("/admin/ProcessTerminalAR{ActivationRequestId}approval={approval}")
    boolean processTerminalActivationRequest(@PathVariable("ActivationRequestId")Integer ARId, @PathVariable("approval") Boolean approval)
    {

        return terminalService.processTerminalActivationRequest(ARId,approval);
    }

    @GetMapping("/admin/ChangeisActiveTerminalId={TerminalId}andIsActive={isActive}")
    public boolean postMethodName(@PathVariable("TerminalId") Integer TerminalId,@PathVariable("isActive") Boolean isActive) 
    {
        return terminalService.ChangeIsActiveTerminalId(TerminalId,isActive);

    }

    @PostMapping("/updateTerminalId={TerminalId}andRouteId={RouteId}andDriverId={DriverId}")
    public ResponseEntity<?> updateTerminal(@PathVariable("TerminalId") Integer TerminalId, @PathVariable("RouteId") Integer RouteId, @PathVariable("DriverId") Integer DriverId,
    HttpServletRequest request){

        Integer id = SecurityUtil.getIdFromAuthToken(request);

        if(id != DriverId)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(false);

        return ResponseEntity.status(HttpStatus.OK).body(routeHistoryService.updateTerminal(TerminalId, RouteId, DriverId));
    }

    @PostMapping("/CloseTerminalRouteHistory")
    public ResponseEntity<?> closeTerminalRouteHistory(@RequestBody RouteHistory routeHistory){

        if(routeHistoryService.closeTerminalRouteHistory(routeHistory))
            return ResponseEntity.status(HttpStatus.OK).body("RouteHistory successufully closed");
        else
            return ResponseEntity.status(HttpStatus.CONFLICT).body("RouteHistory is not opened");

    }
    
    @GetMapping("/getScanInterractionsForSameRouteByTerminalId={TerminalId}andNotOlderThan={Minutes}")
    public ResponseEntity<?> getScanInterractionsForSameRouteByTerminalId(@PathVariable("TerminalId") Integer TerminalId, @PathVariable("Minutes") Long Minutes){

        List<ScanInterraction> scanInterractions = routeHistoryService.getScanInterractionsForSameRouteByTerminalId(TerminalId, Minutes);
        if(scanInterractions == null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Terminal is not opened!");
        else
            return ResponseEntity.status(HttpStatus.OK).body(scanInterractions);
    }

    @GetMapping("/getScanInterractionsByTerminalId={TerminalId}andNotOlderThan={Minutes}")
    public ResponseEntity<?> getScanInterractionsByTerminalId(@PathVariable("TerminalId") Integer TerminalId, @PathVariable("Minutes") Long Minutes){

        Optional<Terminal> result = terminalService.getById(TerminalId);
        if(!result.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        else
            return ResponseEntity.status(HttpStatus.OK).body(scanInterractionService.getScanInterractionsByTerminalId(TerminalId, Minutes));
    }
}
