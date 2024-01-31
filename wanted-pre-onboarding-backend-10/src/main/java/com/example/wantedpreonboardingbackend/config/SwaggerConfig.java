package com.example.wantedpreonboardingbackend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "채용 공고",
                description = "레이어드 아키텍처 기반 채용 공고 명세서",
                version = "v1"))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {
    // SwaggerConfig.java
    @Bean
    public GroupedOpenApi chatOpenApi() {
        String[] paths = {"/**"};

        return GroupedOpenApi.builder()
                .group("채용 공고 사이트")
                .pathsToMatch(paths)
                .build();
    }
}
