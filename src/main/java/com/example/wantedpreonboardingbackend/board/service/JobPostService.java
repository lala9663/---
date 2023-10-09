package com.example.wantedpreonboardingbackend.board.service;

import com.example.wantedpreonboardingbackend.board.dto.RegisterJobDto;
import com.example.wantedpreonboardingbackend.board.entity.JobPost;

import java.util.List;

public interface JobPostService {
    long addRegisterJob(RegisterJobDto registerJobDto);

    List<JobPost> getAllJobs();
    List<JobPost> getJobsByCompany(String companyName);

    List<JobPost> getJobsByPosition(String position);

}
