package cn.edu.szu.user.service;

public interface EmailService {
    String generateRegistrationCode(String email);

    boolean checkRegistrationCode(String email, String code);

    String generateVerificationCode(String email);

    boolean checkVerificationCode(String email, String code);

    boolean sendInviteCode(String email, Long companyId);

    void sendCodeEmail(String email, String code);
    void sendCodeEmailByMQ(String email, String code);
}
