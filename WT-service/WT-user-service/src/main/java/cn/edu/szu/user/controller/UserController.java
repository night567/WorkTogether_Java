package cn.edu.szu.user.controller;

import cn.edu.szu.common.domain.Code;
import cn.edu.szu.common.domain.Result;
import cn.edu.szu.company.service.CompanyUserService;
import cn.edu.szu.user.pojo.LoginDTO;
import cn.edu.szu.user.pojo.User;
import cn.edu.szu.user.pojo.UserDTO;
import cn.edu.szu.user.service.UserService;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private CompanyUserService companyUserService;


    @PostMapping("/createUser")
    public Result createAccount(@RequestBody LoginDTO loginDTO) {
        String token = userService.createAccount(loginDTO);
        if (token != null && !token.isEmpty()) {
            return new Result(Code.UPDATE_OK, token, "登录成功");
        } else {
            return new Result(Code.UPDATE_ERR, "", "登录失败");
        }
    }

    @PostMapping("/login")
    public Result login(@RequestBody LoginDTO loginDTO) {
        String jwt = userService.login(loginDTO);
        if (jwt == null || jwt.isEmpty()) {
            return new Result(Code.GET_ERR, null, "登录失败");
        }
        return new Result(Code.GET_OK, jwt, "登录成功");
    }

    @GetMapping("/findByEmail/{email}")
    public Result getUserByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        if (user != null) {
            return new Result(Code.GET_OK, user, "获取成功");
        } else {
            return new Result(Code.GET_ERR, null, "获取失败");
        }
    }

    @GetMapping("/{id}")
    public Result getUserById(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user != null) {
            return new Result(Code.GET_OK, user, "获取成功");
        } else {
            return new Result(Code.GET_ERR, null, "获取失败");
        }
    }

    @GetMapping("/company/{id}")
    public Result getUserByCompany(@PathVariable Long id){
        List<UserDTO> users = userService.getUserByCompany(id);
        if (users != null) {
            return new Result(Code.GET_OK, users, "获取成功");
        } else {
            return new Result(Code.GET_ERR, null, "获取失败");
        }
    }

    @PostMapping("/status")
    public Result updateStatus(@RequestBody UserDTO user) {
        boolean b = userService.updateById(user);
        if (b) {
            return new Result(Code.UPDATE_OK, true, "修改成功");
        } else {
            return new Result(Code.UPDATE_ERR, false, "修改失败");
        }
    }

    @DeleteMapping("/delete_member")
    public Result deleteMember(@Param(value = "email") String email){
        User user = userService.getUserByEmail(email);
        boolean flag = companyUserService.deleteMember(user.getId());
        if(flag){
            return  new Result(Code.DELETE_OK,null,"移除成功");
        }
        return  new Result(Code.DELETE_ERR,null,"移除失败");
    }
}
