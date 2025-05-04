package com.cboard.dormTrack.dormTrack_backend.repository;

import com.cboard.dormTrack.dormTrack_backend.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {}