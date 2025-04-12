package com.jobportal.dto;


import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class JobResponse {

    private Long id;
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
    private List<String> benefits;
    private String tags;
    private String source;

}
