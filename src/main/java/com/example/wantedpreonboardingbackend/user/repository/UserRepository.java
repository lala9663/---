package com.example.wantedpreonboardingbackend.user.repository;

import com.example.wantedpreonboardingbackend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);

}
