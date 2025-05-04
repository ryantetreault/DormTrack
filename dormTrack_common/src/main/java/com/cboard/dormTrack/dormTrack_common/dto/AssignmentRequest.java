package com.cboard.dormTrack.dormTrack_common.dto;

import java.time.LocalDate;

public class AssignmentRequest {
    private int studentId;
    private int roomId;
    private LocalDate dateAssigned;

    public AssignmentRequest() {}

    public AssignmentRequest(int studentId, int roomId, LocalDate dateAssigned) {
        this.studentId = studentId;
        this.roomId = roomId;
        this.dateAssigned = dateAssigned;
    }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }

    public LocalDate getDateAssigned() { return dateAssigned; }
    public void setDateAssigned(LocalDate dateAssigned) { this.dateAssigned = dateAssigned; }
}