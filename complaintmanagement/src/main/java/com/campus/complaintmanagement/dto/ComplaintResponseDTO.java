package com.campus.complaintmanagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ComplaintResponseDTO {
    private Long id;
    private String title;
    private String description;
    private Long studentId;
    private String name;
    private String status;
    private LocalDateTime createdAt;
    private String remark;
    private String department;
    private String assignedTo;



}
