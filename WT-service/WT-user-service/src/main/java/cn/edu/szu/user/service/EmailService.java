package cn.edu.szu.user.service;

public interface EmailService {
    boolean sendVerificationCode(String email);
}
