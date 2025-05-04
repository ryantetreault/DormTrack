package com.cboard.dormTrack.dormTrack_common.dto;

public class CurrentStudentsByFloorDto {
    private String studentName;
    private int floor;
    private String dormName;
    private String raName;

    public CurrentStudentsByFloorDto() {
    }

    public CurrentStudentsByFloorDto(String studentName, int floor, String dormName, String raName) {
        this.studentName = studentName;
        this.floor = floor;
        this.dormName = dormName;
        this.raName = raName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getDormName() {
        return dormName;
    }

    public void setDormName(String dormName) {
        this.dormName = dormName;
    }

    public String getRaName() {
        return raName;
    }

    public void setRaName(String raName) {
        this.raName = raName;
    }

    @Override
    public String toString() {
        return "CurrentStudentsByFloorDto{" +
                "studentName='" + studentName + '\'' +
                ", floor=" + floor +
                ", dormName='" + dormName + '\'' +
                ", raName='" + raName + '\'' +
                '}';
    }
}