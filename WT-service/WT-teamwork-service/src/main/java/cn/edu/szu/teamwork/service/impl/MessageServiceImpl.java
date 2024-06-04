package cn.edu.szu.teamwork.service.impl;

import cn.edu.szu.feign.client.UserClient;
import cn.edu.szu.feign.pojo.UserDTO;
import cn.edu.szu.teamwork.mapper.MessageMapper;
import cn.edu.szu.teamwork.mapper.MessageUserMapper;
import cn.edu.szu.teamwork.pojo.MessageDTO;
import cn.edu.szu.teamwork.pojo.domain.Message;
import cn.edu.szu.teamwork.pojo.domain.MessageUser;
import cn.edu.szu.teamwork.service.MessageService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zgr24
 * @description 针对表【wt_message(消息)】的数据库操作Service实现
 * @createDate 2024-06-02 17:08:48
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message>
        implements MessageService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private MessageUserMapper messageUserMapper;
    @Autowired
    private UserClient userClient;

    @Override
    public String setMsgToIsRead(Long id, Long uid) {
        MessageUser msgUser = messageUserMapper.selectById(id);
        if (!msgUser.getUserId().equals(uid)) {
            return "信息错误，无法修改";
        }
        msgUser.setIsRead(true);
        int cnt = messageUserMapper.updateById(msgUser);
        if (cnt > 0) {
            return "更新成功";
        }
        return "更新失败";
    }

    @Override
    public String setMsgToIsReadByIds(List<Long> ids, Long uid) {
        List<MessageUser> messageUsers = messageUserMapper.selectBatchIds(ids);
        for (MessageUser msgu: messageUsers){
            msgu.setIsRead(true);
            if (!msgu.getUserId().equals(uid)) {
                return "信息错误，无法修改";
            }
//            @TODO  改为xml的修改
            messageUserMapper.updateById(msgu);
        }
        return "更新成功";
    }

    @Override
    public String setMsgToLater(Long id, Long uid) {
        MessageUser msgUser = messageUserMapper.selectById(id);
        if (!msgUser.getUserId().equals(uid)) {
            return "信息错误，无法修改";
        }
        msgUser.setHandleLater(true);
        int cnt = messageUserMapper.updateById(msgUser);
        if (cnt > 0) {
            return "更新成功";
        }
        return "更新失败";
    }
    @Override
    public String setMsgToLaterByIds(List<Long> ids, Long uid) {
        List<MessageUser> messageUsers = messageUserMapper.selectBatchIds(ids);
        for (MessageUser msgu: messageUsers){
            msgu.setHandleLater(true);
            if (!msgu.getUserId().equals(uid)) {
                return "信息错误，无法修改";
            }
//            @TODO  改为xml的修改
            messageUserMapper.updateById(msgu);
        }
        return "更新成功";
    }
    @Override
    @Async // 声明异步方法
    @Transactional
    public void sandMessageAsync(Message message, List<Long> userIds) {
        // 保存消息到数据库和Redis
        save(message);

        Long messageId = message.getId();
        for (Long userId : userIds) {
            MessageUser messageUser = new MessageUser();
            messageUser.setMessageId(messageId);
            messageUser.setUserId(userId);
            messageUserMapper.insert(messageUser);
        }
    }

    @Override
    public List<MessageDTO> getMassage(Long userId, Long groupId, Boolean isRead, Boolean handleLater, Integer pageNum, Integer pageSize) {
        // 获取消息列表（分页查询）
        IPage<MessageDTO> page = messageUserMapper.getMessage(
                Page.of(pageNum, pageSize), userId, groupId,
                isRead, handleLater);
        List<MessageDTO> messageList = page.getRecords();

        // 填入用户信息
        setUserForMessageList(messageList);

        return messageList;
    }

    @Override
    public List<MessageDTO> getMessageWithIsRead(Long userId, Long groupId, Boolean isRead, Integer pageNum, Integer PageSize) {
        // 获取消息列表（分页查询）
        IPage<MessageDTO> page = messageUserMapper.getMessageByUidAndGidAndReadOrNot(
                Page.of(pageNum, PageSize), userId, groupId, isRead);
        List<MessageDTO> messageList = page.getRecords();

        // 填入用户信息
        setUserForMessageList(messageList);

        return messageList;
    }

    @Override
    public List<MessageDTO> getMessageWithHandleLater(Long userId, Long groupId, Integer pageNum, Integer PageSize) {
        // 获取消息列表（分页查询）
        IPage<MessageDTO> page = messageUserMapper.getMessageByUidAndGidWhereHandleLater(
                Page.of(pageNum, PageSize), userId, groupId);
        List<MessageDTO> messageList = page.getRecords();

        // 填入用户信息
        setUserForMessageList(messageList);

        return messageList;
    }

    private void setUserForMessageList(List<MessageDTO> messageList) {
        for (MessageDTO messageDTO : messageList) {
            Long uid = messageDTO.getUserId();
            UserDTO user = userClient.getUserById(uid);
            if (user != null) {
                messageDTO.setUser(user);
            }
        }
    }
}




