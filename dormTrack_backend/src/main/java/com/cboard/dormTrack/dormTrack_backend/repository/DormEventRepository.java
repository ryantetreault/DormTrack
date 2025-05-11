package com.cboard.dormTrack.dormTrack_backend.repository;

import com.cboard.dormTrack.dormTrack_backend.model.DormEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DormEventRepository extends JpaRepository<DormEvent, Integer> {
    List<DormEvent> findByDormId(int dormId);
}