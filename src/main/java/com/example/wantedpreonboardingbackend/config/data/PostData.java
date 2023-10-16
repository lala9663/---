package com.example.wantedpreonboardingbackend.config.data;

import com.example.wantedpreonboardingbackend.company.entity.Company;
import com.example.wantedpreonboardingbackend.company.repository.CompanyRepository;
import com.example.wantedpreonboardingbackend.post.entity.Post;
import com.example.wantedpreonboardingbackend.post.repository.PostRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class PostData {
    private final PostRepository postRepository;
    private final CompanyRepository companyRepository;

    public PostData(PostRepository postRepository, CompanyRepository companyRepository) {
        this.postRepository = postRepository;
        this.companyRepository = companyRepository;
    }

    @PostConstruct
    public void init() {
        List<Post> Posts = new ArrayList<>();

        Company companyA = Company.builder()
                .companyName("CompanyA")
                .build();

        Company companyB = Company.builder()
                .companyName("CompanyB")
                .build();

        Company companyC = Company.builder()
                .companyName("CompanyC")
                .build();

        Company companyD = Company.builder()
                .companyName("CompanyD")
                .build();

        companyRepository.saveAll(List.of(companyA, companyB, companyC, companyD));


        Posts.add(Post.builder()
                .companyName(companyA)
                .position("Software Engineer")
                .reward(80000)
                .content("We are hiring a software engineer.")
                .stacks(Set.of("Java", "Spring Boot", "MySQL"))
                .build());

        Posts.add(Post.builder()
                .companyName(companyB)
                .position("Data Scientist")
                .reward(90000)
                .content("We are looking for a data scientist.")
                .stacks(Set.of("Python", "Machine Learning", "TensorFlow"))
                .build());

        Posts.add(Post.builder()
                .companyName(companyC)
                .position("Front-end Developer")
                .reward(75000)
                .content("Join our front-end development team.")
                .stacks(Set.of("HTML", "CSS", "JavaScript"))
                .build());

        Posts.add(Post.builder()
                .companyName(companyD)
                .position("Back-end Developer")
                .reward(80000)
                .content("Join our back-end development team.")
                .stacks(Set.of("Java", "Spring Boot", "SQL"))
                .build());

        Posts.add(Post.builder()
                .companyName(companyB)
                .position("UI/UX Designer")
                .reward(70000)
                .content("Join our UI/UX design team.")
                .stacks(Set.of("UI Design", "User Research"))
                .build());

        Posts.add(Post.builder()
                .companyName(companyC)
                .position("Data Scientist")
                .reward(90000)
                .content("Join our data science team.")
                .stacks(Set.of("Python", "Machine Learning"))
                .build());

        Posts.add(Post.builder()
                .companyName(companyD)
                .position("UI/UX Designer")
                .reward(80000)
                .content("Join our back-end development team.")
                .stacks(Set.of("Java", "Spring Boot", "SQL"))
                .build());

        Posts.add(Post.builder()
                .companyName(companyA)
                .position("UI/UX Designer")
                .reward(70000)
                .content("Join our UI/UX design team.")
                .stacks(Set.of("UI Design", "User Research"))
                .build());

        Posts.add(Post.builder()
                .companyName(companyA)
                .position("Data Scientist")
                .reward(90000)
                .content("Join our data science team.")
                .stacks(Set.of("Python", "Machine Learning"))
                .build());

        postRepository.saveAll(Posts);
    }
}

