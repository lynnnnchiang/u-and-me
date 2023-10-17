package tw.idv.cha102.g7.member.controller.exception;

public class InsufficientPointsException extends RuntimeException {
    public InsufficientPointsException(String message) {
        super(message);
    }
}