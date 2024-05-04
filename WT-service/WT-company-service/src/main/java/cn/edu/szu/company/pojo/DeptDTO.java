package cn.edu.szu.company.pojo;

import cn.edu.szu.company.pojo.domain.Department;
import cn.edu.szu.company.pojo.domain.Group;
import cn.edu.szu.feign.pojo.UserDTO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeptDTO {

    private Long id;
    private Long parentId; // 父部门ID
    private Long companyId; // 企业ID
    private String name; // 部门名称
    private Integer member_num; // 部门人数
    private Long managerId; // 负责人ID
    private String managerName; // 负责人名称
    private String introduction; // 介绍
    private Date createTime; // 创建时间

    public DeptDTO(Department department) {
       this.id=department.getId();
       this.companyId=department.getCompanyId();
       this.parentId=department.getParentId();
       this.name=department.getName();
       this.member_num=department.getMember_num();
       this.managerId=department.getManagerId();
       this.introduction=department.getIntroduction();
       this.createTime=department.getCreateTime();

    }

    public void setManager(UserDTO userDTO) {
        if (userDTO == null) {
            return;
        }
        this.managerName = userDTO.getName();
    }
}
