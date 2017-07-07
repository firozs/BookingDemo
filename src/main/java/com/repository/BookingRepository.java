package com.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.model.Booking;
import com.model.Room;

@RepositoryRestResource
public interface BookingRepository extends CrudRepository<Booking, Long> {
}
