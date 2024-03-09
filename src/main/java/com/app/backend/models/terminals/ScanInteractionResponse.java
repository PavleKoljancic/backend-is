package com.app.backend.models.terminals;

import com.app.backend.models.users.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ScanInteractionResponse {
    
    private User user;
    private boolean authSuccess;
    private String ticketName;
    
    public ScanInteractionResponse() 
    {
        this.user=null;
        this.authSuccess=false;
        this.ticketName=null;
    }
}
