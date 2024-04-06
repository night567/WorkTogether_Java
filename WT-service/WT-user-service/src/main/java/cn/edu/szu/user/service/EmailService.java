package cn.edu.szu.user.service;

public interface EmailService {
    boolean sendVerificationCode(String email,Integer i,Long companyId);
    void sendVerificationCodeEmail(String email, String code);
}
