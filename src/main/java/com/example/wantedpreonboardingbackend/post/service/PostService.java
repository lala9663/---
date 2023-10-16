package com.example.wantedpreonboardingbackend.post.service;

import com.example.wantedpreonboardingbackend.company.entity.Company;
import com.example.wantedpreonboardingbackend.post.dto.PostDetailResponseDto;
import com.example.wantedpreonboardingbackend.post.dto.PostResponseDto;
import com.example.wantedpreonboardingbackend.post.dto.RegisterPostDto;
import com.example.wantedpreonboardingbackend.post.dto.UpdatePostDto;
import com.example.wantedpreonboardingbackend.post.entity.Post;

import java.util.List;

public interface PostService {
    long addRegisterJob(RegisterPostDto registerPostDto);

    List<PostResponseDto> findByPosition(String position);
    List<PostResponseDto> findByCompanyName(String companyName);
    long updatePost(Long companyPostId, UpdatePostDto updatePostDto);
    long deletePost(Long companyPostId);
    List<PostResponseDto> getAllPosts();
    Post findById(Long postId);
    PostDetailResponseDto getPostDetailById(Long postId);
}
