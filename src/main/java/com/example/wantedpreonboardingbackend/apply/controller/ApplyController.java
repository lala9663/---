package com.example.wantedpreonboardingbackend.apply.controller;

import com.example.wantedpreonboardingbackend.apply.service.ApplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/Post")
@RequiredArgsConstructor
public class ApplyController {
    private final ApplyService applyService;

    @Operation(summary = "공고에 지원", description = "지원합니다.", tags = {"Apply Controller"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/apply/{postId}")
    public ResponseEntity<String> applyToPost(@RequestHeader Long postId, HttpSession session) {
        boolean applied = applyService.applyToPost(postId, session);

        if (applied) {
            return ResponseEntity.ok("지원이 완료되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("지원에 실패했습니다.");
        }
    }

}
