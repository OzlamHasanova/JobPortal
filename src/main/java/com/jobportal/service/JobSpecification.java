package com.jobportal.service;

import com.jobportal.model.Job;
import com.jobportal.model.enums.ExperienceLevel;
import com.jobportal.model.enums.Industry;
import com.jobportal.model.enums.JobType;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class JobSpecification {

    public static Specification<Job> filterByLocation(String location) {
        return (root, query, cb) -> location == null ? null : cb.like(cb.lower(root.get("location").get("city")), "%" + location.toLowerCase() + "%");
    }

    public static Specification<Job> filterByJobType(String jobType) {
        return (root, query, cb) -> jobType == null ? null : cb.equal(root.get("jobType"), JobType.valueOf(jobType));
    }

    public static Specification<Job> filterByExperienceLevel(String experienceLevel) {
        return (root, query, cb) -> experienceLevel == null ? null : cb.equal(root.get("experienceLevel"), ExperienceLevel.valueOf(experienceLevel));
    }

    public static Specification<Job> filterByIndustry(String industry) {
        return (root, query, cb) -> industry == null ? null : cb.equal(root.get("industry"), Industry.valueOf(industry));
    }

    public static Specification<Job> filterByTags(String tags) {
        return (root, query, cb) -> tags == null ? null : cb.like(cb.lower(root.get("tags")), "%" + tags.toLowerCase() + "%");
    }

    public static Specification<Job> filterBySalaryRange(BigDecimal minSalary, BigDecimal maxSalary) {
        return (root, query, cb) -> (minSalary != null && maxSalary != null)
                ? cb.between(root.get("salary"), minSalary, maxSalary)
                : null;
    }
}
