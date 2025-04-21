package com.cboard.dormTrack.dormTrack_backend.dao;

import com.cboard.dormTrack.dormTrack_backend.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestDao extends JpaRepository<Question, Integer>
{

}
