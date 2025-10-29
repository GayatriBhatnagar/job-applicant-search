package com.system.jobapplicantsearch.controller;

import com.system.jobapplicantsearch.dto.ApplicantRequestDto;
import com.system.jobapplicantsearch.dto.ApplicantResponseDto;
import com.system.jobapplicantsearch.pagination.PaginatedResponseDto;
import com.system.jobapplicantsearch.service.ApplicantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobapplicant")
@Tag(name= "Applicant Apis", description = "Manage Applicants")
public class ApplicantController {

    private final ApplicantService applicantService;

    public ApplicantController(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    @Operation(summary = "creates applicant")
    @ApiResponse(responseCode = "201", description = "created Applicant")
    @PostMapping("/createapplicant")
    public ResponseEntity<ApplicantResponseDto> createApplicant(@RequestBody @Valid ApplicantRequestDto applicantRequestDto){
        ApplicantResponseDto response = applicantService.createApplicant(applicantRequestDto);
   return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @Operation(summary="Get Applicants with Optional Filters ->  firstName, lastName, skills, minExperience")
    @ApiResponse(responseCode = "200", description = "gets all the applicants with given filters")
    @GetMapping("/getapplicantswithfilters")
    public ResponseEntity<PaginatedResponseDto<ApplicantResponseDto>> getApplicantsWithFilters(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) List<String> skill,
            @RequestParam(required = false) Integer minExperience,
            @RequestParam (defaultValue = "or")String filterType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize
    )
    {
        Pageable pageable = PageRequest.of(page, pageSize);
        PaginatedResponseDto<ApplicantResponseDto> applicantResponseDtoPage = applicantService.getApplicantsWithFilters(firstName, lastName, skill, minExperience, filterType, pageable);
        return ResponseEntity.ok(applicantResponseDtoPage);
    }

}
