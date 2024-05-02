package cn.edu.szu.company.service;

import cn.edu.szu.company.pojo.GroupDTO;

import java.util.List;

/**
 * @author zgr24
 * @description 针对表【wt_group(团队)】的数据库操作Service
 * @createDate 2024-04-28 16:39:41
 */
public interface GroupService {
    boolean createGroup(Long companyId, GroupDTO groupDTO);

    boolean deleteGroup(Long deptId);

    boolean updateGroup(GroupDTO groupDTO);

    List<GroupDTO> getAllGroup(Long companyId);

    GroupDTO getGroupById(Long id);
}
