package com.cboard.dormTrack.dormTrack_backend.controller;

import com.cboard.dormTrack.dormTrack_common.dto.ResolvedRequestDto;
import com.cboard.dormTrack.dormTrack_backend.service.ResolvedRequestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/maintenance")
@CrossOrigin(origins = "http://localhost:8080")
public class ResolvedMaintenanceController {

    private final ResolvedRequestService service;

    public ResolvedMaintenanceController(ResolvedRequestService service) {
        this.service = service;
    }

    @GetMapping("/resolved")
    public List<ResolvedRequestDto> getResolvedRequests() {
        return service.getAllResolvedRequests();
    }
}