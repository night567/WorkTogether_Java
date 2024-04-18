package cn.edu.szu.user.pojo.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "wt_user")
public class User implements Serializable {
    @TableId(value = "id")
    private Long id;

    @TableField(value = "email")
    private String email; // 邮箱

    @TableField(value = "name")
    private String name; // 姓名

    @TableField(value = "phone")
    private String phone; // 手机

    @TableField(value = "sex")
    private Integer sex; // 性别(1:男; 2:女; 3:未知)

    @TableField(value = "avatar")
    private String avatar; // 头像

    @TableField(value = "last_login_time")
    private Date lastLoginTime; // 最后登录时间

    @TableField(value = "create_time")
    private Date createTime; // 创建时间
}