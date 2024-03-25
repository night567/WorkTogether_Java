package cn.edu.szu.feign.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private Long id;  // ID
    private String salt;  // MD5盐
    private String email;  // 邮箱
    private String name;  // 姓名
    private String password;  // 密码
    private String phone;  // 手机号
    private Integer sex;  // 性别
    private String avatar;  // 头像
    private Boolean status;  // 启用状态 1启用 0禁用
    private LocalDateTime lastLoginTime;  // 最后登录时间
    private LocalDateTime createTime;  //创建时间
}
