package com.campus.complaintmanagement.controller;

import com.campus.complaintmanagement.dto.AdminRegisterDTO;
import com.campus.complaintmanagement.dto.ComplaintResponseDTO;
import com.campus.complaintmanagement.dto.UpdateComplaintStatusDTO;
import com.campus.complaintmanagement.entity.Admin;
import com.campus.complaintmanagement.entity.Complaint;
import com.campus.complaintmanagement.entity.Student;
import com.campus.complaintmanagement.exception.ResourceNotFound;
import com.campus.complaintmanagement.repository.ComplaintRepository;
import com.campus.complaintmanagement.service.AdminService;
import com.campus.complaintmanagement.service.ComplaintService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ComplaintService complaintService;
    private final AdminService adminService;
    private final ComplaintRepository complaintRepository;

    // ADMIN: View all complaints
    @GetMapping("/complaints")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ComplaintResponseDTO>> getAllComplaints() {

        return ResponseEntity.ok(
                complaintService.getAllComplaints()
        );
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerAdmin(@RequestBody AdminRegisterDTO dto) {
        return ResponseEntity.ok(adminService.registerAdmin(dto));
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
    @GetMapping("/analytics")
    public ResponseEntity<Map<String, Long>> getAnalytics() {

        Map<String, Long> data = new HashMap<>();

        data.put("total", complaintRepository.count());
        data.put("pending", complaintRepository.countByStatus(Complaint.ComplaintStatus.PENDING));
        data.put("inProgress", complaintRepository.countByStatus(Complaint.ComplaintStatus.IN_PROGRESS));
        data.put("resolved", complaintRepository.countByStatus(Complaint.ComplaintStatus.RESOLVED));

        return ResponseEntity.ok(data);
    }
    @GetMapping("/students/{studentId}/complaints")
    public ResponseEntity<List<ComplaintResponseDTO>>
    getComplaintsByStudentId(@PathVariable Long studentId) {

        return ResponseEntity.ok(
                complaintService.getComplaintsByStudentId(studentId)
        );
    }
    @PatchMapping("/complaints/{id}/remark")
    public ResponseEntity<?> addRemark(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String remark = body.get("remark");
        Complaint complaint = complaintRepository.findById(id).orElseThrow();
        complaint.setRemark(remark);
        complaintRepository.save(complaint);
        return ResponseEntity.ok("Remark added");
    }
    @PatchMapping("/complaints/{id}/assign")
    public ResponseEntity<?> assignComplaint(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {

        String assignedTo = body.get("assignedTo");
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Complaint not found"));

        complaint.setAssignedTo(assignedTo);
        // Auto move to IN_REVIEW when assigned
        if (complaint.getStatus().equals(Complaint.ComplaintStatus.PENDING)) {
            complaint.setStatus(Complaint.ComplaintStatus.IN_REVIEW);
        }
        complaintRepository.save(complaint);
        return ResponseEntity.ok("Complaint assigned");
    }

}
