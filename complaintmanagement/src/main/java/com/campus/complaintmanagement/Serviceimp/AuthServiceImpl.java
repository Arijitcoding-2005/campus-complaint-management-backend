package com.campus.complaintmanagement.Serviceimp;

import com.campus.complaintmanagement.entity.Admin;
import com.campus.complaintmanagement.entity.Student;
import com.campus.complaintmanagement.repository.AdminRepository;
import com.campus.complaintmanagement.repository.StudentRepository;
import com.campus.complaintmanagement.security.JwtUtil;
import com.campus.complaintmanagement.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public String loginStudent(String email, String password) {

        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));

        if (!passwordEncoder.matches(password, student.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(email, "STUDENT");
    }

    @Override
    public String loginAdmin(String email, String password) {

        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Admin not found"));

        if (!passwordEncoder.matches(password, admin.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(email, "ADMIN");
    }

}
