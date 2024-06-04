package cn.edu.szu.teamwork.service;

import cn.edu.szu.teamwork.pojo.MessageDTO;
import cn.edu.szu.teamwork.pojo.domain.Message;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author zgr24 ywf
 * @description 针对表【wt_message(消息)】的数据库操作Service
 * @createDate 2024-06-02 17:08:48
 */
public interface MessageService extends IService<Message> {
    void sandMessageAsync(Message message, List<Long> userIds);

    List<MessageDTO> getMassage(Long userId, Long groupId, Boolean isRead, Boolean handleLater, Integer pageNum, Integer pageSize);

    List<MessageDTO> getMessageWithIsRead(Long userId, Long groupId, Boolean isRead, Integer pageNum, Integer PageSize);

    List<MessageDTO> getMessageWithHandleLater(Long userId, Long groupId, Integer pageNum, Integer PageSize);

    String setMsgToIsRead(Long id, Long uid);
    String setMsgToIsReadByIds(List<Long >ids, Long uid);
    String setMsgToLaterByIds(List<Long >ids, Long uid);
    String setMsgToLater(Long id, Long uid);
}
