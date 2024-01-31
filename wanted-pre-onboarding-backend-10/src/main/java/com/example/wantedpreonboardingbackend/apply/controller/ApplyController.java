package com.example.wantedpreonboardingbackend.apply.controller;

import com.example.wantedpreonboardingbackend.apply.dto.ApplyDto;
import com.example.wantedpreonboardingbackend.apply.service.ApplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/apply")
    public ResponseEntity<String> apply(@RequestBody ApplyDto applyDto) {
        applyService.apply(applyDto);
        return ResponseEntity.ok("지원이 완료되었습니다");
    }

}
