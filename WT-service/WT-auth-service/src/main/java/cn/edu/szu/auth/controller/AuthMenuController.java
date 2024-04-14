package cn.edu.szu.auth.controller;

import cn.edu.szu.auth.domain.WtAuthMenu;
import cn.edu.szu.auth.service.WtAuthMenuService;
import cn.edu.szu.common.pojo.Code;
import cn.edu.szu.common.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class AuthMenuController {
    @Autowired
    private WtAuthMenuService WtAuthMenuService;

    /**
     * 查询所有菜单
     */
    @GetMapping
    public Result selectAllMenu() {
        List<WtAuthMenu> list = WtAuthMenuService.selectAllMenu();
        if (list == null){
            return new Result(Code.GET_ERR, null, "查询失败");
        }
        return new Result(Code.GET_OK, list, "查询成功");
    }

    /**
     * 按照id查询
     * @param id
     * @return
     */
    @GetMapping("/selectById/{id}")
    public Result selectByMenuId(@PathVariable Long id) {
        WtAuthMenu menu = WtAuthMenuService.selectByMenuId(id);
        if (menu == null) {
            return new Result(Code.GET_ERR, null, "查询失败");
        }
        return new Result(Code.GET_OK, menu, "查询成功");
    }
}
