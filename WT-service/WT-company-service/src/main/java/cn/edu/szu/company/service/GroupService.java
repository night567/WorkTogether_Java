package cn.edu.szu.company.service;

import cn.edu.szu.company.pojo.GroupDTO;
import cn.edu.szu.company.pojo.GroupUserDTO;
import cn.edu.szu.company.pojo.MemberDTO;
import cn.edu.szu.company.pojo.domain.Group;
import cn.edu.szu.company.pojo.domain.UserGroupRequest;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author zgr24
 * @description 针对表【wt_group(团队)】的数据库操作Service
 * @createDate 2024-04-28 16:39:41
 */
public interface GroupService {
    boolean createGroup(Long companyId, GroupDTO groupDTO);

    boolean createByExcel(Long companyId, MultipartFile groupFile);

    boolean deleteGroup(Long deptId);

    boolean updateGroup(GroupDTO groupDTO);

    List<GroupDTO> getAllGroup(Long companyId);

    GroupDTO getGroupById(Long id);

    List<Group> selectMyGroup(Long uid, Long cid);

    List<MemberDTO> getGroupMember(Long id);

    boolean addMemberToGroup(List<String> emails, Long gid);

    boolean delMemberFromGroup(Long uid, Long gid);

    boolean delMemberFromGroup(List<Long> uid, Long gid);

    boolean updateMember(UserGroupRequest request);

    List<String> getPosition();
    GroupUserDTO getMemberInfo(Long memberId);
    Map<String,List<GroupUserDTO>> getMembers(Long userId,Long groupId) throws BadHanyuPinyinOutputFormatCombination;

    boolean updateMemberGroupInfo(GroupUserDTO groupUserDTO,Long userId);

    Long selectMyselfIdsByUserId(Long userId,Long groupId);
}
