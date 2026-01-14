package com.campus.complaintmanagement.Serviceimp;

import com.campus.complaintmanagement.entity.Admin;
import com.campus.complaintmanagement.repository.AdminRepository;
import com.campus.complaintmanagement.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Admin registerAdmin(Admin admin) {
        admin.setPassword(
                passwordEncoder.encode(admin.getPassword())
        );
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
