package com.system.jobapplicantsearch.repository;

import com.system.jobapplicantsearch.entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicantRepo extends JpaRepository<Applicant, Integer> , JpaSpecificationExecutor<Applicant> {




}
