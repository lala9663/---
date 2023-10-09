package com.example.wantedpreonboardingbackend.board.controller;

import com.example.wantedpreonboardingbackend.board.dto.RegisterJobDto;
import com.example.wantedpreonboardingbackend.board.service.JobPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jobPost")
@RequiredArgsConstructor
public class RegisterController {
    private final JobPostService jobPostService;

    @Operation(summary = " 채용공고 등록 요청", description = "채용공고가 등록됩니다.", tags = {"Register Controller"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/register")
    public ResponseEntity<String> registerJob(RegisterJobDto registerJobDto) {
        try {
            long jobPostId = jobPostService.addRegisterJob(registerJobDto);
            return ResponseEntity.ok("구인공고가 등록되었습니다. id는: " + jobPostId + " 입니다");
        } catch (RuntimeException re) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
