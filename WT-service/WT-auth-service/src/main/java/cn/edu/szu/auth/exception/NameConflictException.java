package cn.edu.szu.auth.exception;

public class NameConflictException extends RuntimeException {

    public NameConflictException(String message) {
        super(message);
    }
}
