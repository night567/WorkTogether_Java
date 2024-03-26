package cn.edu.szu.user.pojo;

import lombok.Data;

@Data
public class LoginForm {
    private String email;
    private String password;
    private String verificationCode;
}
