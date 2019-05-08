package com.jankowski.ticketapp.repository;

import com.jankowski.ticketapp.entity.Movie;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, Long> {
}
