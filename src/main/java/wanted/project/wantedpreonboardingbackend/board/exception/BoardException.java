package wanted.project.wantedpreonboardingbackend.board.exception;

public class BoardException extends RuntimeException {
    public BoardException(String message) {
        super(message);
    }

    public static class BoardNotFoundException extends BoardException {
        public BoardNotFoundException() {
            super("존재하지 않는 게시판입니다");
        }
    }

    public static class BoardDeletedException extends BoardException {
        public BoardDeletedException() {
            super("삭제된 게시판입니다");
        }
    }

    public static class FailedException extends BoardException {
        public FailedException(String message) {
            super(message);
        }

    }
}

