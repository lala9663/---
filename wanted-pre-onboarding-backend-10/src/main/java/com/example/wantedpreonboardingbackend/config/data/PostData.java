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
        List<Post> posts = new ArrayList<>();

        Company companyA = Company.builder()
                .companyName("companyA")
                .build();

        Company companyB = Company.builder()
                .companyName("companyB")
                .build();

        Company companyC = Company.builder()
                .companyName("companyC")
                .build();

        Company companyD = Company.builder()
                .companyName("companyD")
                .build();

        companyRepository.saveAll(List.of(companyA, companyB, companyC, companyD));


        posts.add(Post.builder()
                .company(companyA)
                .position("소프트웨어 엔지니어")
                .reward(80000)
                .content("소프트웨어 엔지니어를 구하고 있습니다.")
                .stacks(Set.of("Java", "Spring Boot", "MySQL"))
                .build());

        posts.add(Post.builder()
                .company(companyB)
                    .position("데이터 분석")
                .reward(90000)
                .content("데이터 분석")
                .stacks(Set.of("Python", "Machine Learning", "TensorFlow"))
                .build());

        posts.add(Post.builder()
                .company(companyC)
                .position("프론트엔드 개발자")
                .reward(75000)
                .content("신입 프론트엔드 개발자 구하는 중입니다.")
                .stacks(Set.of("HTML", "CSS", "JavaScript"))
                .build());

        posts.add(Post.builder()
                .company(companyD)
                .position("백엔드 개발자")
                .reward(80000)
                .content("경력 백엔드 개발자 구하는 중입니다.")
                .stacks(Set.of("Java", "Spring Boot", "SQL"))
                .build());

        posts.add(Post.builder()
                .company(companyB)
                .position("UI/UX 디자이너")
                .reward(70000)
                .content("UI/UX 구합니다.")
                .stacks(Set.of("UI"))
                .build());

        posts.add(Post.builder()
                .company(companyC)
                .position("데이터 분석")
                .reward(90000)
                .content("데이터 분석 시니어 구합니다.")
                .stacks(Set.of("Python", "Machine Learning"))
                .build());

        posts.add(Post.builder()
                .company(companyD)
                .position("UI/UX Designer")
                .reward(80000)
                .content("Join our back-end development team.")
                .stacks(Set.of("Java", "Spring Boot", "SQL"))
                .build());

        posts.add(Post.builder()
                .company(companyA)
                .position("UI/UX")
                .reward(70000)
                .content("어서오세요.")
                .stacks(Set.of("UI", "UX"))
                .build());

        posts.add(Post.builder()
                .company(companyA)
                .position("데이터 분석")
                .reward(90000)
                .content("환영합니다.")
                .stacks(Set.of("Python", "Machine Learning"))
                .build());

        postRepository.saveAll(posts);
    }
}

