package cn.edu.szu.company.pojo;

import cn.edu.szu.company.pojo.domain.Group;
import cn.edu.szu.feign.pojo.UserDTO;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupDTO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String companyName; // 所属企业ID
    private String name; // 部门名称
    @JsonSerialize(using = ToStringSerializer.class)
    private Long managerId; // 负责人ID
    private String managerName; // 负责人名称
    private String managerEmail; // 负责人邮箱
    private String managerPhone; // 负责人电话
    private String description; // 介绍
    private Integer memberNum; // 团队人数
    private Integer deptNum; // 包含部门数量

    public GroupDTO(Group group) {
        this.id = group.getId();
        this.name = group.getName();
        this.managerId = group.getManagerId();
        this.description = group.getDescription();
        this.memberNum = group.getMemberNum();
        this.deptNum = group.getDeptNum();
    }

    public void setManager(UserDTO userDTO) {
        if (userDTO == null) {
            return;
        }
        this.managerId = userDTO.getId();
        this.managerName = userDTO.getName();
        this.managerEmail = userDTO.getEmail();
        this.managerPhone = userDTO.getPhone();
    }
}
