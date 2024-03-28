package cn.edu.szu.auth.service.impl;

import cn.edu.szu.auth.domain.AuthRole;
import cn.edu.szu.auth.domain.AuthRoleAuthority;
import cn.edu.szu.auth.expection.RoleNotFoundException;
import cn.edu.szu.auth.mapper.AuthRoleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.szu.auth.domain.WtAuthMenu;
import cn.edu.szu.auth.service.WtAuthMenuService;
import cn.edu.szu.auth.mapper.WtAuthMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
* @author 86199
* @description 针对表【wt_auth_menu(菜单)】的数据库操作Service实现
* @createDate 2024-03-27 20:18:11
*/
@Service
public class WtAuthMenuServiceImpl extends ServiceImpl<WtAuthMenuMapper, WtAuthMenu>
    implements WtAuthMenuService{

    @Autowired
    private WtAuthMenuMapper menuMapper;

    /**
     * 返回所有角色
     */
    @Override
    public List<WtAuthMenu> selectAllMenu() {
        return menuMapper.selectList(null);
    }

    @Override
    public WtAuthMenu selectByMenuId(Long id) {
        WtAuthMenu menu = menuMapper.selectById(id);
        if (menu == null){
            throw new RoleNotFoundException("Role not found with id: " + id);
        }
        return menu;
    }
}




