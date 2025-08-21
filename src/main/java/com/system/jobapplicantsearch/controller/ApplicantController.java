package com.system.jobapplicantsearch.controller;

import com.system.jobapplicantsearch.dto.ApplicantRequestDto;
import com.system.jobapplicantsearch.dto.ApplicantResponseDto;
import com.system.jobapplicantsearch.service.ApplicantService;
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
public class ApplicantController {

    private final ApplicantService applicantService;

    public ApplicantController(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    @PostMapping("/createapplicant")
    public ResponseEntity<ApplicantResponseDto> createApplicant(@RequestBody @Valid ApplicantRequestDto applicantRequestDto){
        ApplicantResponseDto response = applicantService.createApplicant(applicantRequestDto);
   return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping("/getapplicantswithfilters")
    public ResponseEntity<Page<ApplicantResponseDto>> getApplicantsWithFilters(
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
        Page<ApplicantResponseDto> applicantResponseDtoPage = applicantService.getApplicantsWithFilters(firstName, lastName, skill, minExperience, filterType, pageable);
        return ResponseEntity.ok(applicantResponseDtoPage);
    }

}
