package com.app.backend.services;

import org.springframework.stereotype.Service;

import com.app.backend.repositories.TerminalActivationRequestRepo;



@Service
public class TerminalActivationRequestService {
    TerminalActivationRequestRepo terminalActivationRequestRepo = new TerminalActivationRequestRepo();

    
}
