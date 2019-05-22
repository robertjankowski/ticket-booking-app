package com.jankowski.ticketapp.repository;

import com.jankowski.ticketapp.entity.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {

    List<Room> findAllByMovieTitleDate(String title, LocalDateTime date);

}
