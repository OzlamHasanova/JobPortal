package com.jobportal.model.enums;

import org.springframework.data.domain.Sort;

public enum SortOption {
    POSTED_DATE, SALARY;

    public static Sort toSpringSort(SortOption sortOption) {
        return switch (sortOption) {
            case POSTED_DATE -> Sort.by("postedDate").descending();
            case SALARY -> Sort.by("salaryRange.max").descending();
        };
    }
}

