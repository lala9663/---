package com.example.wantedpreonboardingbackend.company.service;

import com.example.wantedpreonboardingbackend.company.dto.RegisterCompanyDto;
import com.example.wantedpreonboardingbackend.company.entity.Company;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CompanyServiceTest {

    @Autowired
    private CompanyService companyService;

    @Test
    @DisplayName("회사 등록 테스트")
    void 회사_등록_테스트() {
        // given
        RegisterCompanyDto registerCompanyDto = RegisterCompanyDto.builder()
                .name("CompanyE")
                .build();

        // when
        Long companyId = companyService.registerCompany(registerCompanyDto);

        // then
        Long expectedCompanyId = 5L;
        assertEquals(expectedCompanyId, companyId);
    }
}