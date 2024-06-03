package cn.edu.szu.teamwork.service.impl;

import cn.edu.szu.feign.client.UserClient;
import cn.edu.szu.feign.pojo.UserDTO;
import cn.edu.szu.teamwork.mapper.MessageMapper;
import cn.edu.szu.teamwork.mapper.MessageUserMapper;
import cn.edu.szu.teamwork.pojo.MessageDTO;
import cn.edu.szu.teamwork.pojo.domain.Message;
import cn.edu.szu.teamwork.pojo.domain.MessageUser;
import cn.edu.szu.teamwork.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static cn.edu.szu.common.utils.RedisConstants.FEED_KEY;

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
    @Async // 声明异步方法
    @Transactional
    public void sandMessageAsync(Message message, List<Long> userIds) {
        // 保存消息到数据库和Redis
        save(message);

        Long groupId = message.getGroupId();
        Long messageId = message.getId();
        for (Long userId : userIds) {
            MessageUser messageUser = new MessageUser();
            messageUser.setMessageId(messageId);
            messageUser.setUserId(userId);
            messageUserMapper.insert(messageUser);

            // 将用户接收的邮件保存到Redis
            String key = FEED_KEY + groupId + ":" + userId;
            stringRedisTemplate.opsForZSet().add(key, messageUser.getId().toString(), System.currentTimeMillis());
        }
    }

    @Override
    public List<MessageDTO> getMassage(Long userId, Long groupId, Integer page, Integer size) {
        String key = FEED_KEY + groupId + ":" + userId;
        long start = (long) (page - 1) * size;
        long end = start + size - 1;

        Set<String> messageIds = stringRedisTemplate.opsForZSet().range(key, start, end);
        List<MessageDTO> messageList = messageUserMapper.getMessageByIds(messageIds);

        for (MessageDTO messageDTO : messageList) {
            Long uid = messageDTO.getUserId();
            UserDTO user = userClient.getUserById(uid);
            if (user != null) {
                messageDTO.setUser(user);
            }
        }

        return messageList;
    }
}




