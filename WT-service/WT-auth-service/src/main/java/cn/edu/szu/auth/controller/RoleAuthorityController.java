package cn.edu.szu.auth.controller;

import cn.edu.szu.auth.domain.AuthRoleAuthority;
import cn.edu.szu.auth.domain.AuthRoleAuthorityList;
import cn.edu.szu.auth.service.AuthRoleAuthorityService;
import cn.edu.szu.common.pojo.Code;
import cn.edu.szu.common.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色--权限中间表控制类
 */
@RestController
@RequestMapping("/api/authAndRole")
public class RoleAuthorityController {
    @Autowired
    AuthRoleAuthorityService authorityService;

    /**
     * 添加角色的权限
     * @param authorityList
     * @return
     */
    @PostMapping
    public Result addRoleAuthority(@RequestBody AuthRoleAuthorityList authorityList){
        authorityService.addRoleAuthority(authorityList);
        return new Result(Code.SAVE_OK,null,"添加角色权限成功");
    }

    /**
     * 删除某个权限
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delRoleAuthorityById(@PathVariable Long id){
        authorityService.delRoleAuthorityById(id);
        return new Result(Code.DELETE_OK,null,"删除角色权限成功");
    }

    /***
     * 批量删除权限
     * @param ids
     * @return
     */
    @DeleteMapping()
    public Result delRoleAuthorityById(@RequestBody List<Long> ids){
        authorityService.delRoleAuthorityByIds(ids);
        return new Result(Code.DELETE_OK,null,"删除角色多个权限成功");
    }


    /**
     * 删除某个角色所有权限
     * @param id
     * @return
     */
    @DeleteMapping("/roleId/{id}")
    public Result deleteByRoleId(@PathVariable Long id){
        authorityService.deleteByRoleId(id);
        return new Result(Code.DELETE_OK,null,"删除该角色的权限成功");
    }

/**
 * 查询角色所有权限
 */
    @GetMapping("/{id}")
    public Result selectByRoleId(@PathVariable Long id){
        List<AuthRoleAuthority> list = authorityService.getByRoleId(id);
        return new Result(Code.GET_OK,list,"查询该角色的权限成功");
    }
}
