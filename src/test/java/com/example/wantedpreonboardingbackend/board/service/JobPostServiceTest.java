package com.example.wantedpreonboardingbackend.board.service;

import com.example.wantedpreonboardingbackend.board.dto.RegisterJobDto;
import com.example.wantedpreonboardingbackend.board.dto.UpdateJobDto;
import com.example.wantedpreonboardingbackend.board.entity.JobPost;
import com.example.wantedpreonboardingbackend.board.exception.JobPostException;
import com.example.wantedpreonboardingbackend.board.repository.JobPostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class JobPostServiceTest {
    @Autowired
    private JobPostService jobPostService;
    @Autowired
    private JobPostRepository jobPostRepository;

    @Test
    @DisplayName("공고 등록 테스트")
    void 공고_등록() {
        //given
        RegisterJobDto registerJobDto = RegisterJobDto.builder()
                .companyName("원티드")
                .position("백엔드")
                .reward(1000000)
                .content("신입 개발자를 뽑고 있습니다!!!!!")
                .stacks(Set.of("Java", "Spring Boot", "MariaDB"))
                .build();
        //when
        Long companyPostId = jobPostService.addRegisterJob(registerJobDto);

        // Then
        JobPost savedJobPost = jobPostService.getJobPostDetail(companyPostId);
        assertNotNull(savedJobPost);
        assertEquals("원티드", savedJobPost.getCompanyName());
        assertEquals("백엔드", savedJobPost.getPosition());
        assertEquals(1000000, savedJobPost.getReward());
        assertEquals("신입 개발자를 뽑고 있습니다!!!!!", savedJobPost.getContent());
        assertEquals(Set.of("Java", "Spring Boot", "MariaDB"), savedJobPost.getStacks());
    }

    @Test
    @DisplayName("공고 등록 실패 - 필수 필드 누락")
    void 공고_등록_실패_필드누락() {
        // given
        RegisterJobDto registerJobDto = RegisterJobDto.builder()
                .reward(1000000)
                .content("신입 개발자를 뽑고 있습니다!!!!!")
                .stacks(Set.of("Java", "Spring Boot", "MariaDB"))
                .build();

        // when and then
        assertThrows(JobPostException.missingRequiredField().getClass(), () -> jobPostService.addRegisterJob(registerJobDto));
    }


    @Test
    @DisplayName("공고 등록 실패 - 중복된 공고")
    void 공고_등록_실패_중복된공고() {
        // Given
        RegisterJobDto registerJobDto = RegisterJobDto.builder()
                .companyName("원티드")
                .position("백엔드")
                .reward(1000000)
                .content("신입 개발자를 뽑고 있습니다!!!!!")
                .stacks(Set.of("Java", "Spring Boot", "MariaDB"))
                .build();
        long companyPostId = jobPostService.addRegisterJob(registerJobDto);

        //when
        RegisterJobDto registerJobDto2 = RegisterJobDto.builder()
                .companyName("원티드")
                .position("백엔드")
                .reward(1000000)
                .content("신입 개발자를 뽑고 있습니다!!!!!")
                .stacks(Set.of("Java", "Spring Boot", "MariaDB"))
                .build();
        long companyPostId2 = jobPostService.addRegisterJob(registerJobDto);

        //then
        assertThrows(JobPostException.duplicateJob().getClass(), () -> jobPostService.addRegisterJob(registerJobDto2));
    }


    @Test
    @DisplayName("모든 공고 조회")
    void 모든_공고_조회() {
        // given
        List<JobPost> fakeJobPosts = new ArrayList<>();
        fakeJobPosts.add(new JobPost(1L, "1번 회사", "백엔드", 300000, "내용1", Set.of("자바")));
        fakeJobPosts.add(new JobPost(2L, "2번 회사", "프론트엔드", 60000, "내용2", Set.of("자바스크립트")));

        when(jobPostRepository.findAll()).thenReturn(fakeJobPosts);

        // when
        List<JobPost> allJobPosts = jobPostService.getAllJobs();

        // then
        assertEquals(fakeJobPosts.size(), allJobPosts.size());
        for (int i = 0; i < fakeJobPosts.size(); i++) {
            JobPost expected = fakeJobPosts.get(i);
            JobPost actual = allJobPosts.get(i);
            assertEquals(expected.getCompanyName(), actual.getCompanyName());
            assertEquals(expected.getPosition(), actual.getPosition());
            assertEquals(expected.getReward(), actual.getReward());
            assertEquals(expected.getContent(), actual.getContent());
            assertEquals(expected.getStacks(), actual.getStacks());
        }
    }

    @Test
    @DisplayName("회사별 공고 조회")
    void 회사명으로_검색_성공() {
        // given
        String companyName = "테스트회사";
        JobPost jobPost1 = new JobPost(1L, companyName, "테스트포지션1", 10000, "테스트내용1", Set.of("자바"));
        JobPost jobPost2 = new JobPost(2L, "테스트회사", "테스트포지션2", 20000, "테스트내용2", Set.of("파이썬"));
        JobPost jobPost3 = new JobPost(2L, "다른회사", "테스트포지션2", 20000, "테스트내용2", Set.of("파이썬"));
        List<JobPost> expectedJobPosts = Arrays.asList(jobPost1, jobPost2, jobPost3);

        when(jobPostRepository.findByCompanyName(companyName)).thenReturn(expectedJobPosts);

        // when
        List<JobPost> actualJobPosts = jobPostService.getJobsByCompany(companyName);

        // then
        assertEquals(expectedJobPosts, actualJobPosts);
    }

    @Test
    @DisplayName("회사별 공고 조회")
    void 회사명으로_검색_실패() {
        // given
        String companyName = "테스트회사";
        JobPost jobPost1 = new JobPost(1L, companyName, "테스트포지션1", 10000, "테스트내용1", Set.of("자바"));
        JobPost jobPost2 = new JobPost(2L, "테스트회사", "테스트포지션2", 20000, "테스트내용2", Set.of("파이썬"));
        JobPost jobPost3 = new JobPost(2L, "다른회사", "테스트포지션2", 20000, "테스트내용2", Set.of("파이썬"));
        List<JobPost> expectedJobPosts = Arrays.asList(jobPost1, jobPost2, jobPost3);

        when(jobPostRepository.findByCompanyName(companyName)).thenReturn(expectedJobPosts);

        // when
        List<JobPost> actualJobPosts = jobPostService.getJobsByCompany("틀린회사");

        // then
        assertEquals(expectedJobPosts, actualJobPosts);
    }

    @Test
    @DisplayName("포지션별 공고 조회")
    void 포지션으로_검색_성공() {
        // when
        String position = "테스트포지션";
        JobPost jobPost1 = new JobPost(1L, "테스트회사1", position, 10000, "테스트내용1", Set.of("자바"));
        JobPost jobPost2 = new JobPost(2L, "테스트회사2", position, 20000, "테스트내용2", Set.of("파이썬"));
        List<JobPost> expectedJobPosts = Arrays.asList(jobPost1, jobPost2);

        when(jobPostRepository.findByPosition(position)).thenReturn(expectedJobPosts);

        // given
        List<JobPost> actualJobPosts = jobPostService.getJobsByPosition(position);

        // then
        assertEquals(expectedJobPosts, actualJobPosts);
    }

    @Test
    @DisplayName("포지션별 공고 조회")
    void 포지션으로_검색_실패() {
        // when
        String position = "테스트포지션";
        String position1 = "없는 포지션";
        JobPost jobPost1 = new JobPost(1L, "테스트회사1", position, 10000, "테스트내용1", Set.of("자바"));
        JobPost jobPost2 = new JobPost(2L, "테스트회사2", position, 20000, "테스트내용2", Set.of("파이썬"));
        List<JobPost> expectedJobPosts = Arrays.asList(jobPost1, jobPost2);

        when(jobPostRepository.findByPosition(position)).thenReturn(expectedJobPosts);

        // given
        List<JobPost> actualJobPosts = jobPostService.getJobsByPosition(position1);

        // then
        assertEquals(expectedJobPosts, actualJobPosts);
    }

    @Test
    @DisplayName("상세 조회 - 존재하는 post")
    void 상세_조회_공고() {
        // given
        Long companyPostId = 1L;
        JobPost expectedJobPost = new JobPost(companyPostId, "테스트회사", "테스트포지션", 10000, "테스트내용", Set.of("자바"));

        when(jobPostRepository.findById(companyPostId)).thenReturn(Optional.of(expectedJobPost));

        // when
        JobPost existPost = jobPostService.getJobPostDetail(companyPostId);

        // then
        assertEquals(expectedJobPost, existPost);
    }

    @Test
    @DisplayName("상세 조회 - 존재하지않는 공고")
    void 존재하지않는_공고() {
        // given
        Long companyPostId = 1L;
        Long notExistPostId = 2L;
        JobPost expectedJobPost = new JobPost(companyPostId, "테스트회사", "테스트포지션", 10000, "테스트내용", Set.of("자바"));

        when(jobPostRepository.findById(companyPostId)).thenReturn(Optional.of(expectedJobPost));

        // when
        JobPost notExistPost = jobPostService.getJobPostDetail(notExistPostId);

        // then
        assertEquals(expectedJobPost, notExistPost);
    }

    @Test
    @DisplayName("채용 공고 수정 테스트")
    void 채용공고_수정_성공() {
        // given
        RegisterJobDto registerJobDto = RegisterJobDto.builder()
                .companyName("원티드")
                .position("벡앤드 개발자")
                .reward(80000)
                .content("어서오십쇼")
                .stacks(Set.of("자바", "파이썬"))
                .build();

        long jobPostId = jobPostService.addRegisterJob(registerJobDto);

        // when
        UpdateJobDto updatedJobPost = UpdateJobDto.builder()
                .companyName("원티드")
                .position("프론트엔드 개발자")
                .reward(90000)
                .content("어서오십쇼")
                .stacks(Set.of("자바", "자바 스크립트", "리엑트"))
                .build();

        jobPostService.updatePost(jobPostId, updatedJobPost);

        // then
        JobPost retrievedJobPost = jobPostService.getJobPostDetail(jobPostId);
        assertThat(retrievedJobPost.getCompanyName()).isEqualTo("원티드");
        assertThat(retrievedJobPost.getPosition()).isEqualTo("프론트엔드 개발자");
        assertThat(retrievedJobPost.getReward()).isEqualTo(90000);
        assertThat(retrievedJobPost.getContent()).isEqualTo("어서오십쇼");
        Set<String> expectedSet = new HashSet<>(Arrays.asList("자바", "자바 스크립트", "리엑트"));
        Set<String> actualSet = new HashSet<>(retrievedJobPost.getStacks());
        assertTrue(expectedSet.equals(actualSet));
    }

    @Test
    @DisplayName("채용 공고 수정 테스트")
    void 채용공고_수정_실패() {
        // given
        RegisterJobDto registerJobDto = RegisterJobDto.builder()
                .companyName("원티드")
                .position("벡앤드 개발자")
                .reward(80000)
                .content("어서오십쇼")
                .stacks(Set.of("자바", "파이썬"))
                .build();

        long jobPostId = jobPostService.addRegisterJob(registerJobDto);

        // when
        UpdateJobDto updatedJobPost = UpdateJobDto.builder()
                .companyName("원티드")
                .position("프론트엔드 개발자")
                .reward(90000)
                .content("어서오십쇼")
                .stacks(Set.of("자바", "자바 스크립트", "리엑트"))
                .build();

        jobPostService.updatePost(jobPostId, updatedJobPost);

        // then
        JobPost retrievedJobPost = jobPostService.getJobPostDetail(jobPostId);
        assertThat(retrievedJobPost.getCompanyName()).isEqualTo("원티드");
        assertThat(retrievedJobPost.getPosition()).isEqualTo("프론트엔드 개발자");
        assertThat(retrievedJobPost.getReward()).isEqualTo(90000);
        assertThat(retrievedJobPost.getContent()).isEqualTo("여기는 실패 테스트입니다");
        Set<String> expectedSet = new HashSet<>(Arrays.asList("자바", "자바 스크립트", "리엑트"));
        Set<String> actualSet = new HashSet<>(retrievedJobPost.getStacks());
        assertTrue(expectedSet.equals(actualSet));
    }


    @Test
    @DisplayName("채용 공고 삭제 테스트")
    void 채용공고_삭제_성공() {
        // given
        RegisterJobDto registerJobDto = RegisterJobDto.builder()
                .companyName("원티드")
                .position("벡앤드 개발자")
                .reward(80000)
                .content("어서오십쇼")
                .stacks(Set.of("자바", "파이썬"))
                .build();

        long jobPostId = jobPostService.addRegisterJob(registerJobDto);

        // when
        jobPostService.deletePost(jobPostId);

        // then
        assertThrows(JobPostException.NotFoundJobPost().getClass(), () -> jobPostService.getJobPostDetail(jobPostId));

    }

    @Test
    @DisplayName("공고가 존재하지 않는 데이터 삭세 시")
    void 존제하지않는_공고_삭제() {
        // given
        Long nonExistentCompanyPostId = 9999L;

        // when/then
        assertThrows(JobPostException.NotFoundJobPost().getClass(), () -> jobPostService.deletePost(nonExistentCompanyPostId));
    }


}