package com.jobportal.service;

import com.jobportal.mapper.JobScraperMapper;
import com.jobportal.model.Job;
import com.jobportal.repository.JobRepository;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class DjinniScraperServiceTest {

//    @Mock
//    private JobRepository jobRepository;
//
//    @Mock
//    private JobScraperMapper jobMapper;
//
//    @InjectMocks
//    private DjinniScraperService djinniScraperService;
//
//    @Mock
//    private Document mockDocument;
//
//    @Mock
//    private Element mockJobElement;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void scrapeAndStoreJobs_ShouldScrapeAndStoreJobsSuccessfully() throws InterruptedException {
//        // Arrange
//        String testUrl = "https://djinni.co/jobs/?page=1";
//        Elements mockElements = new Elements();
//        mockElements.add(mockJobElement);
//
//        when(jobMapper.toJob(mockJobElement)).thenReturn(Optional.of(new Job()));
//        when(djinniScraperService.fetchDocument(testUrl)).thenReturn(mockDocument);
//        when(mockDocument.select("li[id^=job-item-]")).thenReturn(mockElements);
//
//        // Act
//        djinniScraperService.scrapeAndStoreJobs();
//
//        // Assert
//        verify(jobMapper, times(1)).toJob(mockJobElement);
//        verify(jobRepository, times(1)).saveAll(Mockito.anyList());
//    }
//
//    @Test
//    void scrapeAndStoreJobs_ShouldHandleNoJobsFound() throws InterruptedException {
//        // Arrange
//        String testUrl = "https://djinni.co/jobs/?page=1";
//        Elements mockElements = new Elements();
//
//        when(djinniScraperService.fetchDocument(testUrl)).thenReturn(mockDocument);
//        when(mockDocument.select("li[id^=job-item-]")).thenReturn(mockElements);
//
//        // Act
//        djinniScraperService.scrapeAndStoreJobs();
//
//        // Assert
//        verify(jobRepository, never()).saveAll(Mockito.anyList());
//    }
//
//    @Test
//    void scrapeAndStoreJobs_ShouldHandleExceptionDuringScraping() throws InterruptedException {
//        // Arrange
//        String testUrl = "https://djinni.co/jobs/?page=1";
//
//        when(djinniScraperService.fetchDocument(testUrl)).thenThrow(new RuntimeException("Scraping failed"));
//
//        // Act
//        djinniScraperService.scrapeAndStoreJobs();
//
//        // Assert
//        verify(jobRepository, never()).saveAll(Mockito.anyList());
//    }
//
//    @Test
//    void fetchDocument_ShouldReturnNullWhenExceptionOccurs() {
//        // Arrange
//        String testUrl = "https://djinni.co/jobs/?page=1";
//        when(djinniScraperService.fetchDocument(testUrl)).thenThrow(new RuntimeException("Error fetching document"));
//
//        // Act
//        Document result = djinniScraperService.fetchDocument(testUrl);
//
//        // Assert
//        assertNull(result);
//    }
//
//    @Test
//    void fetchDocument_ShouldReturnDocumentWhenNoError() {
//        // Arrange
//        String testUrl = "https://djinni.co/jobs/?page=1";
//        when(djinniScraperService.fetchDocument(testUrl)).thenReturn(mockDocument);
//
//        // Act
//        Document result = djinniScraperService.fetchDocument(testUrl);
//
//        // Assert
//        assertNotNull(result);
//    }
}
