package com.example.wantedpreonboardingbackend.post.service.impl;

import com.example.wantedpreonboardingbackend.company.entity.Company;
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

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;


    @Override
    public long addRegisterJob(RegisterPostDto registerPostDto) {

        Post Post = registerPostDto.toEntity();
        postRepository.save(Post);

        return Post.getPostId();
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> getPostsByCompany(Company companyName) {
        return postRepository.findByCompanyName(companyName);
    }

    @Override
    public List<Post> getPostsByPosition(String position) {
        return postRepository.findByPosition(position);
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

        return existingPost.getPostId();
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
    public Post getPostDetail(Long postId) {
        Optional<Post> detail = postRepository.findById(postId);
        if (detail.isPresent()) {
            return detail.get();
        } else {
            throw PostException.NotFoundPost();
        }
    }

}
