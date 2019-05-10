package com.jankowski.ticketapp.entity;

import org.springframework.lang.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Size(min = 3)
    private String name;

    @NonNull
    @Size(min = 3)
    private String surname;

    private double payments;

    public User() {
    }

    public User(String name, String surname, double payments) {
        this.name = name;
        this.surname = surname;
        this.payments = payments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", payments=" + payments +
                '}';
    }

    public boolean same(String name, String surname) {
        return Objects.equals(this.getName(), name) && Objects.equals(this.getSurname(), surname);
    }

    public double getPayments() {
        return payments;
    }

    public void setPayments(double payments) {
        this.payments = payments;
    }
}
