package com.jankowski.ticketapp.utils;

import com.jankowski.ticketapp.entity.Movie;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MovieUtils {

    public static List<Movie> sortByTitleAndScreeningTime(List<Movie> movies) {
        return movies
                .stream()
                .sorted(Comparator.comparing(Movie::getTitle))
                .peek(m -> Collections.sort(m.getScreeningTimes()))
                .collect(Collectors.toList());
    }

    public static List<Movie> filterByTitleAndDate(List<Movie> movies, String title, LocalDateTime date) {
        return movies
                .stream()
                .filter(m -> Objects.equals(m.getTitle(), title))
                .peek(m -> m.setScreeningTimes(m.getScreeningTimes()
                        .stream()
                        .filter(d -> Objects.equals(d, date))
                        .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }
}
