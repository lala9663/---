package com.example.wantedpreonboardingbackend.board.exception;

public class JobPostException extends RuntimeException{
    public JobPostException(String message) {
        super(message);
    }

    public static JobPostException duplicateJob() {
        return new JobPostException("이미 등록된 공고 입니다.");
    }

    public static JobPostException NotFoundJobPost() {
        return new JobPostException("해당 공고문을 찾지 못했습니다.");
    }
}
