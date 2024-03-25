package cn.edu.szu.auth.controller;

import cn.edu.szu.auth.domain.AuthRole;
import cn.edu.szu.auth.service.AuthRoleService;
import cn.edu.szu.common.domain.Code;
import cn.edu.szu.common.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * 权限控制  负责增删角色，给角色赋予权限等
 */
@RestController
@RequestMapping("/api/auth")
public class AuthorizationController {
    @Autowired
    private AuthRoleService authRoleService;

    /**
     * 新增角色
     * @param role
     * @return
     */
    @PostMapping
    public Result addRole(AuthRole role){
        authRoleService.addRole(role);
        return new Result(Code.SAVE_OK,null,"成功添加角色");
    }

    @GetMapping
    public Result selectAll(){
        List<AuthRole> list = authRoleService.selectAllRole();
        return new Result(Code.GET_OK,list,"查询成功");
    }


}
