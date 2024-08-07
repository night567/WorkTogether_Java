package cn.edu.szu.teamwork.mapper;

import cn.edu.szu.teamwork.pojo.MessageDTO;
import cn.edu.szu.teamwork.pojo.domain.MessageUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

/**
* @author zgr24
* @description 针对表【wt_message_user(消息接收者)】的数据库操作Mapper
* @createDate 2024-06-02 17:08:48
* @Entity cn.edu.szu.teamwork.pojo.domain.MessageUser
*/
@Mapper
public interface MessageUserMapper extends BaseMapper<MessageUser> {
    List<MessageDTO> getMessageByIds(Set<String> ids);

    IPage<MessageDTO> getMessage(IPage<MessageDTO> page, Long userId, Long groupId, Boolean isRead, Boolean handleLater);

    IPage<MessageDTO> getMessageByUidAndGid(IPage<MessageDTO> page, Long userId, Long groupId);

    IPage<MessageDTO> getMessageByUidAndGidAndReadOrNot(IPage<MessageDTO> page, Long userId, Long groupId, Boolean isRead);

    IPage<MessageDTO> getMessageByUidAndGidWhereHandleLater(IPage<MessageDTO> page, Long userId, Long groupId);
}