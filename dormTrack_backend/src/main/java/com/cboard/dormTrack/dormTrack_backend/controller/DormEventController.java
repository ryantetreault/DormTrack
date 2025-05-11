package com.cboard.dormTrack.dormTrack_backend.controller;

import com.cboard.dormTrack.dormTrack_backend.model.DormEvent;
import com.cboard.dormTrack.dormTrack_backend.repository.DormEventRepository;
import com.cboard.dormTrack.dormTrack_backend.repository.DormRepository;
import com.cboard.dormTrack.dormTrack_common.dto.DormDto;
import com.cboard.dormTrack.dormTrack_common.dto.EventDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/events")
@CrossOrigin(origins = "http://localhost:8080")
public class DormEventController {
    @Autowired
    private DormEventRepository eventRepo;

    @Autowired
    private DormRepository dormRepo;

    @GetMapping("/by-dorm/{dormId}")
    public List<DormEvent> getEventsByDorm(@PathVariable int dormId) {
        return eventRepo.findByDormId(dormId);
    }

    @PostMapping("/add")
    public DormEvent addEvent(@RequestBody DormEvent event) {
        return eventRepo.save(event);
    }
}