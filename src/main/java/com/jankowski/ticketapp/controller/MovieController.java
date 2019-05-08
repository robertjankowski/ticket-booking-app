package com.jankowski.ticketapp.controller;


import com.jankowski.ticketapp.entity.Movie;
import com.jankowski.ticketapp.repository.MovieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("movie")
public class MovieController {

    private final MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping
    public ResponseEntity<Iterable<Movie>> findAllMovies() {
        var movies = movieRepository.findAll();
        var isEmpty = StreamSupport.stream(movies.spliterator(), false).count() == 0;
        if (isEmpty)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @PostMapping("/date")
    public ResponseEntity<Iterable<Movie>> findAllMoviesDate(@RequestBody LocalDateTime date) {
        // TODO: filter movies by date time and return list of movies
        var movies = StreamSupport
                .stream(movieRepository.findAll().spliterator(), false)
                .map(movie -> movie.getScreeningTimes()
                        .stream().filter(d -> {
                            var dur = Duration.between(d, date);
                            System.out.println(dur);
                            return dur.isNegative();
                        }).collect(Collectors.toList()));

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
