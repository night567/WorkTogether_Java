package cn.edu.szu.teamwork.mapper;

import cn.edu.szu.teamwork.pojo.MessageDTO;
import cn.edu.szu.teamwork.pojo.domain.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

/**
* @author zgr24
* @description 针对表【wt_message(消息)】的数据库操作Mapper
* @createDate 2024-06-02 17:08:48
* @Entity cn.edu.szu.teamwork.pojo.domain.Message
*/
@Mapper
public interface MessageMapper extends BaseMapper<Message> {

}




