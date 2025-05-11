package com.cboard.dormTrack.dormTrack_backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roomId;

    @ManyToOne
    @JoinColumn(name = "dorm_id", nullable = false)
    private Dorm dorm;

    private int floor;

    private int capacity;

    @Column(name = "current_occupancy")
    private int currentOccupancy;

    public Room() {}

    public Room(Dorm dorm, int floor, int capacity, int currentOccupancy) {
        this.dorm = dorm;
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

    public Dorm getDorm() {
        return dorm;
    }

    public void setDorm(Dorm dorm) {
        this.dorm = dorm;
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