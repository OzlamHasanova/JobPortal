package com.jobportal.model;

import com.jobportal.model.Location;
import com.jobportal.model.SalaryRange;
import com.jobportal.model.enums.EducationLevel;
import com.jobportal.model.enums.ExperienceLevel;
import com.jobportal.model.enums.Industry;
import com.jobportal.model.enums.JobType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobTitle;

    private String companyName;

    @Embedded
    private Location location;

    @Enumerated(EnumType.STRING)
    private JobType jobType;

    @Embedded
    private SalaryRange salaryRange;

    @Column(columnDefinition = "TEXT")
    private String jobDescription;

    @Column(columnDefinition = "TEXT")
    private String requirements;

    @Enumerated(EnumType.STRING)
    private ExperienceLevel experienceLevel;

    @Enumerated(EnumType.STRING)
    private EducationLevel educationLevel;

    @Enumerated(EnumType.STRING)
    private Industry industry;

    private LocalDate postedDate;

    private LocalDate applicationDeadline;

    private String howToApply;

    private String companyLogoUrl;

    @ElementCollection
    private List<String> benefits;

    @ElementCollection
    private List<String> tags;

    private String source;
}
