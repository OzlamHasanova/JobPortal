package com.jobportal.repository;

import com.jobportal.model.Job;
import com.jobportal.model.enums.ExperienceLevel;
import com.jobportal.model.enums.Industry;
import com.jobportal.model.enums.JobType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface JobRepository extends JpaRepository <Job, Long> {

    Page<Job> findByLocation_CityContaining(String city, Pageable pageable);
    Page<Job> findByJobType(JobType jobType, Pageable pageable);
    Page<Job> findByExperienceLevel(ExperienceLevel experienceLevel, Pageable pageable);
    Page<Job> findByIndustry(Industry industry, Pageable pageable);
    Page<Job> findByTagsContaining(String tags, Pageable pageable);

    Page<Job> findByOrderByPostedDateDesc(Pageable pageable);

    @Query("SELECT j FROM Job j WHERE j.salaryRange.min >= :min AND j.salaryRange.max <= :max")
    Page<Job> findJobsWithinSalaryRange(@Param("min") BigDecimal min, @Param("max") BigDecimal max, Pageable pageable);

}
