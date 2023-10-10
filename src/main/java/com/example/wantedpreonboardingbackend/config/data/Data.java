package com.example.wantedpreonboardingbackend.config.data;

import com.example.wantedpreonboardingbackend.board.entity.JobPost;
import com.example.wantedpreonboardingbackend.board.repository.JobPostRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;

@Component
public class Data {
    private final JobPostRepository jobPostRepository;

    public Data(JobPostRepository jobPostRepository) {
        this.jobPostRepository = jobPostRepository;
    }

    @PostConstruct
    public void init() {
        JobPost jobPost1 = JobPost.builder()
                .companyName("CompanyA")
                .position("Software Engineer")
                .reward(80000)
                .content("We are hiring a software engineer.")
                .stacks(Set.of("Java", "Spring Boot", "MySQL"))
                .build();

        JobPost jobPost2 = JobPost.builder()
                .companyName("CompanyB")
                .position("Data Scientist")
                .reward(90000)
                .content("We are looking for a data scientist.")
                .stacks(Set.of("Python", "Machine Learning", "TensorFlow"))
                .build();

        JobPost jobPost3 = JobPost.builder()
                .companyName("CompanyC")
                .position("Front-end Developer")
                .reward(75000)
                .content("Join our front-end development team.")
                .stacks(Set.of("HTML", "CSS", "JavaScript"))
                .build();

        jobPostRepository.saveAll(List.of(jobPost1, jobPost2, jobPost3));
    }
}

