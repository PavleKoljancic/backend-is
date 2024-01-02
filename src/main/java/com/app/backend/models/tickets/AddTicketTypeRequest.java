package com.app.backend.models.tickets;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddTicketTypeRequest {
    TicketType ticketType;
    Integer [] transporterIds;
    Integer [] documentTypesIds;
}
