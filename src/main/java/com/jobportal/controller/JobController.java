package com.jobportal.controller;

import com.jobportal.dto.JobResponse;
import com.jobportal.dto.PagedResponse;
import com.jobportal.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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


    @Operation(summary = "Get all jobs without sort")
    @GetMapping
    public ResponseEntity<PagedResponse<JobResponse>> getAllJobs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PagedResponse<JobResponse> jobs = jobService.getAllJobs(page, size);
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/filter")
    public ResponseEntity<PagedResponse<JobResponse>> filterJobs(@RequestParam(required = false) String location,
                                                                 @RequestParam(required = false) String jobType,
                                                                 @RequestParam(required = false) String experienceLevel,
                                                                 @RequestParam(required = false) String industry,
                                                                 @RequestParam(required = false) String tags,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(jobService.filterJobs(location, jobType, experienceLevel, industry, tags, pageable));
    }

    @GetMapping("/sort/posted-date")
    public ResponseEntity<PagedResponse<JobResponse>> sortByPostedDate(@RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "postedDate"));
        return ResponseEntity.ok(jobService.sortByPostedDate(pageable));
    }

    @GetMapping("/sort/salary-range")
    public ResponseEntity<PagedResponse<JobResponse>> sortBySalaryRange(@RequestParam BigDecimal minSalary,
                                                                        @RequestParam BigDecimal maxSalary,
                                                                        @RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(jobService.sortBySalaryRange(minSalary, maxSalary, pageable));
    }
}