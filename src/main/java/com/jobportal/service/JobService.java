package com.jobportal.service;

import com.jobportal.dto.JobResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface JobService {

    Page<JobResponse> getAllJobs(Pageable pageable);

    Page<JobResponse> filterJobs(String location, String jobType, String experienceLevel,
                                 String industry, String tags, Pageable pageable);

    Page<JobResponse> sortByPostedDate(Pageable pageable);

    Page<JobResponse> sortBySalaryRange(BigDecimal minSalary, BigDecimal maxSalary, Pageable pageable);
}
