package com.jankowski.ticketapp.controller;

import com.jankowski.ticketapp.entity.User;
import com.jankowski.ticketapp.exception.UserNotFoundException;
import com.jankowski.ticketapp.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.logging.Logger;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("user")
public class UserController {

    private final Logger LOGGER = Logger.getLogger(UserController.class.getName());

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    @ResponseBody
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }


    @PostMapping
    public ResponseEntity createUser(@Valid @RequestBody User user) { // TODO: validation!!!
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{name}/{surname}")
    @ResponseBody
    public User findUser(@PathVariable String name, @PathVariable String surname) {
        User user = null;
        try {
            user = StreamSupport
                    .stream(this.userRepository.findAll().spliterator(), true)
                    .filter(e -> e.same(name, surname))
                    .findFirst().orElseThrow(UserNotFoundException::new);
        } catch (UserNotFoundException e) {
            LOGGER.severe("User not found");
        }
        return user;
    }

}
