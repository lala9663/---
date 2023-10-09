package com.example.wantedpreonboardingbackend.board.exception;

public class JobPostException extends RuntimeException{
    public JobPostException(String message) {
        super(message);
    }

    public static JobPostException duplicateJob() {
        return new JobPostException("이미 등록된 공고 입니다.");
    }
}
