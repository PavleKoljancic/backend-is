package com.app.backend.controllers;

import java.io.StringReader;
import java.util.Base64;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.backend.services.TicketRequestService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/ticketRequests")
public class TicketRequestController {
    @Autowired
    TicketRequestService ticketRequestService;

    @GetMapping("addTicketRequest={ticketTypeId}&UserId={UserID}")
    public ResponseEntity<String> addTicketRequest(@PathVariable("ticketTypeId") Integer ticketTypeId, @PathVariable("UserID") Integer userId, HttpServletRequest request) 
    {
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

        if(id == userId)
            return ResponseEntity.ok(ticketRequestService.addTicketRequest(ticketTypeId, userId));
        else
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }
}
