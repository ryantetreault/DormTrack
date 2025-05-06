package com.cboard.dormTrack.dormTrack_common.dto;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "maintenance_request_log")
public class ResolvedRequestDto {

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getResolvedBy() {
        return resolvedBy;
    }

    public void setResolvedBy(String resolvedBy) {
        this.resolvedBy = resolvedBy;
    }

    public LocalDate getResolutionDate() {
        return resolutionDate;
    }

    public void setResolutionDate(LocalDate resolutionDate) {
        this.resolutionDate = resolutionDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private int logId;

    @Column(name = "request_id")
    private int requestId;

    @Column(name = "resolved_by")
    private String resolvedBy;

    @Column(name = "resolution_date")
    private LocalDate resolutionDate;

    private String issueDescription;

    private String studentName;

    public void setIssueDescription(String issueDescription) {
        this.issueDescription = issueDescription;
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentName() {
        return studentName;
    }
}