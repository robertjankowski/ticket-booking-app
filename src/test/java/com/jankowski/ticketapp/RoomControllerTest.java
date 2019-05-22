package com.jankowski.ticketapp;


import com.jankowski.ticketapp.controller.RoomController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.jankowski.ticketapp.message.Message.*;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RoomControllerTest {

    @Autowired
    private RoomController roomController;


    @Test
    public void checkNumberOfRooms() {
        var request = roomController.findAllRooms();
        assertThat(request.getStatusCode()).isEqualTo(HttpStatus.OK);
        var noRooms = request.getBody().size();
        assertThat(noRooms).isEqualTo(2);
    }

    @Test
    public void roomNotFound() {
        var request = roomController.reserveRoom(10L, "Inception", LocalDateTime.now(), 1L, List.of("ADULT"));
        assertThat(request.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(request.getBody().getMessage()).isEqualTo(ROOM_NOT_FOUND);
    }

    @Test
    public void movieNotFound() {
        var request = roomController.reserveRoom(1L, "Title", LocalDateTime.now(), 1L, List.of("ADULT"));
        assertThat(request.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(request.getBody().getMessage()).isEqualTo(MOVIE_NOT_FOUND);
    }

    @Test
    public void wrongScreeningDate() {
        var request = roomController.reserveRoom(1L, "Inception", LocalDateTime.now(), 1L, List.of("ADULT"));
        assertThat(request.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(request.getBody().getMessage()).isEqualTo(DATE_NOT_FOUND);
    }

    @Test
    public void userNotFound() {
        var date = LocalDateTime.of(2019, 5, 8, 12, 30, 0);
        var request = roomController.reserveRoom(1L, "Inception", date, 20L, List.of("ADULT"));
        assertThat(request.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(request.getBody().getMessage()).isEqualTo(USER_NOT_FOUND);
    }

    @Test
    public void wrongTicketType() {
        var date = LocalDateTime.of(2019, 5, 8, 12, 30, 0);
        var request = roomController.reserveRoom(1L, "Inception", date, 2L, List.of("TICKET"));
        assertThat(request.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(request.getBody().getMessage()).isEqualTo(TICKET_NOT_FOUND);
    }
}
