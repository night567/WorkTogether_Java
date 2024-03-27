package cn.edu.szu.auth.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 菜单
 * @TableName wt_auth_menu
 */
@TableName(value ="wt_auth_menu")
@Data
public class WtAuthMenu implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 菜单名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 功能描述
     */
    @TableField(value = "describe")
    private String describe;

    /**
     * 是否公开菜单
就是无需分配就可以访问的。所有人可见
     */
    @TableField(value = "is_public")
    private Boolean isPublic;

    /**
     * 对应路由path
     */
    @TableField(value = "path")
    private String path;

    /**
     * 对应路由组件component
     */
    @TableField(value = "component")
    private String component;

    /**
     * 状态(是否可用 0:不可用 1:可用)
     */
    @TableField(value = "is_enable")
    private Boolean isEnable;

    /**
     * 排序
     */
    @TableField(value = "sort_value")
    private Integer sortValue;

    /**
     * 菜单分组
     */
    @TableField(value = "group")
    private String group;

    /**
     * 父级菜单id
     */
    @TableField(value = "parent_id")
    private Long parentId;
}