package com.repository;

import java.util.Iterator;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.model.Room;

@RepositoryRestResource
public interface RoomRepository extends CrudRepository<Room, Long> {
	
	// custom query example and return a stream
    @Query("select r from Room r where r.available = true and r.roomTypeId = :roomTypeId")
    List<Room> availableRoomsOfType(@Param("roomTypeId") long roomTypeId);
    
    @Query("select r from Room r where r.roomTypeId = :roomTypeId")
    List<Room> totalRoomsOfType(@Param("roomTypeId") long roomTypeId);
       
}
