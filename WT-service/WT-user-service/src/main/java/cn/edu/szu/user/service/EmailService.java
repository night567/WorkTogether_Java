package cn.edu.szu.user.service;

public interface EmailService {
    boolean sendVerificationCode(String email);
    void sendCodeEmail(String email, String code);
    boolean sendInviteCode(String email,Long companyId);
}
