package com.campus.complaintmanagement.controller;

import com.campus.complaintmanagement.dto.ComplaintRequestDTO;
import com.campus.complaintmanagement.dto.ComplaintResponseDTO;
import com.campus.complaintmanagement.entity.Complaint;
import com.campus.complaintmanagement.service.ComplaintService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
public class ComplaintController {

    private final ComplaintService complaintService;

    //  Create a new complaint
    @PostMapping("/complaints")
    @PreAuthorize("hasRole('STUDENT')")

    public ResponseEntity<ComplaintResponseDTO> createComplaint(
            @RequestBody ComplaintRequestDTO dto,
            Authentication authentication) {

        String studentEmail = authentication.getName();

        return ResponseEntity.ok(
                complaintService.createComplaint(dto,studentEmail));
    }

    // STUDENT: View own complaints
    @GetMapping("/my")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<ComplaintResponseDTO>> getMyComplaints(
            Authentication authentication) {

        String studentEmail = authentication.getName();

        return ResponseEntity.ok(
                complaintService.getComplaintsByStudent(studentEmail)
        );
    }


}
