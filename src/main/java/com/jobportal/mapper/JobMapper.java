package com.jobportal.mapper;

import com.jobportal.dto.JobResponse;
import com.jobportal.model.Job;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JobMapper {
    JobResponse toResponse(Job job);

}
