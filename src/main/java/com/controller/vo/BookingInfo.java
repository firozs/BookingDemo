package com.controller.vo;

import java.io.Serializable;
import java.util.Date;

import com.model.BookingDetails;


public class BookingInfo implements Serializable{

	
	private String patientName;
	private long patientId;
	private long roomType;
	private long roomId;
	private Date bookingDate;
    private Date bookingStartDate;
    private Date bookingEndDate;
    
	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public long getPatientId() {
		return patientId;
	}

	public void setPatientId(long patientId) {
		this.patientId = patientId;
	}

	public long getRoomType() {
		return roomType;
	}

	public void setRoomType(long roomType) {
		this.roomType = roomType;
	}

	public long getRoomId() {
		return roomId;
	}

	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}

	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public Date getBookingStartDate() {
		return bookingStartDate;
	}

	public void setBookingStartDate(Date bookingStartDate) {
		this.bookingStartDate = bookingStartDate;
	}

	public Date getBookingEndDate() {
		return bookingEndDate;
	}

	public void setBookingEndDate(Date bookingEndDate) {
		this.bookingEndDate = bookingEndDate;
	}

	public BookingDetails getBookingDetails() {
		BookingDetails bookingDetails = new BookingDetails();		
		bookingDetails.setBookingDate(bookingDate);
		bookingDetails.setBookingStartDate(bookingStartDate);
		bookingDetails.setBookingEndDate(bookingEndDate);
		return bookingDetails;
	}
	
	
	
    
	
}