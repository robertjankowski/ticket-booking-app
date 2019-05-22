package com.jankowski.ticketapp.repository;

import com.jankowski.ticketapp.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByNameAndSurname(String name, String surname);

    User save(User s);
}

