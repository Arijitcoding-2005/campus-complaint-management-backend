package com.campus.complaintmanagement.controller;

import com.campus.complaintmanagement.dto.LoginRequestDTO;
import com.campus.complaintmanagement.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/student/login")
    public ResponseEntity<String> studentLogin(
            @RequestBody LoginRequestDTO dto) {

        String token = authService.loginStudent(
                dto.getEmail(),
                dto.getPassword()
        );

        return ResponseEntity.ok(token);
    }

    @PostMapping("/admin/login")
    public ResponseEntity<String> adminLogin(
            @RequestBody LoginRequestDTO dto) {

        String token = authService.loginAdmin(
                dto.getEmail(),
                dto.getPassword()
        );

        return ResponseEntity.ok(token);
    }
}

