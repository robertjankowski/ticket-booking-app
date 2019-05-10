package com.jankowski.ticketapp.utils;

import com.jankowski.ticketapp.entity.Movie;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MovieSortingUtil {

    public static List<Movie> sortByTitleAndScreeningTime(List<Movie> movies) {
        return movies
                .stream()
                .sorted(Comparator.comparing(Movie::getTitle))
                .peek(m -> Collections.sort(m.getScreeningTimes()))
                .collect(Collectors.toList());
    }
}
