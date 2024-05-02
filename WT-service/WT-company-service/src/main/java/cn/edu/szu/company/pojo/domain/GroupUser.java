package cn.edu.szu.company.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 团队成员
 *
 * @TableName wt_group_user
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "wt_group_user")
public class GroupUser implements Serializable {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "group_id")
    private Long groupId;

    @TableField(value = "join_time")
    private Date joinTime;

    @TableField(value = "is_deleted")
    private Boolean isDeleted;
}