package com.example.wantedpreonboardingbackend.board.service.impl;

import com.example.wantedpreonboardingbackend.board.dto.RegisterJobDto;
import com.example.wantedpreonboardingbackend.board.dto.UpdateJobDto;
import com.example.wantedpreonboardingbackend.board.entity.JobPost;
import com.example.wantedpreonboardingbackend.board.exception.JobPostException;
import com.example.wantedpreonboardingbackend.board.repository.JobPostRepository;
import com.example.wantedpreonboardingbackend.board.service.JobPostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public List<JobPost> getJobsByCompany(String companyName) {
        return jobPostRepository.findByCompanyName(companyName);

    }

    @Override
    public List<JobPost> getJobsByPosition(String position) {
        return jobPostRepository.findByPosition(position);
    }

    @Override
    public JobPost getJobPostDetail(Long companyPostId) {
        Optional<JobPost> jobPostOptional = jobPostRepository.findById(companyPostId);
        if (jobPostOptional.isPresent()) {
            return jobPostOptional.get();
        } else {
            throw JobPostException.NotFoundJobPost();
        }
    }

    @Override
    public long updateJob(Long postId, UpdateJobDto updateJobDto) {
        JobPost existingPost = jobPostRepository.findById(postId)
                .orElseThrow(JobPostException::NotFoundJobPost);

        JobPost updatedPost = JobPost.builder()
                .companyName(updateJobDto.getCompanyName())
                .position(updateJobDto.getPosition())
                .reward(updateJobDto.getReward())
                .content(updateJobDto.getContent())
                .stacks(updateJobDto.getStacks())
                .build();

        existingPost.updateFrom(updatedPost);

        jobPostRepository.save(existingPost);

        return existingPost.getCompanyPostId();
    }

    public boolean isDuplicateJob(String companyName, String position) {
        return jobPostRepository.existsByCompanyNameAndPosition(companyName, position);
    }

}
