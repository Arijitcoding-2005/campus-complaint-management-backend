package com.campus.complaintmanagement.controller;

import com.campus.complaintmanagement.dto.ComplaintResponseDTO;
import com.campus.complaintmanagement.dto.UpdateComplaintStatusDTO;
import com.campus.complaintmanagement.entity.Admin;
import com.campus.complaintmanagement.entity.Complaint;
import com.campus.complaintmanagement.entity.Student;
import com.campus.complaintmanagement.service.AdminService;
import com.campus.complaintmanagement.service.ComplaintService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ComplaintService complaintService;
    private final AdminService adminService;

    // ADMIN: View all complaints
    @GetMapping("/complaints")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ComplaintResponseDTO>> getAllComplaints() {

        return ResponseEntity.ok(
                complaintService.getAllComplaints()
        );
    }
    @PostMapping
    public Admin createAdmin(@RequestBody Admin admin) {
        return adminService.createadmin(admin);
    }


    // ADMIN: Update complaint status
    @PatchMapping("/complaints/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ComplaintResponseDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam Complaint.ComplaintStatus status) {

        return ResponseEntity.ok(
                complaintService.updateStatus(id, status)
        );
    }
}
