package com.system.jobapplicantsearch.serviceImpl;

import com.system.jobapplicantsearch.dto.ApplicantRequestDto;
import com.system.jobapplicantsearch.dto.ApplicantResponseDto;
import com.system.jobapplicantsearch.entity.Applicant;
import com.system.jobapplicantsearch.mapper.ApplicantMapper;
import com.system.jobapplicantsearch.pagination.PaginatedResponseDto;
import com.system.jobapplicantsearch.repository.ApplicantRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplicantServiceImplTest {

    @Mock
    private ApplicantRepo applicantRepo;


    @Mock
    private ApplicantMapper applicantMapper;


    @InjectMocks
    private ApplicantServiceImpl applicantService;

    private ApplicantResponseDto applicantResponseDto;

    private ApplicantRequestDto applicantRequestDto;

    private Applicant applicant;

    private PaginatedResponseDto paginatedResponseDto;

    @BeforeEach
    void setUp() {

        applicantRequestDto = ApplicantRequestDto.builder().firstName("John").lastName("Doe").applicantId(1).skills("java").minExperience(3).build();
        applicantResponseDto = ApplicantResponseDto.builder().firstName("John").lastName("Doe").applicantId(1).skills("java").minExperience(3).build();
        applicant = Applicant.builder().firstName("John").lastName("Doe").applicantId(1).skills("java").minExperience(3).build();
        paginatedResponseDto= PaginatedResponseDto.builder().content(List.of(applicant)).page(0).size(10).totalElements(1).build();

    }

    @Test
    public void createApplicant_getResponse(){

        when(applicantMapper.toApplicant(applicantRequestDto)).thenReturn(applicant);
        when(applicantRepo.save(any(Applicant.class))).thenReturn(applicant);
        when(applicantMapper.toApplicantDto(applicant)).thenReturn(applicantResponseDto);
        ApplicantResponseDto entityResponse = applicantService.createApplicant(applicantRequestDto);

        assertEquals("John", entityResponse.getFirstName());

    }

    @Test
    public void getApplicantsWithFilters_getResponse(){
        String filterType = "or";
        List<Applicant> applicantList = List.of(applicant);
        Page<Applicant> applicantPage = new PageImpl<>(applicantList);
        Pageable pageable = PageRequest.of(0,10);
        when(applicantRepo.findAll(any(Specification.class), eq(pageable))).thenReturn(applicantPage);
        when(applicantMapper.toApplicantDto(applicant)).thenReturn(applicantResponseDto);
        PaginatedResponseDto<ApplicantResponseDto> paginatedResponse = applicantService.getApplicantsWithFilters(applicantRequestDto.getFirstName(),applicantRequestDto.getLastName(),List.of(applicantRequestDto.getSkills()),applicantRequestDto.getMinExperience(),
       filterType, pageable );

        assertEquals(1, paginatedResponse.getTotalElements());



    }
}