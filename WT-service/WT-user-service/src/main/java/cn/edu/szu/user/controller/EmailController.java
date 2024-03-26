package cn.edu.szu.user.controller;

import cn.edu.szu.common.domain.Code;
import cn.edu.szu.common.domain.Result;
import cn.edu.szu.user.pojo.LoginDTO;
import cn.edu.szu.user.service.EmailService;
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

    @PostMapping("/sendVerificationCode")
    public Result sendEmail(@RequestBody LoginDTO email) {
        boolean b = emailService.sendVerificationCode(email.getEmail());
        if (b) {
            return new Result(Code.SAVE_OK, true, "发送成功");
        } else {
            return new Result(Code.SAVE_ERR, false, "发送失败");
        }
    }
}
