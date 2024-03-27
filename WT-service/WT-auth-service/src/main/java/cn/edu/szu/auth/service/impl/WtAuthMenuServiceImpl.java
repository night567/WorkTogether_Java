package cn.edu.szu.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.szu.auth.domain.WtAuthMenu;
import cn.edu.szu.auth.service.WtAuthMenuService;
import cn.edu.szu.auth.mapper.WtAuthMenuMapper;
import org.springframework.stereotype.Service;

/**
* @author 86199
* @description 针对表【wt_auth_menu(菜单)】的数据库操作Service实现
* @createDate 2024-03-27 20:18:11
*/
@Service
public class WtAuthMenuServiceImpl extends ServiceImpl<WtAuthMenuMapper, WtAuthMenu>
    implements WtAuthMenuService{

}




