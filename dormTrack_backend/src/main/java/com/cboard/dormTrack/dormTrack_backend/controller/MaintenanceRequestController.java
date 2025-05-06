package com.cboard.dormTrack.dormTrack_backend.controller;

import com.cboard.dormTrack.dormTrack_common.dto.CloseRequestDto;
import com.cboard.dormTrack.dormTrack_common.dto.OpenRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/maintenance")
@CrossOrigin(origins = "http://localhost:8080")
public class MaintenanceRequestController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/open")
    public List<OpenRequestDto> getOpenRequests() {
        String sql = "SELECT * FROM open_maintenance_requests";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(OpenRequestDto.class));
    }

    @PostMapping("/close")
    public void closeRequest(@RequestBody CloseRequestDto dto) {
        jdbcTemplate.update("CALL close_maintenance_request(?, ?)", dto.getRequestId(), dto.getResolverName());
    }
}