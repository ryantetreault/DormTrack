package com.cboard.dormTrack.dormTrack_backend.service;

import com.cboard.dormTrack.dormTrack_backend.repository.RoomRepository;
import com.cboard.dormTrack.dormTrack_backend.repository.StudentRepository;
import com.cboard.dormTrack.dormTrack_common.dto.AssignmentDto;
import com.cboard.dormTrack.dormTrack_backend.model.Assignment;
import com.cboard.dormTrack.dormTrack_backend.repository.AssignmentRepository;
import com.cboard.dormTrack.dormTrack_common.dto.AssignmentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssignmentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RoomRepository roomRepository;

    private final AssignmentRepository assignmentRepository;

    public AssignmentService(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<AssignmentDto> getAllAssignments() {
        return assignmentRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private AssignmentDto mapToDto(Assignment a) {
        return new AssignmentDto(
                a.getAssignmentId(),
                a.getStudent().getName(),
                "Room " + a.getRoom().getRoomId(),
                a.getDateAssigned() != null ? a.getDateAssigned().toString() : "",
                a.getDateVacated() != null ? a.getDateVacated().toString() : ""
        );
    }

    public void assignRoom(AssignmentRequest request) {
        var student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid student"));
        var room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid room"));

        if (room.getCurrentOccupancy() >= room.getCapacity()) {
            throw new IllegalStateException("Room is full");
        }

        Assignment assignment = new Assignment(student, room, request.getDateAssigned());
        assignmentRepository.save(assignment);

        room.setCurrentOccupancy(room.getCurrentOccupancy() + 1);
        roomRepository.save(room);
    }

    public Integer assignUsingProcedure(int studentId, int roomId) {
        return jdbcTemplate.execute(
                (Connection con) -> {
                    try (CallableStatement stmt = con.prepareCall("{CALL AssignStudentToRoom(?, ?)}")) {
                        stmt.setInt(1, studentId);
                        stmt.setInt(2, roomId);
                        ResultSet rs = stmt.executeQuery();
                        if (rs.next()) {
                            return rs.getInt("assigned_ra");
                        }
                        return null;
                    }
                }
        );
    }
}