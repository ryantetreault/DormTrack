package com.cboard.dormTrack.dormTrack_backend.controller;

import com.cboard.dormTrack.dormTrack_backend.model.Student;
import com.cboard.dormTrack.dormTrack_backend.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@CrossOrigin(origins = "http://localhost:8081")
public class StudentController {

    @Autowired
    private StudentRepository studentRepo;

    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentRepo.save(student);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody Student student) {
        student.setStudent_id(id);
        return ResponseEntity.ok(studentRepo.save(student));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable int id) {
        studentRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}