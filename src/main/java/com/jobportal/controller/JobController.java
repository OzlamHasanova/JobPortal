package com.jobportal.controller;

import com.jobportal.dto.JobResponse;
import com.jobportal.dto.PagedResponse;
import com.jobportal.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
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


    @Operation(summary = "Get all jobs without sort",
            description = "Fetches a paginated list of all jobs without any sorting.")
    @GetMapping
    public ResponseEntity<PagedResponse<JobResponse>> getAllJobs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PagedResponse<JobResponse> jobs = jobService.getAllJobs(page, size);
        return ResponseEntity.ok(jobs);
    }

    @Operation(summary = "Filter jobs by various parameters",
            description = "Filters jobs based on location, " +
                    "job type, experience level, industry, or tags." +
                    " Any of these parameters can be omitted.")
    @GetMapping("/filter")
    public ResponseEntity<PagedResponse<JobResponse>> filterJobs(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String jobType,
            @RequestParam(required = false) String experienceLevel,
            @RequestParam(required = false) String industry,
            @RequestParam(required = false) String tags,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(jobService.filterJobs(location, jobType, experienceLevel, industry, tags, pageable));
    }

    @Operation(summary = "Sort jobs by posted date",
            description = "Fetches a paginated list of jobs sorted by the posted date in descending order.")
    @GetMapping("/sort/posted-date")
    public ResponseEntity<PagedResponse<JobResponse>> sortByPostedDate(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "postedDate"));
        return ResponseEntity.ok(jobService.sortByPostedDate(pageable));
    }

    @Operation(summary = "Sort jobs by salary range",
            description = "Fetches a paginated list of jobs within the specified salary range." +
                    " Minimum and maximum salary must be provided.")
    @GetMapping("/sort/salary-range")
    public ResponseEntity<PagedResponse<JobResponse>> sortBySalaryRange(
            @RequestParam BigDecimal minSalary,
            @RequestParam BigDecimal maxSalary,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(jobService.sortBySalaryRange(minSalary, maxSalary, pageable));
    }
}
