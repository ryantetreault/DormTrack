package com.cboard.dormTrack.dormTrack_backend.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class RoomInspection {

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

    public int getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(int inspectionId) {
        this.inspectionId = inspectionId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public LocalDate getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(LocalDate inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public InspectionResult getResult() {
        return result;
    }

    public void setResult(InspectionResult result) {
        this.result = result;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int inspectionId;
    private int roomId;
    private LocalDate inspectionDate;
    private String notes;

    @Column(nullable = false)
    private String raName;
    private String roomLabel;

    @Enumerated(EnumType.STRING)
    private InspectionResult result;

    public enum InspectionResult {
        Pass, Fail
    }

    public RoomInspection() {
    }

    public RoomInspection(int roomId, LocalDate inspectionDate, String notes, InspectionResult result, String raName, String roomLabel) {
        this.roomId = roomId;
        this.inspectionDate = inspectionDate;
        this.notes = notes;
        this.result = result;
        this.raName = raName;
        this.roomLabel = roomLabel;
    }
}
