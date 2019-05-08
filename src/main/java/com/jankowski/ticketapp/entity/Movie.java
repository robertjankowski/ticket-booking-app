package com.jankowski.ticketapp.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;

    @ElementCollection(targetClass = LocalDate.class)
    private List<LocalDateTime> screeningTimes;

    public Movie(String title, List<LocalDateTime> screeningTimes) {
        this.title = title;
        this.screeningTimes = screeningTimes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<LocalDateTime> getScreeningTimes() {
        return screeningTimes;
    }

    public void setScreeningTimes(List<LocalDateTime> screeningTimes) {
        this.screeningTimes = screeningTimes;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", screeningTimes=" + screeningTimes +
                '}';
    }
}
