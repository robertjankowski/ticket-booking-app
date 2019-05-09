package com.jankowski.ticketapp.entity;

public enum Ticket {

    ADULT(25),
    STUDENT(18),
    CHILD(12.50);

    private double price;

    Ticket(double price) {
        this.price = price;
    }
}
