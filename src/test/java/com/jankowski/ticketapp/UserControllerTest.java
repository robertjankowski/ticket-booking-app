package com.jankowski.ticketapp;

import com.jankowski.ticketapp.controller.UserController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @Test
    public void isNotEmptyUsersRepo() {
        var request = userController.findUsers();
        assertThat(request.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(request.getBody().size()).isEqualTo(3);
    }

    @Test
    public void findExistingUserByNameAndSurname() {
        String name = "Jan";
        String surname = "Nowak";
        var request = userController.findUser(name, surname);
        var user = request.getBody();
        assertThat(request.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getSurname()).isEqualTo(surname);
    }

    @Test
    public void findNonExistingUserByNameAndSurname() {
        var request = userController.findUser("Paul", "Thomas");
        assertThat(request.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void deleteValidUser() {
        var request = userController.deleteUser("Jan", "Nowak");
        assertThat(request.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(request.getBody()).isTrue();
    }

    @Test
    public void deleteNonValidUser() {
        var request = userController.deleteUser("Paul", "Thomas");
        assertThat(request.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(request.getBody()).isFalse();
    }
}
