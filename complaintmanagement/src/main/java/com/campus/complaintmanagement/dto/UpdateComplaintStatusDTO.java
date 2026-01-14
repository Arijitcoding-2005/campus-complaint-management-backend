package com.campus.complaintmanagement.dto;

import com.campus.complaintmanagement.entity.Complaint;

public class UpdateComplaintStatusDTO {

    private Complaint.ComplaintStatus status;

    public Complaint.ComplaintStatus getStatus() {
        return status;
    }

    public void setStatus(Complaint.ComplaintStatus status) {
        this.status = status;
    }
}
