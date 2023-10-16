package com.example.wantedpreonboardingbackend.apply.service.impl;

import com.example.wantedpreonboardingbackend.apply.dto.ApplyDto;
import com.example.wantedpreonboardingbackend.apply.entity.Apply;
import com.example.wantedpreonboardingbackend.apply.repository.ApplyRepository;
import com.example.wantedpreonboardingbackend.apply.service.ApplyService;
import com.example.wantedpreonboardingbackend.post.entity.Post;
import com.example.wantedpreonboardingbackend.post.service.PostService;
import com.example.wantedpreonboardingbackend.user.entity.User;
import com.example.wantedpreonboardingbackend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplyServiceImpl implements ApplyService {
    private final ApplyRepository applyRepository;
    private final UserService userService;
    private final PostService postService;


    @Override
    public Long apply(ApplyDto applyDto) {
        User user = userService.findById(applyDto.getMemberId());
        Post post = postService.findById(applyDto.getPostId());

        Apply apply = Apply.builder()
                .user(user)
                .post(post)
                .build();

        applyRepository.save(apply);

        return apply.getId();
    }
}
