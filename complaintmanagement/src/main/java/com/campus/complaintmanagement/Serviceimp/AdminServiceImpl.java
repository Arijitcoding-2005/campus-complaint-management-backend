package com.campus.complaintmanagement.Serviceimp;

import com.campus.complaintmanagement.dto.AdminRegisterDTO;
import com.campus.complaintmanagement.entity.Admin;
import com.campus.complaintmanagement.exception.UnauthorizedException;
import com.campus.complaintmanagement.repository.AdminRepository;
import com.campus.complaintmanagement.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.secret.key}")
    private String adminSecretKey;

    @Override
    public Admin registerAdmin(AdminRegisterDTO dto) {

        // Validate secret key
        if (!adminSecretKey.equals(dto.getSecretKey())) {
            throw new UnauthorizedException("Invalid admin secret key");
        }

        // Check if email already exists
        if (adminRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        // Map DTO to entity
        Admin admin = new Admin();
        admin.setEmail(dto.getEmail());
        admin.setPassword(passwordEncoder.encode(dto.getPassword()));
        admin.setRole("ADMIN");

        return adminRepository.save(admin);
    }

    @Override
    public Admin createadmin(Admin admin){
        admin.setPassword(
                passwordEncoder.encode(admin.getPassword())
        );
        return adminRepository.save(admin);
    }
    @Override
    public Admin getadminbyid(Long id){
        return adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }
    @Override
    public List<Admin> getalladmin(){
        return adminRepository.findAll();
    }
}
