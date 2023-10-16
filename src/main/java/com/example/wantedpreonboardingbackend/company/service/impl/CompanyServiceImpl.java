package com.example.wantedpreonboardingbackend.company.service.impl;

import com.example.wantedpreonboardingbackend.company.dto.RegisterCompanyDto;
import com.example.wantedpreonboardingbackend.company.entity.Company;
import com.example.wantedpreonboardingbackend.company.repository.CompanyRepository;
import com.example.wantedpreonboardingbackend.company.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;

    @Override
    public Long registerCompany(RegisterCompanyDto registerCompanyDto) {
        Company registerCompany = Company.builder()
                .companyName(registerCompanyDto.getName())
                .build();
        companyRepository.save(registerCompany);
        return registerCompany.getId();
    }
}
