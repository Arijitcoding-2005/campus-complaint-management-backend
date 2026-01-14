package com.campus.complaintmanagement.controller;

import com.campus.complaintmanagement.entity.Student;
import com.campus.complaintmanagement.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    // Register student
    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    // Get student by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('STUDENT')")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }
    // STUDENT: Update student
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('STUDENT')")
    public Student updateStudent(
            @PathVariable Long id,
            @RequestBody Student updatedStudent) {

        return studentService.updateStudent(id, updatedStudent);
    }

}

