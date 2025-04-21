package com.cboard.dormTrack.dormTrack_backend.service;

import com.cboard.dormTrack.dormTrack_backend.dao.TestDao;
import com.cboard.dormTrack.dormTrack_backend.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService
{
    @Autowired
    TestDao testDao;

    public ResponseEntity<List<Question>> getAllQuestions()
    {
        return new ResponseEntity<>(testDao.findAll(), HttpStatus.OK);
    }
}
