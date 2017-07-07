package com.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.model.BookingDetails;
import com.model.Patient;

@RepositoryRestResource
public interface PatientRepository extends CrudRepository<Patient, Long> {
	
	// custom query example and return a stream
    @Query("select p from Patient p where name = :patientName")
    List<BookingDetails> searchByPatientName(@Param("patientName") String patientName);
}
