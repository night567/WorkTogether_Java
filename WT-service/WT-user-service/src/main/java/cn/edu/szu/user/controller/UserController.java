package cn.edu.szu.user.controller;

import cn.edu.szu.common.domain.Code;
import cn.edu.szu.common.domain.Result;
import cn.edu.szu.user.pojo.User;
import cn.edu.szu.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

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
