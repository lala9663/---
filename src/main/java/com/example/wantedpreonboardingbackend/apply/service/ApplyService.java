package com.example.wantedpreonboardingbackend.apply.service;

import javax.servlet.http.HttpSession;

public interface ApplyService {
    boolean applyToPost(Long postId, HttpSession session);

}
