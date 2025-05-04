package com.cboard.dormTrack.dormTrack_backend.controller;

import com.cboard.dormTrack.dormTrack_common.dto.CurrentStudentsByFloorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/views")
@CrossOrigin(origins = "http://localhost:8080")
public class ViewController {

    @Autowired
    private JdbcTemplate jdbc;

    @GetMapping("/currentStudents")
    public List<CurrentStudentsByFloorDto> getCurrentStudentsByFloor() {
        String sql = "SELECT * FROM current_students_by_floor";
        return jdbc.query(sql, (rs, rowNum) ->
                new CurrentStudentsByFloorDto(
                        rs.getString("student_name"),
                        rs.getInt("floor"),
                        rs.getString("dorm_name"),
                        rs.getString("ra_name")
                )
        );
    }
}