package com.example.wantedpreonboardingbackend.post.service.impl;

import com.example.wantedpreonboardingbackend.company.entity.Company;
import com.example.wantedpreonboardingbackend.company.repository.CompanyRepository;
import com.example.wantedpreonboardingbackend.post.dto.PostDetailResponseDto;
import com.example.wantedpreonboardingbackend.post.dto.PostResponseDto;
import com.example.wantedpreonboardingbackend.post.dto.RegisterPostDto;
import com.example.wantedpreonboardingbackend.post.dto.UpdatePostDto;
import com.example.wantedpreonboardingbackend.post.entity.Post;
import com.example.wantedpreonboardingbackend.post.exception.PostException;
import com.example.wantedpreonboardingbackend.post.repository.PostRepository;
import com.example.wantedpreonboardingbackend.post.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final CompanyRepository companyRepository;


    @Override
    public long addRegisterJob(RegisterPostDto registerPostDto) {

        Company companyId = companyRepository.findById(registerPostDto.getCompanyId())
                .orElseThrow(PostException::notFoundCompany);

        Post post = Post.builder()
                .company(companyId)
                .position(registerPostDto.getPosition())
                .reward(registerPostDto.getReward())
                .content(registerPostDto.getContent())
                .stacks(registerPostDto.getStacks())
                .build();

        companyId.getPostList().add(post);

        Post savedPost = postRepository.save(post);

        return savedPost.getId();
    }

    @Override
    public List<PostResponseDto> findByPosition(String position) {
        List<Post> posts = postRepository.findByPosition(position);
        return posts.stream()
                .map(PostResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostResponseDto> findByCompanyName(String companyName) {
        List<Post> posts = postRepository.findByCompanyCompanyName(companyName);
        return posts.stream()
                .map(PostResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public long updatePost(Long postId, UpdatePostDto updatePostDto) {
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(PostException::NotFoundPost);

        Post updatedPost = Post.builder()
                .position(updatePostDto.getPosition())
                .reward(updatePostDto.getReward())
                .content(updatePostDto.getContent())
                .stacks(updatePostDto.getStacks())
                .build();

        existingPost.updateFrom(updatedPost);

        postRepository.save(existingPost);

        return existingPost.getId();
    }

    @Override
    public long deletePost(Long postId) {
        Optional<Post> post = postRepository.findById(postId);

        if (post.isEmpty()) {
            throw PostException.NotFoundPost();
        } else {
            postRepository.delete(post.get());
        }
        return postId;
    }

    @Override
    public Post findById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(PostException::NotFoundPost);
    }

    @Override
    public PostDetailResponseDto getPostDetailById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostException::NotFoundPost);

        return PostDetailResponseDto.fromEntity(post);
    }

    @Override
    public List<PostResponseDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(PostResponseDto::fromEntity)
                .collect(Collectors.toList());
    }
}
