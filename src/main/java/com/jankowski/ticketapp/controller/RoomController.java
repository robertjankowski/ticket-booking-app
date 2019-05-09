package com.jankowski.ticketapp.controller;

import com.jankowski.ticketapp.entity.Room;
import com.jankowski.ticketapp.entity.Ticket;
import com.jankowski.ticketapp.repository.RoomRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("room")
public class RoomController {

    private final RoomRepository repository;

    public RoomController(RoomRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Room>> findAllRooms() {
        final List<Room> rooms = StreamSupport
                .stream(repository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        if (rooms.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @GetMapping("reserve")
    public ResponseEntity<String> reserveRoom(@RequestParam("id") Long roomId,
                                              @RequestParam("title") String movieTitle,
                                              @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
                                              @RequestParam("ticket") Ticket ticket) throws Exception {
        var room = repository
                .findById(roomId)
                .orElseThrow();
        var movie = room.getMovies()
                .stream()
                .filter(m -> Objects.equals(m.getTitle(), movieTitle))
                .findFirst()
                .orElseThrow();
        var movieAtDate = movie.getScreeningTimes()
                .stream()
                .filter(d -> d.isEqual(date))
                .findFirst()
                .orElseThrow();
        String result = "Ticket for: " + movieTitle + " at: " + movieAtDate + " costs: " + ticket.name();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
