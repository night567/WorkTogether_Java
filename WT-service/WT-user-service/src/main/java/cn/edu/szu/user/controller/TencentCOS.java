package cn.edu.szu.user.controller;

import cn.edu.szu.common.pojo.Code;
import cn.edu.szu.common.pojo.Result;
import cn.edu.szu.common.utils.JwtUtil;
import cn.edu.szu.user.service.UserService;
import cn.edu.szu.user.service.impl.COSClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/user")
public class TencentCOS {
    @Autowired
    private UserService userService;

    @PutMapping("/uploadImage")
    public Result uploadImage(@RequestParam("image") MultipartFile file, @RequestHeader("Authorization") String token) {
        Long userId = JwtUtil.getUserId(token);
        String url;
        boolean b;
        try {
            url = COSClientUtil.sendToTencentCOS(file);
            b = userService.updateUserImage(url,userId);
        } catch (IOException e) {
            throw new RuntimeException("上传失败");
        }
        if(b){
            return new Result(Code.SAVE_OK, url, "上传成功");
        }
        return new Result(Code.SAVE_ERR, null, "上传失败");
    }


}
