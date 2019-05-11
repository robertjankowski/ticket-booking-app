package com.jankowski.ticketapp.controller;

import com.jankowski.ticketapp.entity.Room;
import com.jankowski.ticketapp.entity.Ticket;
import com.jankowski.ticketapp.entity.User;
import com.jankowski.ticketapp.message.Message;
import com.jankowski.ticketapp.repository.MovieRepository;
import com.jankowski.ticketapp.repository.RoomRepository;
import com.jankowski.ticketapp.repository.UserRepository;
import com.jankowski.ticketapp.utils.TicketConvert;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static com.jankowski.ticketapp.message.Message.*;

@Controller
@RequestMapping("room")
public class RoomController {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    public RoomController(RoomRepository roomRepository, UserRepository userRepository, MovieRepository movieRepository) {
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    @GetMapping
    public ResponseEntity<List<Room>> findAllRooms() {
        final List<Room> rooms = StreamSupport
                .stream(roomRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        if (rooms.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @PostMapping("reserve/{id}/{title}/{date}/{userId}")
    public ResponseEntity<Message> reserveRoom(@PathVariable Long id,
                                               @PathVariable String title,
                                               @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
                                               @PathVariable Long userId,
                                               @RequestBody List<String> ticketTypes) {
        var room = roomRepository.findById(id);
        if (room.isEmpty()) {
            return new ResponseEntity<>(new Message(ROOM_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
        var movie = movieRepository.findByTitle(title);
        if (movie.isEmpty()) {
            return new ResponseEntity<>(new Message(MOVIE_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
        var isMovieScreeningTime = movie.get().getScreeningTimes()
                .stream()
                .anyMatch(d -> Objects.equals(d, date));
        if (!isMovieScreeningTime) {
            return new ResponseEntity<>(new Message(DATE_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
        var user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return new ResponseEntity<>(new Message(USER_NOT_FOUND), HttpStatus.NOT_FOUND);
        }

        Supplier<Stream<Optional<Ticket>>> ticketStream = () -> ticketTypes.stream()
                .map(TicketConvert::convertToTicketType);
        if (ticketStream.get().anyMatch(Optional::isEmpty)) {
            return new ResponseEntity<>(new Message(TICKET_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
        double totalCost = ticketStream.get()
                .map(Optional::get)
                .map(Ticket::getPrice)
                .collect(Collectors.summingDouble(Double::doubleValue));

        var updatedUser = user.get();
        updatedUser.setPayments(updatedUser.getPayments() - totalCost);
        userRepository.save(updatedUser);

        var updatedRoom = room.get();
        updatedRoom.setRow(updatedRoom.getRow() - ticketTypes.size());
        roomRepository.save(updatedRoom);

        var message = showReservation(updatedUser, updatedRoom, title, totalCost);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    private static Message showReservation(User user, Room room, String title, double totalCost) {
        return new Message("Reservation for: " + user + " room: " + room.getId() + " movie: " + title + " total cost: " + totalCost);
    }
}
