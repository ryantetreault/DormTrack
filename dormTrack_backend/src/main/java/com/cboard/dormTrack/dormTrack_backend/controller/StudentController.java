package com.cboard.dormTrack.dormTrack_backend.controller;

import com.cboard.dormTrack.dormTrack_backend.model.Student;
import com.cboard.dormTrack.dormTrack_backend.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@CrossOrigin(origins = "http://localhost:8080")
public class StudentController {

    @Autowired
    private StudentRepository studentRepo;

    @GetMapping("all")
    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }

    @PostMapping("/add")
    public Student createStudent(@RequestBody Student student) {
        return studentRepo.save(student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable int id) {
        studentRepo.deleteById(id);
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable int id, @RequestBody Student studentDetails) {
        Student student = studentRepo.findById(id).orElseThrow();
        student.setName(studentDetails.getName());
        student.setGender(studentDetails.getGender());
        student.setYear(studentDetails.getYear());
        student.setEmail(studentDetails.getEmail());
        return studentRepo.save(student);
    }
}