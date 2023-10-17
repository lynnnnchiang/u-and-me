package tw.idv.cha102.g7.member.controller.exception;

public class VerificationException extends RuntimeException{
    public VerificationException(String message) {
        super(message);
    }
}