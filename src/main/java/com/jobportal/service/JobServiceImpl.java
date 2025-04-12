package com.jobportal.service;

import com.jobportal.dto.JobResponse;
import com.jobportal.dto.PagedResponse;
import com.jobportal.mapper.JobMapper;
import com.jobportal.model.Job;
import com.jobportal.model.enums.ExperienceLevel;
import com.jobportal.model.enums.Industry;
import com.jobportal.model.enums.JobType;
import com.jobportal.repository.JobRepository;
import com.jobportal.exception.JobNotFoundException;
import com.jobportal.exception.InvalidJobTypeException;
import com.jobportal.exception.InvalidJobSalaryRangeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final JobMapper jobMapper;

    @Override
    public PagedResponse<JobResponse> getAllJobs(int page, int size) {
        log.info("Fetching all jobs, page: {}, size: {}", page, size);
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Job> jobPage = jobRepository.findAll(pageable);
            List<JobResponse> content = jobPage.getContent().stream()
                    .map(jobMapper::toResponse)
                    .toList();
            log.info("Successfully fetched {} jobs", jobPage.getTotalElements());
            return new PagedResponse<>(content, jobPage.getTotalPages(), jobPage.getTotalElements());
        } catch (Exception e) {
            log.error("Error fetching all jobs", e);
            throw new JobNotFoundException("Could not fetch jobs", e);
        }
    }

    @Override
    public PagedResponse<JobResponse> filterJobs(String location, String jobType, String experienceLevel,
                                                 String industry, String tags, Pageable pageable) {
        log.info("Filtering jobs by location: {}, jobType: {}, experienceLevel: {}, industry: {}, tags: {}",
                location, jobType, experienceLevel, industry, tags);
        try {
            Map<BooleanSupplier, Supplier<Page<Job>>> filters = Map.of(
                    () -> location != null, () -> jobRepository.findByLocation_CityContaining(location, pageable),
                    () -> jobType != null, () -> jobRepository.findByJobType(JobType.valueOf(jobType), pageable),
                    () -> experienceLevel != null, () -> jobRepository.findByExperienceLevel(ExperienceLevel.valueOf(experienceLevel), pageable),
                    () -> industry != null, () -> jobRepository.findByIndustry(Industry.valueOf(industry), pageable),
                    () -> tags != null, () -> jobRepository.findByTagsContaining(tags, pageable)
            );

            Page<Job> jobs = filters.entrySet().stream()
                    .filter(entry -> entry.getKey().getAsBoolean())
                    .map(Map.Entry::getValue)
                    .map(Supplier::get)
                    .findFirst()
                    .orElse(Page.empty(pageable));

            List<JobResponse> content = jobs.stream()
                    .map(jobMapper::toResponse)
                    .toList();

            log.info("Successfully filtered jobs, total found: {}", jobs.getTotalElements());
            return new PagedResponse<>(content, jobs.getTotalPages(), jobs.getTotalElements());
        } catch (IllegalArgumentException e) {
            log.error("Invalid job type or other argument error", e);
            throw new InvalidJobTypeException("Invalid job type provided", e);
        } catch (Exception e) {
            log.error("Error filtering jobs", e);
            throw new JobNotFoundException("Error filtering jobs", e);
        }
    }

    @Override
    public PagedResponse<JobResponse> sortByPostedDate(Pageable pageable) {
        log.info("Sorting jobs by posted date, page: {}", pageable.getPageNumber());
        try {
            Page<Job> jobs = jobRepository.findByOrderByPostedDateDesc(pageable);
            List<JobResponse> content = jobs.stream().map(jobMapper::toResponse).toList();
            log.info("Successfully fetched sorted jobs by posted date, total found: {}", jobs.getTotalElements());
            return new PagedResponse<>(content, jobs.getTotalPages(), jobs.getTotalElements());
        } catch (Exception e) {
            log.error("Error sorting jobs by posted date", e);
            throw new RuntimeException("Error sorting jobs by posted date", e);
        }
    }

    @Override
    public PagedResponse<JobResponse> sortBySalaryRange(BigDecimal minSalary, BigDecimal maxSalary, Pageable pageable) {
        log.info("Sorting jobs by salary range: {} - {}, page: {}", minSalary, maxSalary, pageable.getPageNumber());
        try {
            Page<Job> jobs = jobRepository.findJobsWithinSalaryRange(minSalary, maxSalary, pageable);
            List<JobResponse> content = jobs.stream().map(jobMapper::toResponse).toList();
            log.info("Successfully fetched jobs within salary range, total found: {}", jobs.getTotalElements());
            return new PagedResponse<>(content, jobs.getTotalPages(), jobs.getTotalElements());
        } catch (Exception e) {
            log.error("Error sorting jobs by salary range", e);
            throw new InvalidJobSalaryRangeException("Invalid salary range provided", e);
        }
    }
}
