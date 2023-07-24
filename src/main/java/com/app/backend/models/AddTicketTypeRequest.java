package com.app.backend.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddTicketTypeRequest {
    TicketType ticketType;
    Integer [] transporterIds;
}
