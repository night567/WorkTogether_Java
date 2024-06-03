package cn.edu.szu.teamwork.service;

import cn.edu.szu.teamwork.pojo.MessageDTO;
import cn.edu.szu.teamwork.pojo.domain.Message;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author zgr24
 * @description 针对表【wt_message(消息)】的数据库操作Service
 * @createDate 2024-06-02 17:08:48
 */
public interface MessageService extends IService<Message> {
    void sandMessageAsync(Message message, List<Long> userIds);

    List<MessageDTO> getMassage(Long userId, Long groupId, Integer page, Integer size);
}
