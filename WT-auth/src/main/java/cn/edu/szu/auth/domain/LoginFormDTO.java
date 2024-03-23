package cn.edu.szu.auth.domain;

import lombok.Data;

@Data
public class LoginFormDTO {
    private String email;
    private String password;
    private String verificationCode;
}
