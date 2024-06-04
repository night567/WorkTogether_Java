package cn.edu.szu.user.controller;

import cn.edu.szu.common.pojo.Code;
import cn.edu.szu.common.pojo.Result;
import cn.edu.szu.user.service.impl.COSClientUtil;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/user")
public class TencentCOS {

    @PutMapping("/uploadImage")
    public Result uploadImage(@RequestParam("image") MultipartFile file) {
        String url;
        try {
            url = COSClientUtil.sendToTencentCOS(file);
        } catch (IOException e) {
            throw new RuntimeException("上传失败");
        }
        return new Result(Code.SAVE_OK, url, "上传成功");
    }


}
