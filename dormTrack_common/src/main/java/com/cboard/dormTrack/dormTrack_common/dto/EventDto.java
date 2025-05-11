package com.cboard.dormTrack.dormTrack_common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class EventDto {
    public void setDormName(String dormName) {
        this.dormName = dormName;
    }

    public void setDorm(DormDto dorm) {
        this.dorm = dorm;
    }

    public String getDormName() {
        return dorm.getName();
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getDormId() {
        return dormId;
    }

    public void setDormId(int dormId) {
        this.dormId = dormId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    private int eventId;
    private int dormId;
    private String title;
    private String description;
    private LocalDate date;
    private DormDto dorm;

    @JsonProperty("dormName")
    private String dormName;

}