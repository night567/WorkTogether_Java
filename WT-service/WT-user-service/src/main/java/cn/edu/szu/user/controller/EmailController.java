package cn.edu.szu.user.controller;

import cn.edu.szu.common.pojo.Code;
import cn.edu.szu.common.pojo.Result;
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

    @PostMapping("/sendRegistrationCode")
    public Result sendRegistrationCode(@RequestBody LoginDTO request) {
        String code = emailService.generateRegistrationCode(request.getEmail());
        if (code != null) {
            emailService.sendCodeEmail(request.getEmail(), code);
            return new Result(Code.SAVE_OK, true, "发送成功");
        } else {
            return new Result(Code.SAVE_ERR, false, "发送失败,邮箱错误/账号已存在，请检查注册邮箱");
        }
    }

    @PostMapping("/checkRegistrationCode")
    public Result checkRegistrationCode(@RequestBody LoginDTO request) {
        boolean b = emailService.checkRegistrationCode(request.getEmail(), request.getVerificationCode());
        if (b) {
            return new Result(Code.SAVE_OK, true, "验证成功");
        } else {
            return new Result(Code.SAVE_ERR, false, "验证失败");
        }
    }

    @PostMapping("/sendVerificationCode")
    public Result sendVerificationCode(@RequestBody LoginDTO request) {
        String code = emailService.generateVerificationCode(request.getEmail());
        if (code != null) {
            emailService.sendCodeEmail(request.getEmail(), code);
            return new Result(Code.SAVE_OK, true, "发送成功");
        } else {
            return new Result(Code.SAVE_ERR, false, "发送失败,账号不存在，请检查邮箱");
        }
    }

    @PostMapping("/checkVerificationCode")
    public Result checkVerificationCode(@RequestBody LoginDTO request) {
        boolean b = emailService.checkVerificationCode(request.getEmail(), request.getVerificationCode());
        if (b) {
            return new Result(Code.SAVE_OK, true, "验证成功");
        } else {
            return new Result(Code.SAVE_ERR, false, "验证失败");
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

