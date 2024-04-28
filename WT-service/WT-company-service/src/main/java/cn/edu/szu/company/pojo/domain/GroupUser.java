package cn.edu.szu.company.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 团队成员
 *
 * @TableName wt_group_user
 */
@TableName(value = "wt_group_user")
@Data
public class GroupUser implements Serializable {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField(value = "user_id")
    private Long user_id;

    @TableField(value = "group_id")
    private Long group_id;

    @TableField(value = "join_time")
    private Date join_time;

    @TableField(value = "is_deleted")
    private Boolean is_deleted;
}