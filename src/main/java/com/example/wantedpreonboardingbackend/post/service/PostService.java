package com.example.wantedpreonboardingbackend.post.service;

import com.example.wantedpreonboardingbackend.company.entity.Company;
import com.example.wantedpreonboardingbackend.post.dto.RegisterPostDto;
import com.example.wantedpreonboardingbackend.post.dto.UpdatePostDto;
import com.example.wantedpreonboardingbackend.post.entity.Post;

import java.util.List;

public interface PostService {
    long addRegisterJob(RegisterPostDto registerPostDto);
    List<Post> getAllPosts();
    List<Post> getPostsByCompany(Company companyName);

    List<Post> getPostsByPosition(String position);
    long updatePost(Long companyPostId, UpdatePostDto updatePostDto);
    long deletePost(Long companyPostId);
    Post getPostDetail(Long postId);

}
