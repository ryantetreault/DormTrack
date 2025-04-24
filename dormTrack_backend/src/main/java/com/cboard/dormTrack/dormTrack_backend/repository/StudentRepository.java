package com.cboard.dormTrack.dormTrack_backend.repository;

import com.cboard.dormTrack.dormTrack_backend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {}