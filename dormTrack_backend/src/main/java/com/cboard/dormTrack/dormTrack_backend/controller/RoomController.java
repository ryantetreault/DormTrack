package com.cboard.dormTrack.dormTrack_backend.controller;

import com.cboard.dormTrack.dormTrack_backend.model.Room;
import com.cboard.dormTrack.dormTrack_backend.repository.RoomRepository;
import com.cboard.dormTrack.dormTrack_common.dto.RoomDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
@CrossOrigin(origins = "http://localhost:8080")
public class RoomController {

    @Autowired
    private RoomRepository roomRepo;

    @GetMapping("/all")
    public List<RoomDto> getAllRooms() {
        return roomRepo.findAll().stream()
                .map(r -> new RoomDto(
                        r.getRoomId(),
                        r.getDorm().getDormId(),
                        r.getFloor(),
                        r.getCapacity(),
                        r.getCurrentOccupancy()))
                .toList();
    }

    @PostMapping("/add")
    public Room createRoom(@RequestBody Room room) {
        return roomRepo.save(room);
    }

    @DeleteMapping("/{id}")
    public void deleteRoom(@PathVariable int id) {
        roomRepo.deleteById(id);
    }

    @PutMapping("/{id}")
    public Room updateRoom(@PathVariable int id, @RequestBody Room roomDetails) {
        Room room = roomRepo.findById(id).orElseThrow();
        room.setFloor(roomDetails.getFloor());
        room.setCapacity(roomDetails.getCapacity());
        room.setCurrentOccupancy(roomDetails.getCurrentOccupancy());
        room.setDorm(roomDetails.getDorm());
        return roomRepo.save(room);
    }
}