package com.system.jobapplicantsearch.serviceImpl;

import com.system.jobapplicantsearch.dto.ApplicantRequestDto;
import com.system.jobapplicantsearch.dto.ApplicantResponseDto;
import com.system.jobapplicantsearch.entity.Applicant;
import com.system.jobapplicantsearch.mapper.ApplicantMapper;
import com.system.jobapplicantsearch.pagination.PaginatedResponseDto;
import com.system.jobapplicantsearch.repository.ApplicantRepo;
import com.system.jobapplicantsearch.service.ApplicantService;
import com.system.jobapplicantsearch.specification.ApplicantSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicantServiceImpl implements ApplicantService {
    private final ApplicantRepo applicantRepo;
    private final ApplicantMapper applicationtMapper;

    public ApplicantServiceImpl(ApplicantRepo applicantRepo, ApplicantMapper applicationtMapper) {
        this.applicantRepo = applicantRepo;
        this.applicationtMapper = applicationtMapper;
    }

    @Override
    public ApplicantResponseDto createApplicant(ApplicantRequestDto applicantRequestDto) {
        Applicant applicant = applicationtMapper.toApplicant(applicantRequestDto);
        Applicant postApplicant= applicantRepo.save(applicant);
        return applicationtMapper.toApplicantDto(postApplicant);
    }

    @Override
    public PaginatedResponseDto<ApplicantResponseDto> getApplicantsWithFilters(String firstName, String lastName, List<String> skill, Integer minExperience, String filterType, Pageable pageable) {
        Specification<Applicant> specification = null;
        List<Specification<Applicant>> filters = new ArrayList<>();
        if(firstName!=null && !firstName.isEmpty()){
            filters.add(ApplicantSpecification.hasFirstName(firstName));
        }
        if(lastName!=null && !lastName.isEmpty()){
            filters.add(ApplicantSpecification.hasLastName(lastName));
        }
        if(minExperience!=null){
            filters.add(ApplicantSpecification.hasMinExperience(minExperience));
        }
        if(skill!=null && !skill.isEmpty()){
            for(String sk :skill)
            {
            filters.add(ApplicantSpecification.hasSkills(sk));
            }
        }
            for(Specification<Applicant> filter: filters) {
                if (specification == null) {
                    specification = filter;
                } else {
                 specification= "or".equalsIgnoreCase(filterType) ? specification.or(filter) : specification.and(filter);
                }
            }

if(specification==null){
    specification= (root, query, criteriaBuilder)-> criteriaBuilder.conjunction();
}
        Page<Applicant> pageableResponse = applicantRepo.findAll(specification, pageable);
        Page<ApplicantResponseDto> mappedResponseDto = pageableResponse.map(applicationtMapper::toApplicantDto);

       return new PaginatedResponseDto<>(
               mappedResponseDto.getContent(),
               mappedResponseDto.getNumber(),
               mappedResponseDto.getSize(),
               mappedResponseDto.getTotalElements());

    }
}
