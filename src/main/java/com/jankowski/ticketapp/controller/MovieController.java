package com.jankowski.ticketapp.controller;


import com.jankowski.ticketapp.entity.Movie;
import com.jankowski.ticketapp.repository.MovieRepository;
import com.jankowski.ticketapp.utils.DateTimeUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.jankowski.ticketapp.utils.DateTimeUtils.MIN_MINUTES_TO_BUY;
import static com.jankowski.ticketapp.utils.MovieSortingUtil.sortByTitleAndScreeningTime;

@Controller
@RequestMapping("movie")
public class MovieController {

    private final MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping
    public ResponseEntity<Iterable<Movie>> findAllMovies() {
        final List<Movie> movies = StreamSupport
                .stream(movieRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        if (movies.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(sortByTitleAndScreeningTime(movies), HttpStatus.OK);
    }

    @GetMapping("date")
    public ResponseEntity<Iterable<Movie>> findAllMoviesByDate(@RequestParam("time")
                                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        final List<Movie> movies = new ArrayList<>();
        StreamSupport
                .stream(movieRepository.findAll().spliterator(), false)
                .forEach(movie -> {
                    movie.setScreeningTimes(movie.getScreeningTimes()
                            .stream()
                            .filter(d -> DateTimeUtils.compareDates(date, d, MIN_MINUTES_TO_BUY))
                            .collect(Collectors.toList()));
                    if (!movie.getScreeningTimes().isEmpty())
                        movies.add(movie);
                });
        if (movies.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(sortByTitleAndScreeningTime(movies), HttpStatus.OK);
    }

}
