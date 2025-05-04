package com.cboard.dormTrack.dormTrack_backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "RoomAssignment")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int assignmentId;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    private LocalDate dateAssigned;

    private LocalDate dateVacated;

    public Assignment() {}

    public Assignment(Student student, Room room, LocalDate dateAssigned) {
        this.student = student;
        this.room = room;
        this.dateAssigned = dateAssigned;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDate getDateAssigned() {
        return dateAssigned;
    }

    public void setDateAssigned(LocalDate dateAssigned) {
        this.dateAssigned = dateAssigned;
    }

    public LocalDate getDateVacated() {
        return dateVacated;
    }

    public void setDateVacated(LocalDate dateVacated) {
        this.dateVacated = dateVacated;
    }
}