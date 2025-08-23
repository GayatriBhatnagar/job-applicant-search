package com.system.jobapplicantsearch.controller;

import com.system.jobapplicantsearch.service.ApplicantService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MockedServiceConfig {

    @Bean
    public ApplicantService applicantService(){
        return Mockito.mock(ApplicantService.class);
    }
}
