package com.campus.complaintmanagement.Serviceimp;
import com.campus.complaintmanagement.dto.ComplaintRequestDTO;
import com.campus.complaintmanagement.dto.ComplaintResponseDTO;
import com.campus.complaintmanagement.entity.Complaint;
import com.campus.complaintmanagement.entity.Student;
import com.campus.complaintmanagement.exception.ResourceNotFound;
import com.campus.complaintmanagement.exception.UnauthorizedException;
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
        dto.setName(complaint.getStudent().getName());
        dto.setCreatedAt(complaint.getCreatedAt());
        dto.setRemark(complaint.getRemark());
        dto.setDepartment(complaint.getDepartment());
        dto.setAssignedTo(complaint.getAssignedTo());
        return dto;
    }
    @Override
    public List<ComplaintResponseDTO> getAllComplaints() {
        return complaintRepository.findByIsDeletedFalse()
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }
    @Override
    public List<ComplaintResponseDTO> getComplaintsByStudentId(Long studentId) {

        List<Complaint> complaints =
                complaintRepository.findByStudentIdAndIsDeletedFalse(studentId);

        return complaints.stream()
                .map(this::mapToResponseDTO)
                .toList();
    }
    @Override
    public void softDeleteComplaint(Long id, Long studentId) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Complaint not found"));

        if (!complaint.getStudent().getId().equals(studentId)) {
            throw new UnauthorizedException("You are not authorized to delete this complaint");
        }

        if (!complaint.getStatus().equals(Complaint.ComplaintStatus.PENDING)) {
            throw new RuntimeException("Only PENDING complaints can be withdrawn");
        }

        complaint.setDeleted(true);
        complaintRepository.save(complaint);
    }
    public void editComplaint(Long id, Long studentId, ComplaintRequestDTO request) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Complaint not found"));

        if (!complaint.getStudent().getId().equals(studentId)) {
            throw new UnauthorizedException("You are not authorized to edit this complaint");
        }
        if (!complaint.getStatus().equals(Complaint.ComplaintStatus.PENDING)) {
            throw new RuntimeException("Only PENDING complaints can be edited");
        }

        complaint.setTitle(request.getTitle());
        complaint.setDescription(request.getDescription());
        complaint.setDepartment(request.getDepartment());
        complaintRepository.save(complaint);
    }
}

