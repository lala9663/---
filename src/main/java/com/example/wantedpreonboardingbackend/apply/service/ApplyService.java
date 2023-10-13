package com.example.wantedpreonboardingbackend.apply.service;

import javax.servlet.http.HttpSession;

public interface ApplyService {
    boolean applyToJobPost(Long jobPostId, HttpSession session);

}
