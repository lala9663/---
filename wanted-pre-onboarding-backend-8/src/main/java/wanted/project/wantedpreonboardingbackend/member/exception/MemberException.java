package wanted.project.wantedpreonboardingbackend.member.exception;

public class MemberException extends RuntimeException {

    public MemberException(String message) {
        super(message);
    }

    public MemberException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberException() {
    }
}