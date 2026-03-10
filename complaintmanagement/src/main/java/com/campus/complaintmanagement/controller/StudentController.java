package com.campus.complaintmanagement.controller;

import com.campus.complaintmanagement.dto.ComplaintRequestDTO;
import com.campus.complaintmanagement.entity.Student;
import com.campus.complaintmanagement.exception.ResourceNotFound;
import com.campus.complaintmanagement.service.StudentService;
import com.campus.complaintmanagement.service.ComplaintService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.campus.complaintmanagement.security.JwtUtil;
import com.campus.complaintmanagement.repository.StudentRepository;


import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final ComplaintService complaintService;
    private final JwtUtil jwtUtil;
    private final StudentRepository studentRepository;

    // Register student
    @PostMapping("/register")
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

    @DeleteMapping("/complaints/{id}")
    public ResponseEntity<?> deleteComplaint(
            @PathVariable Long id,
            HttpServletRequest request) {

        // Get email from JWT, then look up the student
        String token = request.getHeader("Authorization").substring(7);
        String email = jwtUtil.extractClaims(token).getSubject();

        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFound("Student not found"));

        complaintService.softDeleteComplaint(id, student.getId());
        return ResponseEntity.ok("Complaint deleted");
    }
    @PutMapping("/complaints/{id}")
    public ResponseEntity<?> editComplaint(
            @PathVariable Long id,
            @Valid @RequestBody ComplaintRequestDTO request,
            HttpServletRequest httpRequest) {

        String email = extractEmail(httpRequest);
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFound("Student not found"));

        complaintService.editComplaint(id, student.getId(), request);
        return ResponseEntity.ok("Complaint updated");
    }
    private String extractEmail(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        return jwtUtil.extractClaims(token).getSubject();
    }

}

