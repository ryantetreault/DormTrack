package com.cboard.dormTrack.dormTrack_backend.controller;

import com.cboard.dormTrack.dormTrack_backend.service.AssignmentService;
import com.cboard.dormTrack.dormTrack_common.dto.AssignmentDto;
import com.cboard.dormTrack.dormTrack_common.dto.AssignmentRequest;
import com.cboard.dormTrack.dormTrack_backend.model.Assignment;
import com.cboard.dormTrack.dormTrack_backend.repository.AssignmentRepository;
import com.cboard.dormTrack.dormTrack_backend.repository.RoomRepository;
import com.cboard.dormTrack.dormTrack_backend.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    private final AssignmentService assignmentService;

    @Autowired
    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

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
        assignmentService.assignRoom(request);
    }

    @PostMapping("/assign-via-procedure")
    public ResponseEntity<Integer> assignViaProcedure(@RequestBody AssignmentRequest req) {
        int studentId = req.getStudentId();
        int roomId = req.getRoomId();
        Integer raId = assignmentService.assignUsingProcedure(studentId, roomId);
        return ResponseEntity.ok(raId);
    }
}