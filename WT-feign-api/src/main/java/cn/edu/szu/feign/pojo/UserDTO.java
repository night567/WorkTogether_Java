package cn.edu.szu.feign.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserDTO implements Serializable {
    private Long id;

    private String email; // 邮箱

    private String name; // 姓名

    private String phone; // 手机

    private Integer sex; // 性别(1:男; 2:女; 3:未知)

    private String avatar; // 头像

    private Date lastLoginTime; // 最后登录时间

    private Date createTime; // 创建时间
}