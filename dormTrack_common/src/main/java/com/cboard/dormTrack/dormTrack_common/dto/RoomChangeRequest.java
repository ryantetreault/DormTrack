package com.cboard.dormTrack.dormTrack_common.dto;

public class RoomChangeRequest {
    private int studentId;
    private int newRoomId;

    public RoomChangeRequest() {}

    public RoomChangeRequest(int studentId, int newRoomId) {
        this.studentId = studentId;
        this.newRoomId = newRoomId;
    }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public int getNewRoomId() { return newRoomId; }
    public void setNewRoomId(int newRoomId) { this.newRoomId = newRoomId; }
}