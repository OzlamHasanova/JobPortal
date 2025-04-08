package com.jobportal.model;

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
    @OneToOne(cascade = CascadeType.ALL)
    private Location location;
    private String jobType;
    private String salaryRange;
    private String jobDescription;// todo

//    @Lob
    private String requirements;

    private String experienceLevel;
    private String educationLevel;
    private String industry;

    private LocalDate postedDate;
    private LocalDate applicationDeadline;

    private String howToApply;
    private String companyLogo;
    private String benefits;

//    @ElementCollection
//    private List<String> tags;

    private String source;
}

