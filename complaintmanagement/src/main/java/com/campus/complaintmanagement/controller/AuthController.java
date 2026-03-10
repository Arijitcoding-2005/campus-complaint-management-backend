package com.campus.complaintmanagement.controller;

import com.campus.complaintmanagement.dto.LoginRequestDTO;
import com.campus.complaintmanagement.dto.LoginResponseDTO;
import com.campus.complaintmanagement.security.JwtUtil;
import com.campus.complaintmanagement.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO dto) {

        String token = authService.login(
                dto.getEmail(),
                dto.getPassword()
        );

        String role = jwtUtil.extractRole(token);

        return ResponseEntity.ok(
                new LoginResponseDTO(token, role)
        );
    }
}

