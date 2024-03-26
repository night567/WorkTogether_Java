package cn.edu.szu.user.controller;

import cn.edu.szu.common.domain.Code;
import cn.edu.szu.common.domain.Result;
import cn.edu.szu.user.pojo.LoginForm;
import cn.edu.szu.user.pojo.User;
import cn.edu.szu.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/createUser")
    public Result createAccount(@RequestBody LoginForm loginForm) {
        String token = userService.createAccount(loginForm);
        if (token != null && !token.isEmpty()) {
            return new Result(Code.UPDATE_OK, token, "登录成功");
        } else {
            return new Result(Code.UPDATE_ERR, "", "登录失败");
        }
    }

    @GetMapping("/login")
    public Result login(@RequestBody LoginForm loginForm) {
        String jwt = userService.login(loginForm);
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
}
