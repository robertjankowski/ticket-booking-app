package com.jankowski.ticketapp.repository;

import com.jankowski.ticketapp.entity.Room;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room, Long> {
}
