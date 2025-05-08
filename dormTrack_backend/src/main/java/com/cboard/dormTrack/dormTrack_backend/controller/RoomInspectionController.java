package com.cboard.dormTrack.dormTrack_backend.controller;


import com.cboard.dormTrack.dormTrack_backend.repository.RoomInspectionRepository;
import com.cboard.dormTrack.dormTrack_backend.service.RoomInspectionService;
import com.cboard.dormTrack.dormTrack_common.dto.RoomInspectionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inspections")
@CrossOrigin(origins = "http://localhost:8080")
public class RoomInspectionController {
    @Autowired
    private RoomInspectionRepository inspectionRepo;

    @Autowired
    private RoomInspectionService roomInspectionService;

    @GetMapping("/all")
    public List<RoomInspectionDto> getInspectionsView() {
        return roomInspectionService.getAllInspectionsFromView();
    }
}