package com.example.wantedpreonboardingbackend.user.service;

import com.example.wantedpreonboardingbackend.user.dto.RegisterUserDto;
import com.example.wantedpreonboardingbackend.user.entity.User;

public interface UserService {
    long registerUser(RegisterUserDto registerUserDto);
    User findById(Long userId);

}
