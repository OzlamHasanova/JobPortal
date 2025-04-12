package com.jobportal.controller;

import com.jobportal.dto.JobResponse;
import com.jobportal.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;


    @GetMapping
    public Page<JobResponse> getAllJobs(Pageable pageable) {
        return jobService.getAllJobs(pageable);
    }

    @GetMapping("/filter")
    public Page<JobResponse> filterJobs(@RequestParam(required = false) String location,
                                        @RequestParam(required = false) String jobType,
                                        @RequestParam(required = false) String experienceLevel,
                                        @RequestParam(required = false) String industry,
                                        @RequestParam(required = false) String tags,
                                        Pageable pageable) {
        return jobService.filterJobs(location, jobType, experienceLevel, industry, tags, pageable);
    }

    @GetMapping("/sort/posted-date")
    public Page<JobResponse> sortByPostedDate(Pageable pageable) {
        return jobService.sortByPostedDate(pageable);
    }

    @GetMapping("/sort/salary-range")
    public Page<JobResponse> sortBySalaryRange(@RequestParam BigDecimal minSalary, @RequestParam BigDecimal maxSalary, Pageable pageable) {
        return jobService.sortBySalaryRange(minSalary, maxSalary, pageable);
    }
}
