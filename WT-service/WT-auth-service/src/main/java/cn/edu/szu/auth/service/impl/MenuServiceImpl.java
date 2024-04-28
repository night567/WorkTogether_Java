package cn.edu.szu.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.szu.auth.domain.Menu;
import cn.edu.szu.auth.service.MenuService;
import cn.edu.szu.auth.mapper.MenuMapper;
import org.springframework.stereotype.Service;

/**
* @author zgr24
* @description 针对表【wt_auth_menu(菜单)】的数据库操作Service实现
* @createDate 2024-04-28 16:24:06
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService{

}




