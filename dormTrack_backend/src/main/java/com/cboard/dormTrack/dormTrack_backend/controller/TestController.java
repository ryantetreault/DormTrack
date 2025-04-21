package com.cboard.dormTrack.dormTrack_backend.controller;

import com.cboard.dormTrack.dormTrack_backend.model.Question;
import com.cboard.dormTrack.dormTrack_backend.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("question")
public class TestController
{
    @Autowired
    TestService testService;

    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions()
    {
        return testService.getAllQuestions();
    }
}
