package com.cboard.dormTrack.dormTrack_backend.controller;

import com.cboard.dormTrack.dormTrack_common.dto.AssignmentDto;
import com.cboard.dormTrack.dormTrack_common.dto.AssignmentRequest;
import com.cboard.dormTrack.dormTrack_backend.model.Assignment;
import com.cboard.dormTrack.dormTrack_backend.repository.AssignmentRepository;
import com.cboard.dormTrack.dormTrack_backend.repository.RoomRepository;
import com.cboard.dormTrack.dormTrack_backend.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assignments")
@CrossOrigin(origins = "http://localhost:8080")
public class AssignmentController {

    @Autowired
    private AssignmentRepository assignmentRepo;

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private RoomRepository roomRepo;

    @GetMapping("/all")
    public List<AssignmentDto> getAllAssignments() {
        return assignmentRepo.findAll().stream()
                .map(a -> new AssignmentDto(
                        a.getAssignmentId(),
                        a.getStudent().getName(),
                        "Room " + a.getRoom().getRoomId(),
                        a.getDateAssigned() != null ? a.getDateAssigned().toString() : "",
                        a.getDateVacated() != null ? a.getDateVacated().toString() : ""
                ))
                .toList();
    }

    @PostMapping
    public void assignRoom(@RequestBody AssignmentRequest request) {
        var student = studentRepo.findById(request.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID"));
        var room = roomRepo.findById(request.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid room ID"));

        if (room.getCurrentOccupancy() >= room.getCapacity()) {
            throw new IllegalStateException("Room is full");
        }

        Assignment assignment = new Assignment(student, room, request.getDateAssigned());
        assignmentRepo.save(assignment);

        room.setCurrentOccupancy(room.getCurrentOccupancy() + 1);
        roomRepo.save(room);
    }
}