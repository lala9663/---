package com.example.wantedpreonboardingbackend.apply.service.impl;

import com.example.wantedpreonboardingbackend.apply.entity.Apply;
import com.example.wantedpreonboardingbackend.apply.repository.ApplyRepository;
import com.example.wantedpreonboardingbackend.apply.service.ApplyService;
import com.example.wantedpreonboardingbackend.post.entity.Post;
import com.example.wantedpreonboardingbackend.post.repository.PostRepository;
import com.example.wantedpreonboardingbackend.user.entity.User;
import com.example.wantedpreonboardingbackend.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ApplyServiceImpl implements ApplyService {
    private final ApplyRepository applyRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;


    @Override
    public boolean applyToPost(Long postId, HttpSession session) {
        String applicantName = (String) session.getAttribute("name");

        if (applicantName != null) {
            Optional<Post> post = postRepository.findById(postId);

            User applicant = userRepository.findByName(applicantName);


            Apply apply = Apply.builder()
                    .post(post.get())
                    .user(applicant)
                    .build();
            applyRepository.save(apply);

            System.out.println(applicantName + "님이 " + postId + "번 공고에 지원했습니다.");

            return true;
        } else {
            return false;
        }
    }
}
