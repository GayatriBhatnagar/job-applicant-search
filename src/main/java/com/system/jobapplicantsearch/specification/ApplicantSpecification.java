package com.system.jobapplicantsearch.specification;

import com.system.jobapplicantsearch.entity.Applicant;
import org.springframework.data.jpa.domain.Specification;

public class ApplicantSpecification {

    public static Specification<Applicant> hasFirstName(String firstName){
      return  (root, query, cb)-> cb.like(cb.lower(root.get("firstName")), "%" + firstName.toLowerCase() +"%");
    }
    public static Specification<Applicant> hasLastName(String lastName){
        return  (root, query, cb)-> cb.like(cb.lower(root.get("lastName")), "%" + lastName.toLowerCase() +"%");
    }
    public  static Specification<Applicant> hasSkills(String skills){
        return  (root, query, cb)-> cb.like(cb.lower(root.get("skills")), "%" + skills.toLowerCase() +"%");
    }
    public static Specification<Applicant> hasMinExperience(Integer minExperience){
        return  (root, query, cb)-> cb.greaterThanOrEqualTo(root.get("minExperience"), minExperience);
    }
}
