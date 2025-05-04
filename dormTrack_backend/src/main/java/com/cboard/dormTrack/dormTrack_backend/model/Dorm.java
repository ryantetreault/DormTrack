package com.cboard.dormTrack.dormTrack_backend.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Dorm")
public class Dorm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dormId;

    private String name;

    private String location;

    private int totalFloors;

    @OneToMany(mappedBy = "dorm", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> rooms;

    public Dorm() {}

    public Dorm(String name, String location, int totalFloors) {
        this.name = name;
        this.location = location;
        this.totalFloors = totalFloors;
    }

    public int getDormId() {
        return dormId;
    }

    public void setDormId(int dormId) {
        this.dormId = dormId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getTotalFloors() {
        return totalFloors;
    }

    public void setTotalFloors(int totalFloors) {
        this.totalFloors = totalFloors;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}