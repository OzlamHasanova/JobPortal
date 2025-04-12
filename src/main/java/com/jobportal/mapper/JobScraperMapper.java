package com.jobportal.mapper;

import com.jobportal.model.*;
import com.jobportal.model.enums.*;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class JobScraperMapper {

    public Optional<Job> toJob(Element jobElement) {
        try {
            String title = jobElement.selectFirst("a.job-item__title-link").text();
            String company = jobElement.selectFirst("a.js-analytics-event").text();
            String logoUrl = Optional.ofNullable(jobElement.selectFirst(".userpic-image"))
                    .map(e -> e.attr("src")).orElse(null);

            SalaryRange salaryRange = parseSalary(jobElement);

            String locationText = jobElement.select(".location-text").text();
            String locationCountry = "Worldwide".equalsIgnoreCase(locationText) ? "Remote" : locationText;

            ExperienceLevel experienceLevel = determineExperienceLevel(jobElement);
            EducationLevel educationLevel = EducationLevel.BACHELOR;

            List<String> tags = new ArrayList<>();
            tags.add("Data Science");

            String description = Optional.ofNullable(jobElement.selectFirst(".js-original-text"))
                    .map(Element::text)
                    .orElseGet(() -> jobElement.selectFirst(".js-truncated-text").text());

            Job job = Job.builder()
                    .jobTitle(title)
                    .companyName(company)
                    .companyLogoUrl(logoUrl)
                    .salaryRange(salaryRange)
                    .location(Location.builder().country(locationCountry).build())
                    .jobType(JobType.PART_TIME)
                    .experienceLevel(experienceLevel)
                    .educationLevel(educationLevel)
                    .industry(Industry.TECH)
                    .jobDescription(description)
                    .requirements("")
                    .postedDate(LocalDate.now())
                    .applicationDeadline(LocalDate.now().plusDays(30))
                    .howToApply("Apply via Djinni.co")
                    .source("djinni.co")
                    .benefits(new ArrayList<>())
                    .tags(tags)
                    .build();

            return Optional.of(job);

        } catch (Exception e) {
            log.error("[MAPPER] Failed to parse job element: {}", e.getMessage());
            return Optional.empty();
        }
    }

    private SalaryRange parseSalary(Element jobElement) {
        try {
            Element salaryEl = jobElement.selectFirst("strong.text-success");
            if (salaryEl == null) return null;

            String salaryText = salaryEl.text().replace("to $", "").trim();
            BigDecimal maxSalary = new BigDecimal(salaryText);
            return new SalaryRange(null, maxSalary);

        } catch (Exception e) {
            log.warn("[MAPPER] Could not parse salary");
            return null;
        }
    }

    private ExperienceLevel determineExperienceLevel(Element jobElement) {
        for (Element span : jobElement.select("span.text-nowrap")) {
            String text = span.text().toLowerCase();
            if (text.contains("5 years") || text.contains("senior")) return ExperienceLevel.SENIOR;
            if (text.contains("2 years")) return ExperienceLevel.MID;
            if (text.contains("1 year") || text.contains("junior")) return ExperienceLevel.LEAD;
        }
        return ExperienceLevel.MID;
    }
}
