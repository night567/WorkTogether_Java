package cn.edu.szu.auth.controller;

import cn.edu.szu.auth.domain.LoginFormDTO;
import cn.edu.szu.auth.service.LoginService;
import cn.edu.szu.common.domain.Code;
import cn.edu.szu.common.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/createUser")
    public Result createAccount(@RequestBody LoginFormDTO loginFormDTO) {
        String token = loginService.createAccount(loginFormDTO);
        if (token != null && !token.isEmpty()) {
            return new Result(Code.UPDATE_OK, token, "登录成功");
        } else {
            return new Result(Code.UPDATE_ERR, "", "登录失败");
        }
    }

    @GetMapping("/login")
    public Result login(@RequestBody LoginFormDTO loginFormDTO) {

        return new Result(Code.GET_OK, 1, "登录成功");
    }
}
