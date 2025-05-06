package com.cboard.dormTrack.dormTrack_backend.service;

import com.cboard.dormTrack.dormTrack_backend.repository.DormRepository;
import com.cboard.dormTrack.dormTrack_backend.repository.RoomRepository;
import com.cboard.dormTrack.dormTrack_common.dto.DormDto;
import com.cboard.dormTrack.dormTrack_common.dto.RoomDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DormService {

    @Autowired
    private DormRepository dormRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    public DormService(DormRepository dormRepository) {
        this.dormRepository = dormRepository;
    }

    public List<DormDto> getAllDorms() {
        return dormRepository.findAll().stream()
                .map(dorm -> new DormDto(dorm.getDormId(), dorm.getName()))
                .collect(Collectors.toList());
    }

    public List<RoomDto> getAvailableRoomsByDorm(int dormId) {
        return roomRepository.findAvailableRoomsByDormId(dormId)
                .stream()
                .map(room -> new RoomDto(
                        room.getRoomId(),
                        room.getDorm().getDormId(),
                        room.getFloor(),
                        room.getCapacity(),
                        room.getCurrentOccupancy()
                ))
                .collect(Collectors.toList());
    }
}