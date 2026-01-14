package com.campus.complaintmanagement.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor


public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private String title;

    @Enumerated(EnumType.STRING)
    private ComplaintStatus status = ComplaintStatus.PENDING;

    public void setStatus(ComplaintStatus status) {
        this.status = status;
    }

    // Relationship: Many complaints belong to 1 student
    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonIgnoreProperties("complaints")
    private Student student;

    private String department; // To which department complaint belongs

    public enum ComplaintStatus {
        PENDING,
        IN_PROGRESS,
        RESOLVED
    }


}
