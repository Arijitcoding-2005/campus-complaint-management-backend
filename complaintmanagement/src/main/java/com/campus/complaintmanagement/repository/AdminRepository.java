package com.campus.complaintmanagement.repository;

import com.campus.complaintmanagement.entity.Admin;
import com.campus.complaintmanagement.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByEmail(String email);
}