package com.jankowski.ticketapp.repository;

import com.jankowski.ticketapp.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}

