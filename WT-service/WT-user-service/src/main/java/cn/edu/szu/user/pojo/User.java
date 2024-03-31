package cn.edu.szu.user.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "wt_user")
public class User implements Serializable {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;  // ID

    private String salt;  // MD5盐
    private String email;  // 邮箱
    private String name;  // 姓名
    private String password;  // 密码
    private String phone;  // 手机号
    private Integer sex;  // 性别
    private String avatar;  // 头像
    private Boolean status;  // 启用状态 1启用 0禁用

    @TableField(value = "last_login_time")
    private Date lastLoginTime;  // 最后登录时间

    @TableField(value = "create_time")
    private Date createTime;  //创建时间

    public UserDTO parseToDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(this.id);
        userDTO.setEmail(this.email);
        userDTO.setName(this.name);
        userDTO.setPhone(this.phone);
        userDTO.setStatus(this.status);
        userDTO.setLastLoginTime(this.lastLoginTime);
        userDTO.setCreateTime(this.createTime);
        return userDTO;
    }
}