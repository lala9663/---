package com.example.wantedpreonboardingbackend.post.controller;

import com.example.wantedpreonboardingbackend.post.dto.RegisterJobDto;
import com.example.wantedpreonboardingbackend.post.dto.UpdateJobDto;
import com.example.wantedpreonboardingbackend.post.entity.JobPost;
import com.example.wantedpreonboardingbackend.post.service.JobPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/jobPost")
@RequiredArgsConstructor
public class PostController {
    private final JobPostService jobPostService;

    @Operation(summary = " 채용공고 등록 요청", description = "채용공고가 등록됩니다.", tags = {"Post Controller"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/register")
    public ResponseEntity<String> registerPosts(RegisterJobDto registerJobDto) {
        try {
            long jobPostId = jobPostService.addRegisterJob(registerJobDto);
            return ResponseEntity.ok("구인공고가 등록되었습니다. id는: " + jobPostId + " 입니다");
        } catch (RuntimeException re) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = " 공고 전체 조회", description = "전체 조회합니다.", tags = {"Post Controller"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping("/list")
    public ResponseEntity<List<JobPost>> getAllPosts() {
        try {
            List<JobPost> jobPosts = jobPostService.getAllJobs();
            return ResponseEntity.ok(jobPosts);
        } catch (RuntimeException re) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "회사 검색", description = "회사명으로 검색합니다", tags = {"Post Controller"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping("/list/company/{companyName}")
    public ResponseEntity<List<JobPost>> getPostsByCompany(@PathVariable String companyName) {
        try {
            List<JobPost> jobPosts = jobPostService.getJobsByCompany(companyName);
            return ResponseEntity.ok(jobPosts);
        } catch (RuntimeException re) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "포지션 검색", description = "포지션으로 검색합니다", tags = {"Post Controller"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping("/list/position/{position}")
    public ResponseEntity<List<JobPost>> getPostsByPosition(@PathVariable String position) {
        try {
            List<JobPost> jobPosts = jobPostService.getJobsByPosition(position);
            return ResponseEntity.ok(jobPosts);
        } catch (RuntimeException re) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "상세 조회", description = "상세 조회합니다.", tags = {"Post Controller"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping("/{companyPostId}/details")
    public ResponseEntity<JobPost> getPostById(@PathVariable Long companyPostId) {
        try {
            JobPost jobPost = jobPostService.getJobPostDetail(companyPostId);
            return new ResponseEntity<>(jobPost, HttpStatus.OK);
        } catch (RuntimeException re) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "채용공고문 수정", description = "공고문을 수정합니다", tags = {"Post Controller"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PutMapping("/update/{companyPostId}")
    public ResponseEntity<String> updatePost(
            @PathVariable Long companyPostId,
            @Valid @RequestBody UpdateJobDto updateJobDto) {
        try {
            long updatedPostId = jobPostService.updatePost(companyPostId, updateJobDto);

            return ResponseEntity.ok("채용 공고 업데이트가 완료되었습니다. 업데이트된 포스트 ID: " + updatedPostId);
        } catch (RuntimeException re) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "채용공고문 삭제", description = "공고문을 삭제합니다", tags = {"Post Controller"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @DeleteMapping("/delete/{companyPostId}")
    public ResponseEntity<String> deletePost(@PathVariable Long companyPostId) {
        try {
            long deletePostId = jobPostService.deletePost(companyPostId);
            return ResponseEntity.ok("공고 번호 " + deletePostId + "가 삭제되었습니다.");
        } catch (RuntimeException re) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
