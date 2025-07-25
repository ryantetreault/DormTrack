package com.cboard.dormTrack.dormTrack_common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AssignmentDto {

    @JsonProperty("assignment_id")
    private int assignmentId;
    private String studentName;
    private String roomLabel;
    private String dateAssigned;
    private String dateVacated;
    private int dormId;

    public AssignmentDto() {}

    public AssignmentDto(int assignmentId, String studentName, String roomLabel, String dateAssigned, String dateVacated) {
        this.assignmentId = assignmentId;
        this.studentName = studentName;
        this.roomLabel = roomLabel;
        this.dateAssigned = dateAssigned;
        this.dateVacated = dateVacated;
    }

    public AssignmentDto(int assignmentId, String studentName, String roomLabel, String dateAssigned, String dateVacated, int dormId) {
        this.assignmentId = assignmentId;
        this.studentName = studentName;
        this.roomLabel = roomLabel;
        this.dateAssigned = dateAssigned;
        this.dateVacated = dateVacated;
        this.dormId = dormId;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getRoomLabel() {
        return roomLabel;
    }

    public void setRoomLabel(String roomLabel) {
        this.roomLabel = roomLabel;
    }

    public String getDateAssigned() {
        return dateAssigned;
    }

    public void setDateAssigned(String dateAssigned) {
        this.dateAssigned = dateAssigned;
    }

    public String getDateVacated() {
        return dateVacated;
    }

    public void setDateVacated(String dateVacated) {
        this.dateVacated = dateVacated;
    }

    public int getDormId() {
        return dormId;
    }

    public void setDormId(int dormId) {
        this.dormId = dormId;
    }
}