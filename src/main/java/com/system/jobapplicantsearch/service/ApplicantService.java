package com.system.jobapplicantsearch.service;

import com.system.jobapplicantsearch.dto.ApplicantRequestDto;
import com.system.jobapplicantsearch.dto.ApplicantResponseDto;
import com.system.jobapplicantsearch.pagination.PaginatedResponseDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ApplicantService {
  ApplicantResponseDto createApplicant(@Valid ApplicantRequestDto applicantRequestDto);

  PaginatedResponseDto<ApplicantResponseDto> getApplicantsWithFilters(String firstName, String lastName, List<String> skill, Integer minExperience, String filterType, Pageable pageable);
}
