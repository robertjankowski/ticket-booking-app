package com.jankowski.ticketapp.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<Movie> movies;

    private Long seats;

    public Room(List<Movie> movies, Long seats) {
        this.movies = movies;
        this.seats = seats;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
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
                ", seats=" + seats +
                '}';
    }
}
