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
public class Menu implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 菜单名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 是否公开菜单
就是无需分配就可以访问的。所有人可见
     */
    @TableField(value = "is_public")
    private Boolean is_public;

    /**
     * 对应路由path
     */
    @TableField(value = "path")
    private String path;

    /**
     * 菜单层级
     */
    @TableField(value = "level")
    private Integer level;

    /**
     * 父级菜单id
     */
    @TableField(value = "parent_id")
    private Long parent_id;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}