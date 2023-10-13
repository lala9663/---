package com.example.wantedpreonboardingbackend.post.service;

import com.example.wantedpreonboardingbackend.post.dto.RegisterJobDto;
import com.example.wantedpreonboardingbackend.post.dto.UpdateJobDto;
import com.example.wantedpreonboardingbackend.post.entity.JobPost;

import java.util.List;

public interface JobPostService {
    long addRegisterJob(RegisterJobDto registerJobDto);
    List<JobPost> getAllJobs();
    List<JobPost> getJobsByCompany(String companyName);

    List<JobPost> getJobsByPosition(String position);
    JobPost getJobPostDetail(Long companyPostId);
    long updatePost(Long companyPostId, UpdateJobDto updateJobDto);
    long deletePost(Long companyPostId);

}
