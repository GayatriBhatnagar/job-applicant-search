package com.system.jobapplicantsearch.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.jobapplicantsearch.dto.ApplicantRequestDto;
import com.system.jobapplicantsearch.dto.ApplicantResponseDto;
import com.system.jobapplicantsearch.entity.Applicant;
import com.system.jobapplicantsearch.pagination.PaginatedResponseDto;
import com.system.jobapplicantsearch.service.ApplicantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ApplicantController.class)
@Import(MockedServiceConfig.class)
class ApplicantControllerTest {

   @Autowired
   MockMvc mockMvc;

   @Autowired
    ApplicantService applicantService;

   @Autowired
    ObjectMapper objectMapper;

   private ApplicantRequestDto applicantRequestDto;
   private Applicant applicant;
   private ApplicantResponseDto applicantResponseDto;
   private PaginatedResponseDto paginatedResponseDto;

   @BeforeEach
   void setUp(){

       applicantRequestDto = ApplicantRequestDto.builder().firstName("John").lastName("Doe").applicantId(1).skills("java").minExperience(3).build();
       applicantResponseDto = ApplicantResponseDto.builder().firstName("John").lastName("Doe").applicantId(1).skills("java").minExperience(3).build();
       applicant = Applicant.builder().firstName("John").lastName("Doe").applicantId(1).skills("java").minExperience(3).build();
       paginatedResponseDto= PaginatedResponseDto.builder().content(List.of(applicant)).page(0).size(10).totalElements(1).build();


   }


    @Test
    public void createApplicant_ReturnResponse() throws Exception{
       when(applicantService.createApplicant(applicantRequestDto)).thenReturn(applicantResponseDto);
       mockMvc.perform(post("/jobapplicant/createapplicant")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(applicantRequestDto)))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.firstName").value("John"));

    }

    @Test
    public void getApplicantWithFilters_ReturnResponse() throws Exception{
       String filterType = "or";
       List<ApplicantResponseDto> applicantList = List.of(applicantResponseDto);
       Page<ApplicantResponseDto> applicantPage = new PageImpl<>(applicantList);
       when(applicantService.getApplicantsWithFilters
               (eq(applicantRequestDto.getFirstName())
                       ,any(),
                       any(),
                       any(),any(),
                       any(Pageable.class))).thenReturn(paginatedResponseDto);

       mockMvc.perform(get("/jobapplicant/getapplicantswithfilters")
               .param("firstName", applicantRequestDto.getFirstName())
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.content[0].firstName").value("John"));


    }

   }







