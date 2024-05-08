package cn.edu.szu.company.pojo;

import cn.edu.szu.company.pojo.domain.Department;
import cn.edu.szu.feign.pojo.UserDTO;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeptDTO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId; // 父部门ID
    private String parentName; // 父部门名称
    @JsonSerialize(using = ToStringSerializer.class)
    private Long companyId; // 企业ID
    private String name; // 部门名称
    @JsonSerialize(using = ToStringSerializer.class)
    private Long managerId; // 负责人ID
    private String managerName; // 负责人名称
    private String introduction; // 介绍
    private Date createTime; // 创建时间
    private Integer job; // 待分配任务数
    @JsonSerialize(using = ToStringSerializer.class)
    private Long num; // 部门人数

    public DeptDTO(Department department) {
       this.id=department.getId();
       this.companyId=department.getCompanyId();
       this.parentId=department.getParentId();
       this.name=department.getName();
       this.managerId=department.getManagerId();
       this.introduction=department.getIntroduction();
       this.createTime=department.getCreateTime();
       this.job=department.getJob();
       this.num=department.getNum();
    }

    public void setManager(UserDTO userDTO) {
        if (userDTO == null) {
            return;
        }
        this.managerName = userDTO.getName();
    }
}
