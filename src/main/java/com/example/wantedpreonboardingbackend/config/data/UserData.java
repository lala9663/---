package com.example.wantedpreonboardingbackend.config.data;

import com.example.wantedpreonboardingbackend.user.entity.User;
import com.example.wantedpreonboardingbackend.user.repository.UserRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserData {
    private final UserRepository userRepository;
    public UserData(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @PostConstruct
    public void init() {
        List<User> users = new ArrayList<>();

        users.add(User.builder()
                .name("user1")
                .password("password1")
                .build());

        users.add(User.builder()
                .name("user2")
                .password("password2")
                .build());

        userRepository.saveAll(users);
    }
}
