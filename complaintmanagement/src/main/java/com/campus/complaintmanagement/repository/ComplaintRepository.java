package com.campus.complaintmanagement.repository;

import com.campus.complaintmanagement.entity.Complaint;
import com.campus.complaintmanagement.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    List<Complaint> findByStudent_Email(String studentEmail);

}