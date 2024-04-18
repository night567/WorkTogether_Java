package cn.edu.szu.user.pojo.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName wt_user_login
 */
@Data
@TableName(value ="wt_user_login")
public class UserLogin implements Serializable {
    @TableId(value = "id")
    private Long id;

    @TableField(value = "email")
    private String email; // 邮箱

    @TableField(value = "salt")
    private String salt; // MD5盐

    @TableField(value = "password")
    private String password; // 密码
}