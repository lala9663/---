package com.example.wantedpreonboardingbackend.board.service;

import com.example.wantedpreonboardingbackend.board.dto.RegisterJobDto;

public interface JobPostService {

    long addRegisterJob(RegisterJobDto registerJobDto);
}
