package cn.edu.szu.auth.controller;

import cn.edu.szu.auth.domain.AuthRole;
import cn.edu.szu.auth.service.AuthRoleService;
import cn.edu.szu.common.domain.Code;
import cn.edu.szu.common.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Result addRole(@RequestBody AuthRole role){
        authRoleService.addRole(role);
        return new Result(Code.SAVE_OK,null,"成功添加角色");
    }

    /**
     * 查询所有角色
     * @return
     */
    @GetMapping
    public Result selectAll(){
        List<AuthRole> list = authRoleService.selectAllRole();
        return new Result(Code.GET_OK,list,"查询成功");
    }

    /**
     * 按照id查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result selectById(@PathVariable Long id){
        AuthRole role = authRoleService.selectById(id);
        return new Result(Code.GET_OK,role,"查询成功");
    }

    /**
     * 按照id删除角色
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Long id){
        authRoleService.delRole(id);
        return new Result(Code.DELETE_OK,null,"删除角色成功");
    }

    /**
     * 根据id更新角色
     * @param role
     * @return
     */
    @PutMapping
    public Result updateById(@RequestBody AuthRole role){
        authRoleService.updateById(role);
        return new Result(Code.UPDATE_OK,null,"更新角色成功");
    }



}
