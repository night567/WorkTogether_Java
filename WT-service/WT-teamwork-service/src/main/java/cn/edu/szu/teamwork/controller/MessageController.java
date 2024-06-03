package cn.edu.szu.teamwork.controller;

import cn.edu.szu.common.pojo.Code;
import cn.edu.szu.common.pojo.Result;
import cn.edu.szu.common.utils.JwtUtil;
import cn.edu.szu.teamwork.pojo.MessageDTO;
import cn.edu.szu.teamwork.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @GetMapping("/{groupId}")
    public Result getMessage(@RequestHeader("Authorization") String token,
                             @PathVariable Long groupId,
                             @RequestParam(value = "page", defaultValue = "0") Integer page,
                             @RequestParam(value = "size", defaultValue = "100") Integer size) {
        Long userId = JwtUtil.getUserId(token);
        List<MessageDTO> messageList = messageService.getMassage(userId, groupId, page, size);
        if (messageList != null) {
            return new Result(Code.GET_OK, messageList, "获取消息成功");
        }
        return new Result(Code.GET_ERR, Collections.emptyList(), "获取消息失败");
    }
}
