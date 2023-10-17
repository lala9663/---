package com.example.wantedpreonboardingbackend.post.service;

import com.example.wantedpreonboardingbackend.company.entity.Company;
import com.example.wantedpreonboardingbackend.company.repository.CompanyRepository;
import com.example.wantedpreonboardingbackend.post.dto.PostResponseDto;
import com.example.wantedpreonboardingbackend.post.dto.RegisterPostDto;
import com.example.wantedpreonboardingbackend.post.entity.Post;
import com.example.wantedpreonboardingbackend.post.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Mock
    private PostRepository postRepository;
    @Mock
    private CompanyRepository companyRepository;

    @Test
    @DisplayName(("공고 등록"))
    void 공고등록_성공_테스트() {
        // given
        Company companyA = Company.builder()
                .companyName("Company A")
                .build();
        companyRepository.save(companyA);

        RegisterPostDto registerPostDto = RegisterPostDto.builder()
                .companyId(companyA.getId())
                .position("백엔드")
                .reward(10000)
                .content("신입 공채")
                .stacks(Set.of("자바", "코틀린", "파이썬"))
                .build();

        // when
        Long postId = postService.addRegisterJob(registerPostDto);

        // then
        assertNotNull(postId);

    }

    @Test
    @DisplayName(("포지션으로 검색"))
    void 포지션으로_검색() {
        // given
        String position = "데이터 분석";

        // 포지션으로 검색 시나리오를 설정합니다.
        when(postRepository.findByPosition(position))
                .thenReturn(Arrays.asList(
                        Post.builder()
                                .position(position)
                                .reward(50000)
                                .content("데이터 분석")
                                .stacks(new HashSet<>(Arrays.asList("Python", "R")))
                                .build(),
                        Post.builder()
                                .position(position)
                                .reward(55000)
                                .content("데이터 분석")
                                .stacks(new HashSet<>(Arrays.asList("Python", "SQL")))
                                .build()
                ));

        // when
        List<PostResponseDto> posts = postService.findByPosition(position);

        // then
        assertNotNull(posts);
        assertEquals(3, posts.size());
    }
}