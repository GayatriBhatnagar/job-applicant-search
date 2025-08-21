package com.system.jobapplicantsearch.mapper;

import com.system.jobapplicantsearch.dto.ApplicantRequestDto;
import com.system.jobapplicantsearch.dto.ApplicantResponseDto;
import com.system.jobapplicantsearch.entity.Applicant;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApplicantMapper {
    
    public Applicant toApplicant(ApplicantRequestDto applicantRequestDto) ;

    ApplicantResponseDto toApplicantDto(Applicant postApplicant);
}
