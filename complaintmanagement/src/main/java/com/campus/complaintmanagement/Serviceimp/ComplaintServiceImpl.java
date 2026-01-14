package com.campus.complaintmanagement.Serviceimp;

import com.campus.complaintmanagement.dto.ComplaintRequestDTO;
import com.campus.complaintmanagement.dto.ComplaintResponseDTO;
import com.campus.complaintmanagement.entity.Complaint;
import com.campus.complaintmanagement.entity.Student;
import com.campus.complaintmanagement.exception.ResourceNotFound;
import com.campus.complaintmanagement.repository.ComplaintRepository;
import com.campus.complaintmanagement.repository.StudentRepository;
import com.campus.complaintmanagement.service.ComplaintService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final StudentRepository studentRepository;

    @Override
    public ComplaintResponseDTO createComplaint(ComplaintRequestDTO dto,String studentEmail) {

        Student student = studentRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new ResourceNotFound("Student not found"));

        Complaint complaint = new Complaint();
        complaint.setTitle(dto.getTitle());
        complaint.setDescription(dto.getDescription());
        complaint.setStatus(Complaint.ComplaintStatus.PENDING);
        complaint.setStudent(student);

        Complaint saved = complaintRepository.save(complaint);

        return mapToResponseDTO(saved);
    }

    @Override
    public List<ComplaintResponseDTO> getComplaintsByStudent(String email) {
        return complaintRepository.findByStudent_Email(email)
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    @Override
    public ComplaintResponseDTO updateStatus(Long complaintId, Complaint.ComplaintStatus status) {

        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new ResourceNotFound("Complaint not found"));

        complaint.setStatus(status);
        return mapToResponseDTO(complaintRepository.save(complaint));
    }

    // 🔁 Entity → DTO mapper
    private ComplaintResponseDTO mapToResponseDTO(Complaint complaint) {
        ComplaintResponseDTO dto = new ComplaintResponseDTO();
        dto.setId(complaint.getId());
        dto.setTitle(complaint.getTitle());
        dto.setDescription(complaint.getDescription());
        dto.setStatus(complaint.getStatus().name());
        dto.setStudentId(complaint.getStudent().getId());
        return dto;
    }
    @Override
    public List<ComplaintResponseDTO> getAllComplaints() {
        return complaintRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }
}

