package com.jobportal.mapper;

import com.jobportal.dto.JobResponse;
import com.jobportal.model.Job;
import com.jobportal.model.SalaryRange;
import org.springframework.stereotype.Component;

@Component
public class JobMapper {

    public JobResponse toResponse(Job job) {
        JobResponse response = new JobResponse();
        response.setId(job.getId());
        response.setJobTitle(job.getJobTitle());
        response.setCompanyName(job.getCompanyName());

        SalaryRange salaryRange = job.getSalaryRange();
        if (salaryRange != null) {
            response.setSalaryRange(salaryRange.getMin() + " - " + salaryRange.getMax());
        }

        response.setJobDescription(job.getJobDescription());

        if (job.getExperienceLevel() != null) {
            response.setExperienceLevel(job.getExperienceLevel().name());
        }

        response.setPostedDate(job.getPostedDate());
        response.setApplicationDeadline(job.getApplicationDeadline());
        response.setHowToApply(job.getHowToApply());
        response.setCompanyLogo(job.getCompanyLogoUrl());

        if (job.getTags() != null && !job.getTags().isEmpty()) {
            response.setTag(job.getTags().get(0));
        }

        response.setSource(job.getSource());

        return response;
    }
}
