package cn.edu.szu.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 王奇艳
 * 资源实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResource {
    //资源ID
    private Integer id;
    //资源名称
    private String name;
    //资源路径
    private String url;
    //资源描述
    private String describe;
    //对应菜单的ID
    private Integer menuId;
}
