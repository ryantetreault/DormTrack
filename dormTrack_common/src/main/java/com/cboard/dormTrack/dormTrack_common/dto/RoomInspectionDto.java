package com.cboard.dormTrack.dormTrack_common.dto;

import java.time.LocalDate;

public class RoomInspectionDto {
    public int getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(int inspectionId) {
        this.inspectionId = inspectionId;
    }

    public String getRaName() {
        return raName;
    }

    public void setRaName(String raName) {
        this.raName = raName;
    }

    public String getRoomLabel() {
        return roomLabel;
    }

    public void setRoomLabel(String roomLabel) {
        this.roomLabel = roomLabel;
    }

    public LocalDate getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(LocalDate inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    private int roomId;
    private int inspectionId;
    private String raName;
    private String roomLabel;
    private LocalDate inspectionDate;
    private String result;
    private String notes;

    public RoomInspectionDto() {
    }

    public RoomInspectionDto(int inspectionId, String raName, String roomLabel, LocalDate inspectionDate, String result, String notes) {
        this.inspectionId = inspectionId;
        this.raName = raName;
        this.roomLabel = roomLabel;
        this.inspectionDate = inspectionDate;
        this.result = result;
        this.notes = notes;
    }
}
