package com.cboard.dormTrack.dormTrack_backend.controller;

import com.cboard.dormTrack.dormTrack_backend.service.DormService;
import com.cboard.dormTrack.dormTrack_common.dto.DormDto;
import com.cboard.dormTrack.dormTrack_common.dto.RoomDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dorms")
@CrossOrigin(origins = "http://localhost:8080")
public class DormController {

    @Autowired
    private DormService dormService;

    @GetMapping("/all")
    public List<DormDto> getAllDorms() {
        return dormService.getAllDorms();
    }

    @GetMapping("/available/by-dorm/{dormId}")
    public List<RoomDto> getAvailableRoomsByDorm(@PathVariable int dormId) {
        return dormService.getAvailableRoomsByDorm(dormId);
    }
}