package cn.edu.szu.user.controller;

import cn.edu.szu.common.pojo.Code;
import cn.edu.szu.common.pojo.Result;
import cn.edu.szu.user.pojo.InviteDTO;
import cn.edu.szu.user.pojo.LoginDTO;
import cn.edu.szu.user.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/sendRegistrationCode")
    public Result sendRegistrationCode(@RequestBody LoginDTO request) {
        String code = emailService.generateRegistrationCode(request.getEmail());
        if (code != null) {
            emailService.sendCodeEmailByMQ(request.getEmail(), code);
            long end = System.currentTimeMillis();
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
//        System.out.println("??");
        String emails = request.getEmail();
        Long cid = request.getCompanyId();
        boolean b = true;
        System.out.println(emails);
        for (String s : emails.trim().split("\n")) {
            System.out.println(s);
            s = s.trim();
            b = emailService.sendInviteCode(s,request.getCompanyId()) & b;
            if (!b){
                return new Result(Code.SAVE_ERR, false, "发送失败,失败的邮箱是： "+s);
            }
        }

        return new Result(Code.SAVE_OK, true, "发送成功");

    }
}

