package com.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.model.BookingDetails;
import com.model.Room;

@RepositoryRestResource
public interface BookingDetailsRepository extends CrudRepository<BookingDetails, Long> {
	
	// custom query example and return a stream
    @Query("select bd from BookingDetails bd where bookingDate between :startDate and :endDate")
    List<BookingDetails> bookingDetailsDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
    
}