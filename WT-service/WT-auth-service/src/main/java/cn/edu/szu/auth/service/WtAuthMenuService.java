package cn.edu.szu.auth.service;

import cn.edu.szu.auth.domain.AuthRole;
import cn.edu.szu.auth.domain.WtAuthMenu;

import cn.edu.szu.auth.mapper.WtAuthMenuMapper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 86199
* @description 针对表【wt_auth_menu(菜单)】的数据库操作Service
* @createDate 2024-03-27 20:18:11
*/
public interface WtAuthMenuService extends IService<WtAuthMenu> {

    /**
     * 查询所有菜单
     */

    List<WtAuthMenu> selectAllMenu();

    /**
     * 根据id查询菜单
     * @param id
     */
    WtAuthMenu selectByMenuId(Long id);

}
