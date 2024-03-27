package cn.edu.szu.auth.mapper;

import cn.edu.szu.auth.domain.AuthRoleAuthority;
import cn.edu.szu.auth.domain.WtAuthMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 86199
* @description 针对表【wt_auth_menu(菜单)】的数据库操作Mapper
* @createDate 2024-03-27 20:18:11
* @Entity cn.edu.szu.auth.domain.WtAuthMenu
*/
public interface WtAuthMenuMapper extends BaseMapper<WtAuthMenu> {
    List<WtAuthMenu> selectAll();
    WtAuthMenu selectByMenuId(Long menuId);
}




