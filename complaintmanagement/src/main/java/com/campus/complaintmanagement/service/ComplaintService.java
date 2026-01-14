package com.campus.complaintmanagement.service;
import com.campus.complaintmanagement.dto.ComplaintRequestDTO;
import com.campus.complaintmanagement.dto.ComplaintResponseDTO;
import com.campus.complaintmanagement.entity.Complaint;

import java.util.List;

public interface ComplaintService {

    ComplaintResponseDTO createComplaint(ComplaintRequestDTO dto,String studentEmail);

    List<ComplaintResponseDTO> getComplaintsByStudent(String email);

    ComplaintResponseDTO updateStatus(Long complaintId, Complaint.ComplaintStatus status);
    List<ComplaintResponseDTO > getAllComplaints();
}