package cn.edu.szu.teamwork.controller;

import cn.edu.szu.common.pojo.Code;
import cn.edu.szu.common.pojo.Result;
import cn.edu.szu.common.utils.JwtUtil;
import cn.edu.szu.teamwork.pojo.MessageDTO;
import cn.edu.szu.teamwork.pojo.domain.Message;
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

    @GetMapping
    public Result getMessage(@RequestHeader("Authorization") String token,
                             @RequestBody MessageDTO message,
                             @RequestParam(value = "page", defaultValue = "0") Integer page,
                             @RequestParam(value = "size", defaultValue = "100") Integer size) {
        Long userId = JwtUtil.getUserId(token);
        List<MessageDTO> messageList = messageService.getMessage(userId, message, page, size);
        if (messageList != null) {
            return new Result(Code.GET_OK, messageList, "获取消息成功");
        }
        return new Result(Code.GET_ERR, Collections.emptyList(), "获取消息失败");
    }

    @GetMapping("/{groupId}")
    public Result getAllMessage(@RequestHeader("Authorization") String token,
                                @PathVariable Long groupId,
                                @RequestParam(value = "page", defaultValue = "0") Integer page,
                                @RequestParam(value = "size", defaultValue = "100") Integer size) {
        Long userId = JwtUtil.getUserId(token);
        List<MessageDTO> messageList = messageService.getAllMassage(userId, groupId, page, size);
        if (messageList != null) {
            return new Result(Code.GET_OK, messageList, "获取消息成功");
        }
        return new Result(Code.GET_ERR, Collections.emptyList(), "获取消息失败");
    }

    @PutMapping("/toIsRead/{id}")
    public Result setMsgToIsRead(@PathVariable Long id,@RequestHeader("Authorization") String token){
        Long userId = JwtUtil.getUserId(token);
        String msg = messageService.setMsgToIsRead(id,userId);
        if (msg.equals("信息错误，无法修改")){
            return new Result(Code.UPDATE_ERR,null,msg);
        }
        return new Result(Code.UPDATE_OK,null,msg);
    }
    @PutMapping("/toLater/{id}")
    public Result setMsgToLater(@PathVariable Long id,@RequestHeader("Authorization") String token){
        Long userId = JwtUtil.getUserId(token);
        String msg = messageService.setMsgToLater(id,userId);
        if (msg.equals("信息错误，无法修改")){
            return new Result(Code.UPDATE_ERR,null,msg);
        }
        return new Result(Code.UPDATE_OK,null,msg);
    }

    @GetMapping("/isRead/{groupId}/{isRead}")
    public Result getMessageWithIsRead(@RequestHeader("Authorization") String token,
                                       @PathVariable Long groupId,
                                       @PathVariable Boolean isRead,
                                       @RequestParam(value = "page", defaultValue = "0") Integer page,
                                       @RequestParam(value = "size", defaultValue = "100") Integer size) {
        Long userId = JwtUtil.getUserId(token);
        List<MessageDTO> messageList = messageService.getMessageWithIsRead(userId, groupId, isRead, page, size);
        if (messageList != null) {
            return new Result(Code.GET_OK, messageList, "获取消息成功");
        }
        return new Result(Code.GET_ERR, Collections.emptyList(), "获取消息失败");
    }

    @GetMapping("/handleLater/{groupId}")
    public Result getMessageWithHandleLater(@RequestHeader("Authorization") String token,
                                            @PathVariable Long groupId,
                                            @RequestParam(value = "page", defaultValue = "0") Integer page,
                                            @RequestParam(value = "size", defaultValue = "100") Integer size) {
        Long userId = JwtUtil.getUserId(token);
        List<MessageDTO> messageList = messageService.getMessageWithHandleLater(userId, groupId, page, size);
        if (messageList != null) {
            return new Result(Code.GET_OK, messageList, "获取消息成功");
        }
        return new Result(Code.GET_ERR, Collections.emptyList(), "获取消息失败");
    }
}
