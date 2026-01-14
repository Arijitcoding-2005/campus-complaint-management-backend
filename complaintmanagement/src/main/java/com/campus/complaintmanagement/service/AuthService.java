package com.campus.complaintmanagement.service;

public interface AuthService {
    String loginStudent(String email, String password);

    String loginAdmin(String email, String password);
}
