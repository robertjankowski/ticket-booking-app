package com.jankowski.ticketapp.controller;

import com.jankowski.ticketapp.entity.User;
import com.jankowski.ticketapp.message.Message;
import com.jankowski.ticketapp.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.jankowski.ticketapp.message.Message.USER_NOT_FOUND;
import static com.jankowski.ticketapp.message.Message.USER_REMOVED;
import static org.springframework.util.StringUtils.capitalize;

@Controller
@RequestMapping("user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<User>> findUsers() {
        var users = StreamSupport
                .stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        User savedUser = userRepository.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    @GetMapping("{name}/{surname}")
    public ResponseEntity<User> findUser(@PathVariable String name, @PathVariable String surname) {
        var user = userRepository.findByNameAndSurname(capitalize(name), capitalize(surname));
        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    @DeleteMapping("{name}/{surname}")
    public ResponseEntity<Message> deleteUser(@PathVariable String name, @PathVariable String surname) {
        var user = findUser(name, surname);
        if (user.getStatusCode().is4xxClientError()) {
            return new ResponseEntity(new Message(USER_NOT_FOUND), HttpStatus.BAD_REQUEST);
        }
        userRepository.delete(user.getBody());
        return new ResponseEntity<>(new Message(USER_REMOVED), HttpStatus.OK);
    }

}
