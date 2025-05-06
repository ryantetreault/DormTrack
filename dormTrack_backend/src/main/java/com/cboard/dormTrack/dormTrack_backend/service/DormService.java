package com.cboard.dormTrack.dormTrack_backend.service;

import com.cboard.dormTrack.dormTrack_backend.repository.DormRepository;
import com.cboard.dormTrack.dormTrack_common.dto.DormDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DormService {

    private final DormRepository dormRepository;

    @Autowired
    public DormService(DormRepository dormRepository) {
        this.dormRepository = dormRepository;
    }

    public List<DormDto> getAllDorms() {
        return dormRepository.findAll().stream()
                .map(dorm -> new DormDto(dorm.getDormId(), dorm.getName()))
                .collect(Collectors.toList());
    }
}