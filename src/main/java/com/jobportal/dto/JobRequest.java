package com.jobportal.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class JobRequest {

    private String jobTitle;
    private String companyName;
    private String location;
    private String jobType;
    private String salaryRange;
    private String jobDescription;
    private String requirements;
    private String experienceLevel;
    private String educationLevel;
    private String industry;
    private LocalDate postedDate;
    private LocalDate applicationDeadline;
    private String howToApply;
    private String companyLogo;
    private String benefits;
    private String tags;
    private String source;

}

