package com.jobportal.controller;

import com.jobportal.service.DjinniScraperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/scraper")
@RequiredArgsConstructor
public class JobScraperController {

    private final DjinniScraperService djinniScraperService;

    @PostMapping("/djinni")
    public ResponseEntity<String> fetchDjinniJobs() {
        djinniScraperService.scrapeAndStoreJobs();
        return ResponseEntity.ok("Djinni jobs successfully scraped and stored.");
    }
}

