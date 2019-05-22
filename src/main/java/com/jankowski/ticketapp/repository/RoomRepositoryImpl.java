package com.jankowski.ticketapp.repository;

import com.jankowski.ticketapp.entity.Room;
import com.jankowski.ticketapp.utils.MovieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class RoomRepositoryImpl {

    @Autowired
    private RoomRepository roomRepository;

    public List<Room> findAllByMovieTitleDate(String title, LocalDateTime date) {
        return StreamSupport
                .stream(roomRepository.findAll().spliterator(), false)
                .peek(r -> r.setMovies(MovieUtils.filterByTitleAndDate(r.getMovies(), title, date)))
                .collect(Collectors.toList());
    }
}
