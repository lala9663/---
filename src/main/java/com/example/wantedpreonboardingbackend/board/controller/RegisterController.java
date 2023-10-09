package com.example.wantedpreonboardingbackend.board.controller;

import com.example.wantedpreonboardingbackend.board.dto.RegisterJobDto;
import com.example.wantedpreonboardingbackend.board.dto.RegisterStackDto;
import com.example.wantedpreonboardingbackend.board.service.JobPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
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

    @Operation(summary = " 직무 등록 요청", description = "해당 직무가 등록됩니다.", tags = {"Register Controller"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/registerStack")
    public ResponseEntity<String> registerJob(RegisterStackDto registerStackDto) {
        try {
            long stackId = jobPostService.addStack(registerStackDto);
            return ResponseEntity.ok("해당 직무가 등록되었습니다. 직무 번호는: " + stackId + " 입니다.");
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("등록에 실패했습니다.");
        }
    }


//        @Operation(summary = " 채용공고 등록 요청", description = "채용공고가 등록됩니다.", tags = {"Register Controller"})
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "OK"),
//            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
//            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
//            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
//    })
//    @PostMapping("/register")
//    public ResponseEntity<String> registerJob(RegisterJobDto registerJobDto) {
//        try {
////            long companyId = jobPostService.
//        } catch () {
//        }
//    }

}
