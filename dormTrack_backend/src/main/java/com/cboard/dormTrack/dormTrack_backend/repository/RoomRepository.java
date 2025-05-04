package com.cboard.dormTrack.dormTrack_backend.repository;

import com.cboard.dormTrack.dormTrack_backend.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
}