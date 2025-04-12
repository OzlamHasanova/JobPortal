package com.jobportal.service;

import com.jobportal.dto.JobResponse;
import com.jobportal.dto.PagedResponse;
import com.jobportal.mapper.JobMapper;
import com.jobportal.model.Job;
import com.jobportal.model.enums.ExperienceLevel;
import com.jobportal.model.enums.Industry;
import com.jobportal.model.enums.JobType;
import com.jobportal.repository.JobRepository;
import lombok.RequiredArgsConstructor;
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
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final JobMapper jobMapper;


    @Override
    public PagedResponse<JobResponse> getAllJobs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Job> jobPage = jobRepository.findAll(pageable);
        List<JobResponse> content = jobPage.getContent().stream()
                .map(jobMapper::toResponse)
                .toList();
        return new PagedResponse<>(content, jobPage.getTotalPages(), jobPage.getTotalElements());
    }


    @Override
    public PagedResponse<JobResponse> filterJobs(String location, String jobType, String experienceLevel,
                                                 String industry, String tags, Pageable pageable) {

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

        return new PagedResponse<>(content, jobs.getTotalPages(), jobs.getTotalElements());
    }

    @Override
    public PagedResponse<JobResponse> sortByPostedDate(Pageable pageable) {
        Page<Job> jobs = jobRepository.findByOrderByPostedDateDesc(pageable);
        List<JobResponse> content = jobs.stream().map(jobMapper::toResponse).toList();

        return new PagedResponse<>(content, jobs.getTotalPages(), jobs.getTotalElements());
    }

    @Override
    public PagedResponse<JobResponse> sortBySalaryRange(BigDecimal minSalary, BigDecimal maxSalary, Pageable pageable) {
        Page<Job> jobs = jobRepository.findJobsWithinSalaryRange(minSalary, maxSalary, pageable);
        List<JobResponse> content = jobs.stream().map(jobMapper::toResponse).toList();

        return new PagedResponse<>(content, jobs.getTotalPages(), jobs.getTotalElements());
    }

}
