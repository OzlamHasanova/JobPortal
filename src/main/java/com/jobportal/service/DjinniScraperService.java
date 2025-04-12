package com.jobportal.service;

import com.jobportal.mapper.JobScraperMapper;
import com.jobportal.model.Job;
import com.jobportal.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class DjinniScraperService {

    private final JobRepository jobRepository;
    private final JobScraperMapper jobMapper;

    private static final String DJINNI_URL = "https://djinni.co/jobs/?page=";
    private static final int MAX_PAGES = 5;

    @Scheduled(cron = "0 0 2 * * *")
    public void scrapeAndStoreJobs() {
        log.info("[SCRAPER] Starting Djinni.co scraping...");
        List<Job> scrapedJobs = new ArrayList<>();

        try {
            int page = 1;
            boolean hasMorePages = true;

            while (hasMorePages && page <= MAX_PAGES) {
                String url = DJINNI_URL + page;
                log.info("[SCRAPER] Processing page: {}", page);

                Document doc = fetchDocument(url);
                if (doc == null) break;

                Elements jobElements = doc.select("li[id^=job-item-]");
                if (jobElements.isEmpty()) {
                    log.info("[SCRAPER] No more job elements found on page {}, stopping...", page);
                    break;
                }

                log.info("[SCRAPER] Found {} job elements on page {}", jobElements.size(), page);

                for (Element jobElement : jobElements) {
                    try {
                        Optional<Job> jobOptional = jobMapper.toJob(jobElement);
                        jobOptional.ifPresent(scrapedJobs::add);
                    } catch (Exception e) {
                        log.error("[SCRAPER] Error processing job element: {}", e.getMessage());
                    }
                }

                page++;
                TimeUnit.SECONDS.sleep(5);
            }

            if (!scrapedJobs.isEmpty()) {
                jobRepository.saveAll(scrapedJobs);
                log.info("[SCRAPER] Successfully saved {} jobs to database", scrapedJobs.size());
            } else {
                log.warn("[SCRAPER] No valid jobs found");
            }

        } catch (Exception e) {
            log.error("[SCRAPER] FATAL ERROR during scraping: {}", e.getMessage());
        }
    }

    private Document fetchDocument(String url) {
        try {
            return Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36")
                    .referrer("https://www.google.com")
                    .timeout(30000)
                    .get();
        } catch (Exception e) {
            log.error("[SCRAPER] Failed to fetch document: {}", e.getMessage());
            return null;
        }
    }
}
