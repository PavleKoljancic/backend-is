package com.app.backend.models.tickets;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AmountTicketWrapper {
    
    AmountTicket amountTicket;
    Integer count;
}
