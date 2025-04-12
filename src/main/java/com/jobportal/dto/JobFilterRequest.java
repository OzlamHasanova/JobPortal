package com.jobportal.dto;

import com.jobportal.model.enums.ExperienceLevel;
import com.jobportal.model.enums.Industry;
import com.jobportal.model.enums.JobType;
import lombok.Data;

import java.util.List;

@Data
public class JobFilterRequest {
    private String location;
    private JobType jobType;
    private ExperienceLevel experienceLevel;
    private Industry industry;
    private List<String> tags;
}

