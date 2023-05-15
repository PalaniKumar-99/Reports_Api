package com.example.ReportsApi.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "Eligibility_details")
public class EligibilityDetails {

    @Id
    @GeneratedValue
    private Integer eligibilityId;
    private String name;
    private long mobileNo;
    private String email;
    private Character gender;
    private Long ssn;
    private String planName;
    private String planStatus;
    private LocalDate planStartDate;
    private LocalDate planEndDate;
    private LocalDate planCreatedDate;
    private LocalDate planUpdatedDate;
    private String createdBy;
    private String updatedBy;
}
