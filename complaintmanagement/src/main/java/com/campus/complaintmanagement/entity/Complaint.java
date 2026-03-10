package com.campus.complaintmanagement.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor


public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description", length = 1000)
    private String description;
    private String title;
    @Column(length = 500)
    private String remark;
    @Column(nullable = false)
    private boolean isDeleted = false;
    @Column
    private String assignedTo;


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
        IN_REVIEW,
        RESOLVED,
        REJECTED
    }
    @CreationTimestamp
    @Column(updatable = false)
    @Getter
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime createdAt;





}
