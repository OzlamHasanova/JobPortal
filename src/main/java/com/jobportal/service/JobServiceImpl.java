package com.jobportal.service;

import com.jobportal.dto.JobResponse;
import com.jobportal.mapper.JobMapper;
import com.jobportal.model.Job;
import com.jobportal.model.enums.ExperienceLevel;
import com.jobportal.model.enums.Industry;
import com.jobportal.model.enums.JobType;
import com.jobportal.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final JobMapper jobMapper;


    @Override
    public Page<JobResponse> getAllJobs(Pageable pageable) {
        Page<Job> jobs = jobRepository.findAll(pageable);
        return jobs.map(jobMapper::toResponse);
    }

    @Override
    public Page<JobResponse> filterJobs(String location, String jobType, String experienceLevel,
                                        String industry, String tags, Pageable pageable) {
        if (location != null) {
            return jobRepository.findByLocation_CityContaining(location, pageable)
                    .map(jobMapper::toResponse);
        } else if (jobType != null) {
            return jobRepository.findByJobType(JobType.valueOf(jobType), pageable)
                    .map(jobMapper::toResponse);
        } else if (experienceLevel != null) {
            return jobRepository.findByExperienceLevel(ExperienceLevel.valueOf(experienceLevel), pageable)
                    .map(jobMapper::toResponse);
        } else if (industry != null) {
            return jobRepository.findByIndustry(Industry.valueOf(industry), pageable)
                    .map(jobMapper::toResponse);
        } else if (tags != null) {
            return jobRepository.findByTagsContaining(tags, pageable)
                    .map(jobMapper::toResponse);
        }

        return getAllJobs(pageable);
    }

    @Override
    public Page<JobResponse> sortByPostedDate(Pageable pageable) {
        Page<Job> jobs = jobRepository.findByOrderByPostedDateDesc(pageable);
        return jobs.map(jobMapper::toResponse);
    }

    @Override
    public Page<JobResponse> sortBySalaryRange(BigDecimal minSalary, BigDecimal maxSalary, Pageable pageable) {
        Page<Job> jobs = jobRepository.findJobsWithinSalaryRange(minSalary, maxSalary, pageable);
        return jobs.map(jobMapper::toResponse);
    }

}
