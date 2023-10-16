package com.example.wantedpreonboardingbackend.post.exception;

public class PostException extends RuntimeException{

    private String errorCode;
    private int httpStatus;
    public PostException(String message) {
        super(message);
    }

    public static PostException duplicateJob() {
        return new PostException("이미 등록된 공고 입니다.");
    }

    public static PostException NotFoundPost() {
        return new PostException("해당 공고문을 찾지 못했습니다.");
    }

    public static PostException requiredField() {
        return new PostException("필수 입력 사항입니다");
    }

    public static PostException notFoundCompany() {
        return new PostException("회사가 등록되지 않았습니다.");
    }
}
