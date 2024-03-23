package cn.edu.szu.auth.controller;

import cn.edu.szu.auth.domain.LoginFormDTO;
import cn.edu.szu.common.controller.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class LoginController {
    @PostMapping("/createUser")
    public Result createUser(@RequestBody LoginFormDTO loginFormDTO) {

        return null;
    }

    @PostMapping("/login")
    public Result login(@RequestBody LoginFormDTO loginFormDTO) {

        return null;
    }
}
