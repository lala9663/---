package com.example.wantedpreonboardingbackend.config.data;

import com.example.wantedpreonboardingbackend.post.entity.JobPost;
import com.example.wantedpreonboardingbackend.post.repository.JobPostRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class JobPostData {
    private final JobPostRepository jobPostRepository;

    public JobPostData(JobPostRepository jobPostRepository) {
        this.jobPostRepository = jobPostRepository;
    }

    @PostConstruct
    public void init() {
        List<JobPost> jobPosts = new ArrayList<>();

        jobPosts.add(JobPost.builder()
                .companyName("CompanyA")
                .position("Software Engineer")
                .reward(80000)
                .content("We are hiring a software engineer.")
                .stacks(Set.of("Java", "Spring Boot", "MySQL"))
                .build());

        jobPosts.add(JobPost.builder()
                .companyName("CompanyB")
                .position("Data Scientist")
                .reward(90000)
                .content("We are looking for a data scientist.")
                .stacks(Set.of("Python", "Machine Learning", "TensorFlow"))
                .build());

        jobPosts.add(JobPost.builder()
                .companyName("CompanyC")
                .position("Front-end Developer")
                .reward(75000)
                .content("Join our front-end development team.")
                .stacks(Set.of("HTML", "CSS", "JavaScript"))
                .build());

        jobPosts.add(JobPost.builder()
                .companyName("CompanyA")
                .position("Back-end Developer")
                .reward(80000)
                .content("Join our back-end development team.")
                .stacks(Set.of("Java", "Spring Boot", "SQL"))
                .build());

        jobPosts.add(JobPost.builder()
                .companyName("CompanyB")
                .position("UI/UX Designer")
                .reward(70000)
                .content("Join our UI/UX design team.")
                .stacks(Set.of("UI Design", "User Research"))
                .build());

        jobPosts.add(JobPost.builder()
                .companyName("CompanyC")
                .position("Data Scientist")
                .reward(90000)
                .content("Join our data science team.")
                .stacks(Set.of("Python", "Machine Learning"))
                .build());

        jobPosts.add(JobPost.builder()
                .companyName("CompanyD")
                .position("Back-end Developer")
                .reward(80000)
                .content("Join our back-end development team.")
                .stacks(Set.of("Java", "Spring Boot", "SQL"))
                .build());

        jobPosts.add(JobPost.builder()
                .companyName("CompanyE")
                .position("UI/UX Designer")
                .reward(70000)
                .content("Join our UI/UX design team.")
                .stacks(Set.of("UI Design", "User Research"))
                .build());

        jobPosts.add(JobPost.builder()
                .companyName("CompanyA")
                .position("Data Scientist")
                .reward(90000)
                .content("Join our data science team.")
                .stacks(Set.of("Python", "Machine Learning"))
                .build());

        jobPostRepository.saveAll(jobPosts);
    }
}

