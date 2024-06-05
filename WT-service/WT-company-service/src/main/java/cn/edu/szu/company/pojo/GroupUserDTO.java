package cn.edu.szu.company.pojo;

import cn.edu.szu.company.pojo.domain.GroupUser;
import cn.edu.szu.feign.pojo.UserDTO;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupUserDTO {
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;
    private String name; //姓名
    private String phone; //手机
    private String email; // 邮箱
    private String avatar; // 头像
    private String deptName; //部门名称
    private String job; //职位
    private String address; //工作地址
    private String introduction; //个人介绍

    public GroupUserDTO(GroupUser groupUser, UserDTO userDTO, String deptName, String job) {
        this.id = groupUser.getId().toString();
        this.name = userDTO.getName();
        this.phone = userDTO.getPhone();
        this.email = userDTO.getEmail();
        this.avatar = userDTO.getAvatar();
        this.deptName = deptName;
        this.introduction = groupUser.getDescription();
        this.address = groupUser.getLocation();
        this.job = job;
    }
}

