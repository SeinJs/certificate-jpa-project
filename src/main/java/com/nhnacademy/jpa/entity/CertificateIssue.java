package com.nhnacademy.jpa.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "certificate_issue")
public class CertificateIssue {
    @Id
    @Column(name = "certificate_confirmation_number")
    private Long certificateConfirmationNumber;

    @ManyToOne
    @JoinColumn(name = "resident_serial_number")
    private Resident resident;

    @NonNull
    @Column(name = "certificate_type_code")
    private String certificateTypeCode;

    @NonNull
    @Column(name = "certificate_issue_date")
    private Date certificateIssueDate;
}
