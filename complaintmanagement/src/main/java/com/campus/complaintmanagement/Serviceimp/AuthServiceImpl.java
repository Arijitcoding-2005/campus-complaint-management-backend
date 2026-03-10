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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

//    @Override
//    public String loginStudent(String email, String password) {
//
//        Student student = studentRepository.findByEmail(email)
//                .orElseThrow(() ->
//                        new RuntimeException("Student not found"));
//
//        if (!passwordEncoder.matches(password, student.getPassword())) {
//            throw new RuntimeException("Invalid credentials");
//        }
//
//        return jwtUtil.generateToken(email, "STUDENT");
//    }
    @Override
    public String loginStudent(String email, String password) {

        System.out.println("LOGIN ATTEMPT → " + email);

        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> {
                    System.out.println("STUDENT NOT FOUND");
                    return new RuntimeException("Student not found");
                });

        System.out.println("DB PASSWORD → " + student.getPassword());

        boolean matches = passwordEncoder.matches(password, student.getPassword());

        System.out.println("PASSWORD MATCHES? → " + matches);

        if (!matches) {
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
    @Override
    public String login(String email, String password) {

        // 1️⃣ Check Admin first
        Optional<Admin> adminOptional = adminRepository.findByEmail(email);

        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();

            if (!passwordEncoder.matches(password, admin.getPassword())) {
                throw new RuntimeException("Invalid credentials");
            }

            return jwtUtil.generateToken(email, "ADMIN");
        }

        // 2️⃣ If not admin, check student
        Optional<Student> studentOptional = studentRepository.findByEmail(email);

        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();

            if (!passwordEncoder.matches(password, student.getPassword())) {
                throw new RuntimeException("Invalid credentials");
            }

            return jwtUtil.generateToken(email, "STUDENT");
        }

        // 3️⃣ If neither found
        throw new RuntimeException("User not found");
    }

}
