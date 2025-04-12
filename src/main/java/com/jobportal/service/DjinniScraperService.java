package com.jobportal.service;

import com.jobportal.model.Job;
import com.jobportal.model.Location;
import com.jobportal.model.SalaryRange;
import com.jobportal.model.enums.*;
import com.jobportal.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class DjinniScraperService {

    private final JobRepository jobRepository;
    private static final String DJINNI_URL = "https://djinni.co/jobs/?page=";
    private static final int MAX_PAGES = 5; // Only scrape the first 5 pages

    @Scheduled(cron = "0 0 2 * * *") // Runs every day at 2:00 AM
    public void scrapeAndStoreJobs() {
        log.info("[SCRAPER] Starting Djinni.co scraping...");
        List<Job> scrapedJobs = new ArrayList<>();

        try {
            int page = 1;
            boolean hasMorePages = true;

            // Limit the scraping to 5 pages
            while (hasMorePages && page <= MAX_PAGES) {
                String url = DJINNI_URL + page;
                log.info("[SCRAPER] Processing page: {}", page);

                Document doc = null;
                try {
                    doc = Jsoup.connect(url)
                            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36")
                            .referrer("https://www.google.com")
                            .timeout(30000)
                            .get();
                } catch (IOException e) {
                    log.error("[SCRAPER] Network or timeout error: {}", e.getMessage());
                    break; // Stop scraping if there's a connection issue
                }

                Elements jobElements = doc.select("li[id^=job-item-]");
                if (jobElements.isEmpty()) {
                    log.info("[SCRAPER] No more job elements found on page {}, stopping...", page);
                    hasMorePages = false; // Stop scraping if there are no jobs found on the page
                    break;
                }

                log.info("[SCRAPER] Found {} job elements on page {}", jobElements.size(), page);

                for (Element jobElement : jobElements) {
                    try {
                        Optional<Job> jobOptional = parseJobElement(jobElement);
                        if (jobOptional.isPresent()) {
                            Job job = jobOptional.get();
                            if (isJobRecent(job.getPostedDate())) {
                                scrapedJobs.add(job);
                                log.info("[SCRAPER] Successfully parsed job: {}", job.getJobTitle());
                            } else {
                                hasMorePages = false;
                                log.info("[SCRAPER] Job '{}' is older than 1 day, skipping...", job.getJobTitle());
                            }
                        }
                    } catch (Exception e) {
                        log.error("[SCRAPER] Error processing job element: {}", e.getMessage());
                    }
                }

                page++;
                TimeUnit.SECONDS.sleep(5);
            }

            // Save all the scraped jobs to the database
            if (!scrapedJobs.isEmpty()) {
                jobRepository.saveAll(scrapedJobs);
                log.info("[SCRAPER] Successfully saved {} jobs to database", scrapedJobs.size());
            } else {
                log.error("[SCRAPER] FAILURE: No valid jobs found");
            }

        } catch (Exception e) {
            log.error("[SCRAPER] FATAL ERROR during scraping: {}", e.getMessage());
        }
    }

    private Optional<Job> parseJobElement(Element jobElement) {
        try {
            String jobTitle = jobElement.selectFirst("h2 a.job-item__title-link").text().trim();
            String jobLink = "https://djinni.co" + jobElement.selectFirst("h2 a.job-item__title-link").attr("href");

            String companyName = jobElement.selectFirst(".userpic-wrapper + a").text().trim();

            String companyLogoUrl = Optional.ofNullable(jobElement.selectFirst(".userpic-image_img"))
                    .map(img -> img.attr("src"))
                    .orElse(null);

            String jobDescription = Optional.ofNullable(jobElement.selectFirst(".js-original-text"))
                    .map(Element::text)
                    .orElse("");

            String location = jobElement.selectFirst(".location-text") != null
                    ? jobElement.selectFirst(".location-text").text().trim()
                    : "Worldwide";

            List<String> tags = new ArrayList<>();
            for (Element el : jobElement.select(".fw-medium span.text-nowrap")) {
                tags.add(el.text().trim());
            }

            String experienceText = tags.stream()
                    .filter(t -> t.contains("year"))
                    .findFirst().orElse("0");

            ExperienceLevel experienceLevel = experienceText.contains("2") ? ExperienceLevel.MID : ExperienceLevel.ENTRY;

            String englishLevel = tags.stream()
                    .filter(t -> t.equalsIgnoreCase("Upper-Intermediate"))
                    .findFirst().orElse(null);

            JobType jobType = tags.contains("Part-time") ? JobType.PART_TIME : JobType.FULL_TIME;

            // Salary
            String salaryText = jobElement.selectFirst("strong.text-success") != null
                    ? jobElement.selectFirst("strong.text-success").text().replace("to", "").replace("$", "").trim()
                    : null;
            SalaryRange salaryRange = null;
            if (salaryText != null && salaryText.matches("\\d+")) {
                salaryRange = SalaryRange.builder()
                        .min(new BigDecimal(salaryText))
                        .max(new BigDecimal(salaryText))

                        .build();
            }

            // Posted Date
            String posted = jobElement.selectFirst("span[data-original-title]") != null
                    ? jobElement.selectFirst("span[data-original-title]").attr("data-original-title")
                    : null;
            LocalDate postedDate = posted != null ? LocalDate.parse(posted, DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy")) : LocalDate.now();

            Job job = Job.builder()
                    .jobTitle(jobTitle)
                    .companyName(companyName)
                    .companyLogoUrl(companyLogoUrl)
                    .jobDescription(jobDescription)
                    .jobType(jobType)
                    .salaryRange(salaryRange)
                    .experienceLevel(experienceLevel)
                    .educationLevel(EducationLevel.BACHELOR) // optional
                    .industry(Industry.TECH) // or parse if available
                    .location(new Location(location, "Remote"))
                    .postedDate(postedDate)
                    .applicationDeadline(postedDate.plusDays(30))
                    .howToApply(jobLink)
                    .tags(tags)
                    .source("Djinni")
                    .build();

            return Optional.of(job);
        } catch (Exception e) {
            log.error("[SCRAPER] Error parsing job element: {}", e.getMessage());
            return Optional.empty();
        }
    }


    private boolean isJobRecent(LocalDate postedDate) {
        if (postedDate == null) {
            log.warn("[SCRAPER] Skipping job with null posted date.");
            return false;
        }

        LocalDate yesterday = LocalDate.now().minusDays(1); // Only allow jobs from the last 24 hours
        return !postedDate.isBefore(yesterday);
    }

    private LocalDate parsePostedDate(String timeText) {
        if (timeText.contains("today")) {
            return LocalDate.now();
        }
        if (timeText.contains("yesterday")) {
            return LocalDate.now().minusDays(1);
        }
        if (timeText.contains("h")) { // X hours ago
            int hours = Integer.parseInt(timeText.replaceAll("\\D+", ""));
            return hours < 24 ? LocalDate.now() : LocalDate.now().minusDays(1);
        }
        if (timeText.contains("d")) { // X days ago
            int days = Integer.parseInt(timeText.replaceAll("\\D+", ""));
            return LocalDate.now().minusDays(days);
        }
        return LocalDate.now(); // Default to today in case of any unrecognized format
    }

    private Location parseLocation(String locationText) {
        String[] parts = locationText.split(",");
        String city = parts.length > 0 ? parts[0].trim() : "Unknown City";
        String state = parts.length > 1 ? parts[1].trim() : "Unknown State";
        String country = parts.length > 2 ? parts[2].trim() : "Unknown Country";
        return new Location(city, state, country, WorkArrangement.REMOTE); // Assuming remote by default
    }

    private SalaryRange parseSalaryRange(String salaryText) {
        try {
            String[] salaryParts = salaryText.replaceAll("[^0-9.]", "").split("-");
            BigDecimal minSalary = new BigDecimal(salaryParts[0].trim());
            BigDecimal maxSalary = salaryParts.length > 1 ? new BigDecimal(salaryParts[1].trim()) : minSalary;
            return new SalaryRange(minSalary, maxSalary);
        } catch (Exception e) {
            log.warn("[SCRAPER] Invalid salary format: {}", salaryText);
            return new SalaryRange(BigDecimal.ZERO, BigDecimal.ZERO);
        }
    }
}
