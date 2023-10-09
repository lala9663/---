package com.example.wantedpreonboardingbackend.board.service.impl;

import com.example.wantedpreonboardingbackend.board.dto.RegisterStackDto;
import com.example.wantedpreonboardingbackend.board.entity.Stack;
import com.example.wantedpreonboardingbackend.board.repository.JobPostRepository;
import com.example.wantedpreonboardingbackend.board.repository.StackRepository;
import com.example.wantedpreonboardingbackend.board.service.JobPostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JobPostServiceImpl implements JobPostService {
    private final JobPostRepository jobPostRepository;
    private final StackRepository stackRepository;
    @Override
    public long addStack(RegisterStackDto registerStackDto) {
        String stackName = registerStackDto.getStackName();
        Stack stack = Stack.builder()
                .stackName(stackName)
                .build();

        Stack savedStack = stackRepository.save(stack);

        return savedStack.getStackId();
    }
}
