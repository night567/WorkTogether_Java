package cn.edu.szu.user.pojo;

import lombok.Data;

@Data
public class LoginDTO {
    private String email;
    private String password;
    private String verificationCode;
}
