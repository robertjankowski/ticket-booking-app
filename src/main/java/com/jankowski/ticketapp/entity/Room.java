package com.jankowski.ticketapp.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    private List<Movie> movies;

    @Column(name = "row_number")
    private Long row;

    private Long seats;

    public Room(List<Movie> movies, Long row, Long seats) {
        this.movies = movies;
        this.row = row;
        this.seats = seats;
    }

    public Room() {
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public Long getId() {
        return id;
    }

    public Long getRow() {
        return row;
    }

    public void setRow(Long row) {
        this.row = row;
    }

    public Long getSeats() {
        return seats;
    }

    public void setSeats(Long seats) {
        this.seats = seats;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", movies=" + movies +
                ", row=" + row +
                ", seats=" + seats +
                '}';
    }
}
