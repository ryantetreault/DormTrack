package com.cboard.dormTrack.dormTrack_backend.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "maintenance_request_log")
public class ResolvedRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer logId;

    private Integer requestId;

    private String resolvedBy;

    public ResolvedRequest() {}

    public ResolvedRequest(Integer logId, Integer requestId, String resolvedBy, LocalDate resolutionDate) {
        this.logId = logId;
        this.requestId = requestId;
        this.resolvedBy = resolvedBy;
        this.resolutionDate = resolutionDate;
    }

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
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

    private LocalDate resolutionDate;
}