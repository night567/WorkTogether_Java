package cn.edu.szu.auth.service;

public interface EmailService {
    boolean sendVerificationCode(String email);
}
