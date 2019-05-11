package com.jankowski.ticketapp.message;

public class Message {

    public static final String ROOM_NOT_FOUND = "Room not found";
    public static final String MOVIE_NOT_FOUND = "Movie not found";
    public static final String DATE_NOT_FOUND = "Not found screening time for specify movie";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String TICKET_NOT_FOUND = "Ticket not found";
    public static final String USER_REMOVED = "User removed";

    private String message;

    public Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
