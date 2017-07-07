package com.controller.vo;

import java.io.Serializable;


public class RoomsInfo implements Serializable{
	
    private String roomType;  
    private long roomTypeId;  
    private int totalRooms;
    private int availableRooms;
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public long getRoomTypeId() {
		return roomTypeId;
	}
	public void setRoomTypeId(long roomTypeId) {
		this.roomTypeId = roomTypeId;
	}
	public int getTotalRooms() {
		return totalRooms;
	}
	public void setTotalRooms(int totalRooms) {
		this.totalRooms = totalRooms;
	}
	public int getAvailableRooms() {
		return availableRooms;
	}
	public void setAvailableRooms(int availableRooms) {
		this.availableRooms = availableRooms;
	}
        	
    
}