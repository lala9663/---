package com.example.wantedpreonboardingbackend.post.repository;

import com.example.wantedpreonboardingbackend.company.entity.Company;
import com.example.wantedpreonboardingbackend.post.dto.PostResponseDto;
import com.example.wantedpreonboardingbackend.post.entity.Post;
import com.example.wantedpreonboardingbackend.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByPosition(String position);
    List<Post> findByCompanyCompanyName(String companyName);

}
