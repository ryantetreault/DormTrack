package com.cboard.dormTrack.dormTrack_backend.controller;

import com.cboard.dormTrack.dormTrack_backend.repository.AssignmentRepository;
import com.cboard.dormTrack.dormTrack_backend.service.AssignmentService;
import com.cboard.dormTrack.dormTrack_common.dto.AssignmentDto;
import com.cboard.dormTrack.dormTrack_common.dto.AssignmentRequest;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import com.cboard.dormTrack.dormTrack_backend.repository.RoomRepository;
import com.cboard.dormTrack.dormTrack_backend.repository.StudentRepository;
import com.cboard.dormTrack.dormTrack_common.dto.RoomChangeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    private JdbcTemplate jdbcTemplate;

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
                        a.getDateVacated() != null ? a.getDateVacated().toString() : "",
                        a.getRoom().getDorm().getDormId()
                ))
                .toList();
    }

    @PostMapping("/assign-via-procedure")
    public ResponseEntity<Integer> assignViaProcedure(@RequestBody AssignmentRequest req) {
        int studentId = req.getStudentId();
        int roomId = req.getRoomId();
        Integer raId = assignmentService.assignUsingProcedure(studentId, roomId);
        return ResponseEntity.ok(raId);
    }

    @PostMapping("/change-room")
    public ResponseEntity<String> changeStudentRoom(@RequestBody RoomChangeRequest request) {
        try {
            jdbcTemplate.update("CALL ChangeStudentRoom(?, ?)", request.getStudentId(), request.getNewRoomId());
            return ResponseEntity.ok("Room changed successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/move-out")
    public ResponseEntity<String> moveOutStudent(@RequestBody Map<String, Integer> body) {
        Integer studentId = body.get("studentId");
        try {
            jdbcTemplate.update("CALL MoveStudentOut(?)", studentId);
            return ResponseEntity.ok("Student moved out.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }
}