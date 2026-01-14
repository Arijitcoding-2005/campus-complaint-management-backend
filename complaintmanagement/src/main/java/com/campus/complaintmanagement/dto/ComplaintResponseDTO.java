package com.campus.complaintmanagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComplaintResponseDTO {
    private Long id;
    private String title;
    private String description;
    private Long studentId;
    private String status;

}
