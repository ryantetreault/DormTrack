package com.cboard.dormTrack.dormTrack_common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RoomDto {
    private DormDto dorm;
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

    public RoomDto(int roomId, int dormId, int floor, int capacity, int currentOccupancy, DormDto dorm) {
        this.roomId = roomId;
        this.dormId = dormId;
        this.floor = floor;
        this.capacity = capacity;
        this.currentOccupancy = currentOccupancy;
        this.dorm = dorm;
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

    public DormDto getDorm() {
        return dorm;
    }

    public void setDorm(DormDto dorm) {
        this.dorm = dorm;
    }

    public void setDormName(String dormName) {
        this.dorm.setName(dormName);
    }
}