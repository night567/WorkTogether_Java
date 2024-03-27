package cn.edu.szu.auth.expection;

public class NameConflictException extends RuntimeException {

    public NameConflictException(String message) {
        super(message);
    }
}
