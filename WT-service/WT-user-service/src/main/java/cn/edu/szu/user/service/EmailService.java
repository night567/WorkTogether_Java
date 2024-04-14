package cn.edu.szu.user.service;

public interface EmailService {
    boolean sendVerificationCode(String email);

    boolean sendInviteCode(String email, Long companyId);

    void sendCodeEmail(String email, String code);
}
