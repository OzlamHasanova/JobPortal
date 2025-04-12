package com.jobportal.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class JobFilterRequest {
    private String location;
    private String jobType;
    private String experienceLevel;
    private String industry;
    private String tags;
    private BigDecimal minSalary;
    private BigDecimal maxSalary;
}
