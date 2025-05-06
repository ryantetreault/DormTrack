package com.cboard.dormTrack.dormTrack_common.dto;

import java.time.LocalDate;

public class OpenRequestDto {
    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    public void setIssueDescription(String issueDescription) {
        this.issueDescription = issueDescription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(LocalDate dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    private int requestId;
    private String studentName;
    private String issueDescription;
    private String status;
    private LocalDate dateSubmitted;

    public OpenRequestDto(int requestId, String studentName, String issueDescription, String status, LocalDate dateSubmitted) {
        this.requestId = requestId;
        this.studentName = studentName;
        this.issueDescription = issueDescription;
        this.status = status;
        this.dateSubmitted = dateSubmitted;
    }

    public OpenRequestDto() {}
}