package cn.edu.szu.auth.controller;

import cn.edu.szu.auth.domain.AuthRole;
import cn.edu.szu.auth.domain.UserRoleListDTO;
import cn.edu.szu.auth.service.UserRoleService;
import cn.edu.szu.common.pojo.Code;
import cn.edu.szu.common.pojo.Result;
import cn.edu.szu.common.utils.JwtUtil;
import cn.edu.szu.feign.pojo.CheckAuthDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 给用户赋予角色接口
 */
@RestController
@RequestMapping("/api/userAndRole")
public class UserRoleController {
    @Autowired
    private UserRoleService userRoleService;

    @PutMapping()
    public Result saveRoleToUser(@RequestHeader("Authorization") String token, @RequestBody UserRoleListDTO userRoleList) {
        Long userId = JwtUtil.getUserId(token);
        userRoleList.setCreateUser(userId);
        boolean bool = userRoleService.saveRoleToUser(userRoleList);
        if (!bool) {
            return new Result(Code.SAVE_ERR, false, "保存失败");
        }
        return new Result(Code.SAVE_OK, true, "保存成功");
    }

    @GetMapping
    public Result getRoleByToken(@RequestHeader("Authorization") String token,
                                 @RequestHeader("companyId") String companyId) {
        Long userId = JwtUtil.getUserId(token);
        List<AuthRole> roleList = userRoleService.getRoleByUserId(userId, companyId);
        return new Result(Code.GET_OK, roleList, "查询成功");
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
