package com.example.wantedpreonboardingbackend.board.service;

import com.example.wantedpreonboardingbackend.board.dto.RegisterJobDto;
import com.example.wantedpreonboardingbackend.board.dto.UpdateJobDto;
import com.example.wantedpreonboardingbackend.board.entity.JobPost;

import java.util.List;

public interface JobPostService {
    long addRegisterJob(RegisterJobDto registerJobDto);
    List<JobPost> getAllJobs();
    List<JobPost> getJobsByCompany(String companyName);

    List<JobPost> getJobsByPosition(String position);
    JobPost getJobPostDetail(Long companyPostId);
    long updateJob(Long postId, UpdateJobDto updateJobDto);

}
