package com.jankowski.ticketapp;

import com.jankowski.ticketapp.controller.MovieController;
import com.jankowski.ticketapp.entity.Movie;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MovieControllerTest {

    @Autowired
    private MovieController movieController;

    private List<Movie> movies;

    @Before
    public void getListOfMovies() {
        movies = StreamSupport
                .stream(movieController.findAllMovies().getBody().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Test
    public void notNullMovieController() {
        assertThat(movieController).isNotNull();
    }

    @Test
    public void getStatusOkWhenFindsMovies() {
        assertThat(movieController.findAllMovies().getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void checkNumberOfMovies() {
        assertThat(movies.size()).isEqualTo(3);
    }

}
