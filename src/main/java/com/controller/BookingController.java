package com.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.controller.vo.BookingInfo;
import com.controller.vo.RoomsInfo;
import com.model.Booking;
import com.model.BookingDetails;
import com.model.Patient;
import com.model.Room;
import com.model.RoomType;
import com.repository.BookingDetailsRepository;
import com.repository.BookingRepository;
import com.repository.PatientRepository;
import com.repository.RoomRepository;
import com.repository.RoomTypeRepository;

@RestController
public class BookingController {

	@Autowired
	private RoomRepository roomRepository;
	@Autowired
	private RoomTypeRepository roomTypeRepository;
	@Autowired
	private BookingDetailsRepository bookingDetailsRepository;
	@Autowired
	private BookingRepository bookingRepository;
	@Autowired
	private PatientRepository patientRepository;
	
	private final Logger logger = LoggerFactory.getLogger(BookingController.class);	    
	   	   

	@PreAuthorize("hasRole('ADMIN') OR hasRole('EXECUTIVE')")
	@GetMapping("/api/patient/search")
	public Resources<BookingDetails> searchBooking(@RequestParam(value="patientName") String patientName) {	
		 logger.debug(" searchBooking  Entry ");
		List<BookingDetails> list = patientRepository.searchByPatientName(patientName); 
		logger.debug(" searchBooking exit ");	
		return new Resources<>(list);	       	
    }

	@PreAuthorize("hasRole('ADMIN') OR hasRole('EXECUTIVE')")
	@PostMapping("/api/patient/addPatient")
	public ResponseEntity<?> addPatient(@ModelAttribute Patient patient) throws IOException{	     
			patientRepository.save(patient);
	        return new ResponseEntity<Object>("Successfully uploaded!", HttpStatus.OK);
	} 
	
	@PreAuthorize("hasRole('ADMIN') OR hasRole('EXECUTIVE')")
	@PostMapping("/api/patient/updatePatient")
	public ResponseEntity<?> updatePatient(@ModelAttribute Patient patient) throws IOException{	     
			patientRepository.save(patient);
	        return new ResponseEntity<Object>("Successfully uploaded!", HttpStatus.OK);
	} 
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/api/room/{id}")
	public ResponseEntity<?> deleteRoom(@PathVariable(value="roomId") long roomId) throws IOException{	     
	        roomRepository.delete(roomId);
	        return new ResponseEntity<Object>("Successfully deleted!", HttpStatus.OK);
	}   
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/api/room/addRoom")
	public Room addRoom(@ModelAttribute Room room) throws IOException{	
		return roomRepository.save(room);
	}  
	
	@PreAuthorize("hasRole('ADMIN') OR hasRole('EXECUTIVE')")
	//List the Available rooms
	@GetMapping("/api/rooms/all")
	public  List<RoomsInfo> findAllRooms() throws IOException{
	        logger.debug("find All Rooms");	
	        List<RoomsInfo> roomsList = new ArrayList<RoomsInfo>();
	        Iterable<RoomType> roomTypeList = roomTypeRepository.findAll();
	        for(RoomType roomType :roomTypeList){
	        	RoomsInfo roominfo = new RoomsInfo();
	        	roominfo = new RoomsInfo();
	        	roominfo.setRoomTypeId(roomType.getRoomTypeId());
	        	roominfo.setRoomType(roomType.getRoomType());
	        	List<Room> roomList = roomRepository.availableRoomsOfType(roomType.getRoomTypeId());
	        	if(roomList != null){
		        	 logger.debug("availableRoomsOfType "+roomList.size());	
	        		roominfo.setAvailableRooms(roomList.size());
	        	}
	        	List<Room> roomList2 = roomRepository.totalRoomsOfType(roomType.getRoomTypeId());
	        	if(roomList2 != null){
	        		logger.debug("availableRoomsOfType "+roomList.size());	
	        		roominfo.setTotalRooms(roomList2.size());
	        	}	        
	        	roomsList.add(roominfo);
	        }
	        return roomsList;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/api/room/booking/{bookingId}")
    public BookingDetails getBooking(@PathVariable(value="bookingId") long bookingId) {
        logger.debug("updateBooking");
        return	bookingDetailsRepository.findOne(bookingId);        	
    }
	
	@PreAuthorize("hasRole('ADMIN') OR hasRole('EXECUTIVE')")
	@GetMapping("/api/room/booking/details")
    public List<BookingDetails> searchBooking(@RequestParam(value="startDate") Date startDate, @RequestParam(value="endDate") Date endDate) {
		java.sql.Date startDateSQL = new java.sql.Date( startDate.getTime() );
		java.sql.Date endDateSQL = new java.sql.Date( endDate.getTime() );
		return	bookingDetailsRepository.bookingDetailsDateRange(startDateSQL, endDateSQL);        	
    }

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/api/room/booking/update")
    public ResponseEntity<?> updateBooking(@ModelAttribute BookingInfo bookingInfo) {
        logger.debug("updateBooking");
        try {	        	
        	bookingDetailsRepository.save(bookingInfo.getBookingDetails());        	
        } catch (Exception e) {
			logger.error("Error while booking file", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Object>("Successfully booked!", HttpStatus.OK);
    }
	
	@PreAuthorize("hasRole('ADMIN') OR hasRole('EXECUTIVE')")
    @PostMapping("/api/room/booking")
    public ResponseEntity<?> booking(@ModelAttribute BookingInfo bookingInfo) {
        logger.debug("Upload file With metadata");
        try {	        	
        	BookingDetails bookingDetails = bookingDetailsRepository.save(bookingInfo.getBookingDetails());
        	Room room = roomRepository.findOne(bookingInfo.getRoomId());
        	room.setAvailable(false);
        	roomRepository.save(room);
        	Booking booking = new Booking();
        	booking.setPatientId(bookingInfo.getPatientId());
        	booking.setRoomId(bookingInfo.getRoomId());
        	booking.setBookingId(bookingDetails.getBookingId());
        	bookingRepository.save(booking);
        } catch (Exception e) {
			logger.error("Error while booking file", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Object>("Successfully booked!", HttpStatus.OK);
    }    
}
	     
