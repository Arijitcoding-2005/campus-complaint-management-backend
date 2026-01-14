package com.campus.complaintmanagement.service;

import com.campus.complaintmanagement.entity.Admin;

import java.util.List;

public interface AdminService {
    Admin createadmin(Admin admin);
    Admin getadminbyid(Long id);
    List<Admin> getalladmin();
    Admin registerAdmin(Admin admin);
}
