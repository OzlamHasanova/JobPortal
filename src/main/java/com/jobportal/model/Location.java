package com.jobportal.model;

import com.jobportal.model.enums.WorkArrangement;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {
    private String city;
    private String state;
    private String country;

    @Enumerated(EnumType.STRING)
    private WorkArrangement workArrangement;
}

