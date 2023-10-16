package com.example.wantedpreonboardingbackend.user.service.impl;

import com.example.wantedpreonboardingbackend.user.dto.RegisterUserDto;
import com.example.wantedpreonboardingbackend.user.entity.User;
import com.example.wantedpreonboardingbackend.user.repository.UserRepository;
import com.example.wantedpreonboardingbackend.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public long registerUser(RegisterUserDto registerUserDto) {
        User user = User.builder()
                .name(registerUserDto.getName())
                .password(registerUserDto.getPassword())
                .build();

        userRepository.save(user);

        return user.getUserId();
    }

    @Override
    public boolean login(String name, String password) {
        User user = userRepository.findByName(name);

        if (user != null && user.getPassword().equals(password)) {
            return true;
        }

        return false;
    }

}
