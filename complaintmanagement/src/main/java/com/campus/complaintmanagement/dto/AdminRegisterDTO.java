package com.campus.complaintmanagement.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AdminRegisterDTO {
    private String email;
    private String password;
    private String secretKey;
}
