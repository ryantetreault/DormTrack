package com.cboard.dormTrack.dormTrack_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/views")
@CrossOrigin(origins = "http://localhost:8080")
public class RoomChangeHistoryController {

    @Autowired
    private JdbcTemplate jdbc;

    @GetMapping("/roomChangeHistory")
    public List<Map<String, Object>> getRoomChangeHistory() {
        return jdbc.queryForList("SELECT * FROM room_change_history_view");
    }
}