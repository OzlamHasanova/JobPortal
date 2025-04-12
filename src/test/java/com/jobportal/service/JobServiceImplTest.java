package com.jobportal.service;

import com.jobportal.dto.JobResponse;
import com.jobportal.dto.PagedResponse;
import com.jobportal.mapper.JobMapper;
import com.jobportal.model.Job;
import com.jobportal.model.Location;
import com.jobportal.model.enums.ExperienceLevel;
import com.jobportal.model.enums.Industry;
import com.jobportal.model.enums.JobType;
import com.jobportal.repository.JobRepository;
import com.jobportal.exception.JobNotFoundException;
import com.jobportal.exception.InvalidJobTypeException;
import com.jobportal.exception.InvalidJobSalaryRangeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JobServiceImplTest {

//    @Mock
//    private JobRepository jobRepository;
//
//    @Mock
//    private JobMapper jobMapper;
//
//    @InjectMocks
//    private JobServiceImpl jobService;
//
//    private Pageable pageable;
//    private Job job;
//    private JobResponse jobResponse;
//
//    @BeforeEach
//    void setUp() {
//        pageable = PageRequest.of(0, 10);
//        job = new Job();
//        job.setId(1L);
//        job.setLocation(new Location("New York", null));
//        job.setJobType(JobType.FULL_TIME);
//        job.setExperienceLevel(ExperienceLevel.SENIOR);
//        job.setIndustry(Industry.TECH);
//
//        jobResponse = new JobResponse();
//        jobResponse.setId(1L);
//        jobResponse.setJobTitle("Software Developer");
//        jobResponse.setCompanyName("Tech Corp");
//        jobResponse.setExperienceLevel("SENIOR");
//        jobResponse.setSalaryRange("50000 - 70000");
//    }
//
//    @Test
//    void getAllJobs_ShouldReturnPagedResponse() {
//        Page<Job> jobPage = new PageImpl<>(List.of(job), pageable, 1);
//        when(jobRepository.findAll(pageable)).thenReturn(jobPage);
//        when(jobMapper.toResponse(job)).thenReturn(jobResponse);
//
//        PagedResponse<JobResponse> response = jobService.getAllJobs(0, 10);
//
//        assertNotNull(response);
//        assertEquals(1, response.getTotalElements());
//        assertEquals(1, response.getContent().size());
//        verify(jobRepository, times(1)).findAll(pageable);
//    }
//
//    @Test
//    void filterJobs_ShouldReturnFilteredResults() {
//        Page<Job> jobPage = new PageImpl<>(List.of(job), pageable, 1);
//        when(jobRepository.findByLocation_CityContaining("New York", pageable)).thenReturn(jobPage);
//        when(jobMapper.toResponse(job)).thenReturn(jobResponse);
//
//        PagedResponse<JobResponse> response = jobService.filterJobs("New York", null, null, null, null, pageable);
//
//        assertNotNull(response);
//        assertEquals(1, response.getTotalElements());
//        assertEquals(1, response.getContent().size());
//        verify(jobRepository, times(1)).findByLocation_CityContaining("New York", pageable);
//    }
//
//    @Test
//    void filterJobs_InvalidJobType_ShouldThrowException() {
//        // Fix: Handling invalid job type
//        assertThrows(InvalidJobTypeException.class, () -> {
//            jobService.filterJobs(null, "INVALID", null, null, null, pageable);
//        });
//    }
//
//    @Test
//    void sortByPostedDate_ShouldReturnSortedJobs() {
//        Page<Job> jobPage = new PageImpl<>(List.of(job), pageable, 1);
//        when(jobRepository.findByOrderByPostedDateDesc(pageable)).thenReturn(jobPage);
//        when(jobMapper.toResponse(job)).thenReturn(jobResponse);
//
//        PagedResponse<JobResponse> response = jobService.sortByPostedDate(pageable);
//
//        assertNotNull(response);
//        assertEquals(1, response.getTotalElements());
//        assertEquals(1, response.getContent().size());
//        verify(jobRepository, times(1)).findByOrderByPostedDateDesc(pageable);
//    }
//
//    @Test
//    void sortBySalaryRange_ShouldReturnJobsWithinSalaryRange() {
//        Page<Job> jobPage = new PageImpl<>(List.of(job), pageable, 1);
//        when(jobRepository.findJobsWithinSalaryRange(new BigDecimal("50000"), new BigDecimal("70000"), pageable)).thenReturn(jobPage);
//        when(jobMapper.toResponse(job)).thenReturn(jobResponse);
//
//        PagedResponse<JobResponse> response = jobService.sortBySalaryRange(new BigDecimal("50000"), new BigDecimal("70000"), pageable);
//
//        assertNotNull(response);
//        assertEquals(1, response.getTotalElements());
//        assertEquals(1, response.getContent().size());
//        verify(jobRepository, times(1)).findJobsWithinSalaryRange(new BigDecimal("50000"), new BigDecimal("70000"), pageable);
//    }
//
//    @Test
//    void sortBySalaryRange_InvalidSalaryRange_ShouldThrowException() {
//        when(jobRepository.findJobsWithinSalaryRange(new BigDecimal("70000"), new BigDecimal("50000"), pageable))
//                .thenThrow(IllegalArgumentException.class);
//
//        assertThrows(InvalidJobSalaryRangeException.class,
//                () -> jobService.sortBySalaryRange(new BigDecimal("70000"), new BigDecimal("50000"), pageable));
//    }
//
//    @Test
//    void getAllJobs_ShouldThrowJobNotFoundException() {
//        when(jobRepository.findAll(pageable)).thenThrow(new RuntimeException("Database error"));
//
//        assertThrows(JobNotFoundException.class, () -> jobService.getAllJobs(0, 10));
//    }
}
