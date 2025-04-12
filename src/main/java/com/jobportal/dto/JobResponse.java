package com.jobportal.dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class JobResponse {

    private Long id;
    private String jobTitle;
    private String companyName;
    private String salaryRange;
    private String jobDescription;
    private String experienceLevel;
    private LocalDate postedDate;
    private LocalDate applicationDeadline;
    private String howToApply;
    private String companyLogo;
    private String tag;
    private String source;

}
