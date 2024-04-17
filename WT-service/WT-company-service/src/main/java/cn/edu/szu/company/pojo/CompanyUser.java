package cn.edu.szu.company.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "wt_company_user")
public class CompanyUser implements Serializable {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField(value = "user_id")
    private Long userId; // 用户ID

    @TableField(value = "company_id")
    private Long companyId; // 企业ID

    @TableField(value = "dept_id")
    private Long deptId; // 部门ID

    @TableField(value = "status")
    private Boolean status; // 启用状态 1启用 0禁用

    @TableField(value = "join_time")
    private Date joinTime; // 加入时间

    @TableField(value = "is_deleted")
    private Boolean is_deleted; // 逻辑删除
}
