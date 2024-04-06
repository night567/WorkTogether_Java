package cn.edu.szu.company.pojo;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "wt_company_user")
public class CompanyUser implements Serializable {
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id; //主键
    @TableField(value = "user_id")
    private Long userId; //用户ID
    @TableField(value = "company_id")
    private Long companyId; //公司ID
    @TableField(value = "join_time")
    private DateTime joinTime;  //加入公司的时间

}
