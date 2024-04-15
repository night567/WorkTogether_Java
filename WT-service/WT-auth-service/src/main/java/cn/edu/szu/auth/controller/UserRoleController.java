package cn.edu.szu.auth.controller;

import cn.edu.szu.feign.pojo.CheckAuthDTO;
import cn.edu.szu.auth.domain.UserRoleListDTO;
import cn.edu.szu.auth.service.UserRoleService;
import cn.edu.szu.common.pojo.Code;
import cn.edu.szu.common.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 给用户赋予角色接口
 */
@RestController
@RequestMapping("/api/userAndRole")
public class UserRoleController {
    @Autowired
    private UserRoleService userRoleService;

    @PostMapping()
    public Result saveRoleToUser(@RequestBody UserRoleListDTO userRoleList) {
        boolean bool = userRoleService.saveRoleToUser(userRoleList);
        if (!bool) {
            return new Result(Code.SAVE_ERR, false, "保存失败");
        }
        return new Result(Code.SAVE_OK, true, "保存成功");
    }

    @PostMapping("/check")
    public Result checkAuth(@RequestBody CheckAuthDTO checkAuthDTO) {
        boolean check = userRoleService.checkAuth(checkAuthDTO);
        if (check) {
            return new Result(Code.SAVE_OK, true, "权限认证通过");
        }
        return new Result(Code.SAVE_ERR, false, "权限认证不通过");
    }
}
