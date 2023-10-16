package com.example.wantedpreonboardingbackend.company.repository;

import com.example.wantedpreonboardingbackend.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findByCompanyName(String companyName);
}
