package com.cboard.dormTrack.dormTrack_common.dto;

public class RoomChangeHistoryDto {
    private String studentName;
    private String oldRoomLabel;
    private String newRoomLabel;
    private String changeDate;

    public RoomChangeHistoryDto() {}

    public RoomChangeHistoryDto(String studentName, String oldRoomLabel, String newRoomLabel, String changeDate) {
        this.studentName = studentName;
        this.oldRoomLabel = oldRoomLabel;
        this.newRoomLabel = newRoomLabel;
        this.changeDate = changeDate;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getOldRoomLabel() {
        return oldRoomLabel;
    }

    public void setOldRoomLabel(String oldRoomLabel) {
        this.oldRoomLabel = oldRoomLabel;
    }

    public String getNewRoomLabel() {
        return newRoomLabel;
    }

    public void setNewRoomLabel(String newRoomLabel) {
        this.newRoomLabel = newRoomLabel;
    }

    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }
}