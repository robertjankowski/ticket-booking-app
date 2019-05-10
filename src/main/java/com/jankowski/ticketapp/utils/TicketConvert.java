package com.jankowski.ticketapp.utils;

import com.jankowski.ticketapp.entity.Ticket;

import java.util.Optional;

public class TicketConvert {

    public static Optional<Ticket> convertToTicketType(String ticketType) {
        try {
            return Optional.of(Ticket.valueOf(ticketType));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

}
