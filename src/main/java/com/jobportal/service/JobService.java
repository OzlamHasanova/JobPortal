package com.jobportal.service;

import com.jobportal.dto.JobResponse;
import com.jobportal.dto.PagedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface JobService {

    PagedResponse<JobResponse> getAllJobs(int page, int size);

    PagedResponse<JobResponse> filterJobs(String location,
                                          String jobType,
                                          String experienceLevel,
                                          String industry,
                                          String tags,
                                          Pageable pageable);

    PagedResponse<JobResponse> sortByPostedDate(Pageable pageable);

    PagedResponse<JobResponse> sortBySalaryRange(BigDecimal minSalary,
                                                 BigDecimal maxSalary,
                                                 Pageable pageable);
}