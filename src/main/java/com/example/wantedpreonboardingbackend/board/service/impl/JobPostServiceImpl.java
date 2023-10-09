package com.example.wantedpreonboardingbackend.board.service.impl;

import com.example.wantedpreonboardingbackend.board.dto.RegisterJobDto;
import com.example.wantedpreonboardingbackend.board.entity.JobPost;
import com.example.wantedpreonboardingbackend.board.exception.JobPostException;
import com.example.wantedpreonboardingbackend.board.repository.JobPostRepository;
import com.example.wantedpreonboardingbackend.board.service.JobPostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class JobPostServiceImpl implements JobPostService {
    private final JobPostRepository jobPostRepository;

    @Override
    public long addRegisterJob(RegisterJobDto registerJobDto) {

        if (isDuplicateJob(registerJobDto.getCompanyName(), registerJobDto.getPosition())) {
            throw JobPostException.duplicateJob();
        }

        JobPost jobPost = registerJobDto.toEntity();
        jobPostRepository.save(jobPost);

        return jobPost.getCompanyPostId();
    }

    @Override
    public List<JobPost> getAllJobs() {
        return jobPostRepository.findAll();
    }


    public boolean isDuplicateJob(String companyName, String position) {
        return jobPostRepository.existsByCompanyNameAndPosition(companyName, position);
    }
}
