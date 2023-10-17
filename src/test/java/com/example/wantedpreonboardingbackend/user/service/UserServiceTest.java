package com.example.wantedpreonboardingbackend.user.service;

import com.example.wantedpreonboardingbackend.user.dto.RegisterUserDto;
import com.example.wantedpreonboardingbackend.user.entity.User;
import com.example.wantedpreonboardingbackend.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Test
    @DisplayName("회원가입 테스트")
    void 회원가입_테스트() {
        // given
        RegisterUserDto registerUserDto = RegisterUserDto.builder()
                .name("user1")
                .password("password1")
                .build();
        // when
        Long userId = userService.registerUser(registerUserDto);

        // then
        Long expectedUserId = 3L;
        assertEquals(expectedUserId, userId);
    }

    @Test
    @DisplayName("아이디 찾기")
    void 아이디_이용_테스트() {
        RegisterUserDto registerUserDto = RegisterUserDto.builder()
                .name("user1")
                .password("password1")
                .build();
        // when
        Long userId = userService.registerUser(registerUserDto);

        // then
        User findUser = userService.findById(3L);

        assertEquals(3L, findUser.getId());
        assertEquals("user1", findUser.getName());
        assertEquals("password1", findUser.getPassword());
    }
}