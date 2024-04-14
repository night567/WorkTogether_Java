package cn.edu.szu.user.controller;

import cn.edu.szu.common.domain.Code;
import cn.edu.szu.common.domain.Result;
import cn.edu.szu.user.pojo.InviteDTO;
import cn.edu.szu.user.pojo.LoginDTO;
import cn.edu.szu.user.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/sendVerificationCode")
    public Result sendVerificationEmail(@RequestBody LoginDTO request) {
        boolean b = emailService.sendVerificationCode(request.getEmail());
        if (b) {
            return new Result(Code.SAVE_OK, true, "发送成功");
        } else {
            return new Result(Code.SAVE_ERR, false, "发送失败");
        }
    }

    @PostMapping("/sendInviteCode")
    public Result sendInviteEmail(@RequestBody InviteDTO request) {
        boolean b = emailService.sendInviteCode(request.getEmail(),request.getCompanyId());
        if (b) {
            return new Result(Code.SAVE_OK, true, "发送成功");
        } else {
            return new Result(Code.SAVE_ERR, false, "发送失败");
        }
    }
}

