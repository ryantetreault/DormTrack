package com.cboard.dormTrack.dormTrack_common.dto;

public class DormDto {
    private int dormId;
    private String name;
    private String location;
    private int totalFloors;

    public DormDto() {}

    public DormDto(int dormId, String name, String location, int totalFloors) {
        this.dormId = dormId;
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
}