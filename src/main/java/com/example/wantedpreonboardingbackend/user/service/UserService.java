package com.example.wantedpreonboardingbackend.user.service;

import com.example.wantedpreonboardingbackend.user.dto.RegisterUserDto;

public interface UserService {
    long registerUser(RegisterUserDto registerUserDto);
    boolean login(String name, String password);
}
