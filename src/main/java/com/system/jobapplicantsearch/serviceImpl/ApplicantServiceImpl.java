package com.system.jobapplicantsearch.serviceImpl;

import com.system.jobapplicantsearch.dto.ApplicantRequestDto;
import com.system.jobapplicantsearch.dto.ApplicantResponseDto;
import com.system.jobapplicantsearch.entity.Applicant;
import com.system.jobapplicantsearch.mapper.ApplicantMapper;
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
    private final ApplicantMapper applicantMapper;

    public ApplicantServiceImpl(ApplicantRepo applicantRepo, ApplicantMapper applicantMapper) {
        this.applicantRepo = applicantRepo;
        this.applicantMapper = applicantMapper;
    }

    @Override
    public ApplicantResponseDto createApplicant(ApplicantRequestDto applicantRequestDto) {
        Applicant applicant = applicantMapper.toApplicant(applicantRequestDto);
        Applicant postApplicant= applicantRepo.save(applicant);
        return applicantMapper.toApplicantDto(postApplicant);
    }
   @Override
    public Page<ApplicantResponseDto> getApplicantsWithFilters(String firstName, String lastName, List<String> skill, Integer minExperience, String filterType, Pageable pageable) {
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

        return applicantRepo.findAll(specification, pageable).map(applicantMapper::toApplicantDto);

    }
}
