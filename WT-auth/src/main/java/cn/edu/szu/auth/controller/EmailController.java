package cn.edu.szu.auth.controller;

import cn.edu.szu.auth.service.EmailService;
import cn.edu.szu.common.controller.Code;
import cn.edu.szu.common.controller.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping
    public Result sendEmail(@RequestBody String email) {
        boolean b = emailService.sendVerificationCode(email);
        if (b) {
            return new Result(Code.SAVE_OK, true, "发送成功");
        } else {
            return new Result(Code.SAVE_ERR, false, "发送失败");
        }
    }
}
