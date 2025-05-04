package com.cboard.dormTrack.dormTrack_common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RoomDto {
    @JsonProperty("room_id")
    private int roomId;
    @JsonProperty("dorm_id")
    private int dormId;
    private int floor;
    private int capacity;
    @JsonProperty("current_occupancy")
    private int currentOccupancy;

    public RoomDto() {}

    public RoomDto(int roomId, int dormId, int floor, int capacity, int currentOccupancy) {
        this.roomId = roomId;
        this.dormId = dormId;
        this.floor = floor;
        this.capacity = capacity;
        this.currentOccupancy = currentOccupancy;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getDormId() {
        return dormId;
    }

    public void setDormId(int dormId) {
        this.dormId = dormId;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCurrentOccupancy() {
        return currentOccupancy;
    }

    public void setCurrentOccupancy(int currentOccupancy) {
        this.currentOccupancy = currentOccupancy;
    }
}