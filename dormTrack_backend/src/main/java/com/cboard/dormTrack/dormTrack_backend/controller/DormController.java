package com.cboard.dormTrack.dormTrack_backend.controller;

import com.cboard.dormTrack.dormTrack_backend.service.DormService;
import com.cboard.dormTrack.dormTrack_common.dto.DormDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}