package com.example.wantedpreonboardingbackend.post.controller;

import com.example.wantedpreonboardingbackend.company.entity.Company;
import com.example.wantedpreonboardingbackend.post.dto.PostResponseDto;
import com.example.wantedpreonboardingbackend.post.dto.RegisterPostDto;
import com.example.wantedpreonboardingbackend.post.dto.UpdatePostDto;
import com.example.wantedpreonboardingbackend.post.entity.Post;
import com.example.wantedpreonboardingbackend.post.service.PostService;
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
@RequestMapping("/Post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @Operation(summary = " 채용공고 등록 요청", description = "채용공고가 등록됩니다.", tags = {"Post Controller"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/register")
    public ResponseEntity<String> registerPosts(@RequestBody RegisterPostDto registerPostDto) {
        try {
            long postId = postService.addRegisterJob(registerPostDto);
            return ResponseEntity.ok("구인공고가 등록되었습니다. id는: " + postId + " 입니다");
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
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        List<PostResponseDto> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @Operation(summary = "회사명 검색", description = "회사명으로 검색합니다", tags = {"Post Controller"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping("/list/company/{companyName}")
    public ResponseEntity<List<PostResponseDto>> findByCompanyName(@PathVariable String companyName) {
        List<PostResponseDto> posts = postService.findByCompanyName(companyName);
        return ResponseEntity.ok(posts);
    }

    @Operation(summary = "포지션 검색", description = "포지션으로 검색합니다", tags = {"Post Controller"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping("/list/position/{position}")
    public ResponseEntity<List<PostResponseDto>> findByPosition(@RequestParam String position) {
        List<PostResponseDto> posts = postService.findByPosition(position);
        return ResponseEntity.ok(posts);
    }

    @Operation(summary = "채용공고문 수정", description = "공고문을 수정합니다", tags = {"Post Controller"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PutMapping("/update/{postId}")
    public ResponseEntity<String> updatePost(
            @PathVariable Long postId,
            @Valid @RequestBody UpdatePostDto updatePostDto) {
        try {
            long updatedPostId = postService.updatePost(postId, updatePostDto);

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
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        try {
            long deletePostId = postService.deletePost(postId);
            return ResponseEntity.ok("공고 번호 " + deletePostId + "가 삭제되었습니다.");
        } catch (RuntimeException re) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
