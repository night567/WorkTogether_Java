package cn.edu.szu.company.service.impl;

import cn.edu.szu.company.mapper.CompanyMapper;
import cn.edu.szu.company.mapper.CompanyUserMapper;
import cn.edu.szu.company.mapper.GroupMapper;
import cn.edu.szu.company.mapper.GroupUserMapper;
import cn.edu.szu.company.pojo.GroupDTO;
import cn.edu.szu.company.pojo.MemberDTO;
import cn.edu.szu.company.pojo.domain.*;
import cn.edu.szu.company.service.GroupService;
import cn.edu.szu.feign.client.UserClient;
import cn.edu.szu.feign.pojo.UserDTO;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zgr24
 * @description 针对表【wt_group(团队)】的数据库操作Service实现
 * @createDate 2024-04-28 16:39:41
 */
@Service
public class GroupServiceImpl implements GroupService {
    private final GroupMapper groupMapper;
    private final GroupUserMapper groupUserMapper;
    private final CompanyUserMapper companyUserMapper;
    private final CompanyMapper companyMapper;
    private final UserClient userClient;

    public GroupServiceImpl(GroupMapper groupMapper, GroupUserMapper groupUserMapper, CompanyUserMapper companyUserMapper, CompanyMapper companyMapper, UserClient userClient) {
        this.groupMapper = groupMapper;
        this.groupUserMapper = groupUserMapper;
        this.companyUserMapper = companyUserMapper;
        this.companyMapper = companyMapper;
        this.userClient = userClient;
    }

    /**
     * 创建团队信息
     *
     * @param companyId 公司ID，用于指定团队所属公司
     * @param groupDTO 团队数据传输对象，包含团队的基本信息
     * @return boolean 创建成功返回true，否则返回false
     */
    @Override
    @Transactional
    public boolean createGroup(Long companyId, GroupDTO groupDTO) {
        // 判断传入的基本信息是否为空
        if (groupDTO == null || companyId == null || groupDTO.getName() == null || groupDTO.getManagerId() == null) {
            return false;
        }

        // 初始化Group对象并设置团队信息
        Group group = new Group();
        group.setName(groupDTO.getName());
        group.setManagerId(groupDTO.getManagerId());
        group.setCompanyId(companyId);
        group.setDescription(groupDTO.getDescription());
        group.setCreateTime(new Date());
        group.setMemberNum(1);

        // 根据团队管理员ID查询管理员在公司的信息，以确认其存在性
        LambdaQueryWrapper<CompanyUser> lqw = new LambdaQueryWrapper<>();
        lqw.eq(CompanyUser::getUserId, group.getManagerId());
        lqw.eq(CompanyUser::getCompanyId, group.getCompanyId());
        CompanyUser companyUser = companyUserMapper.selectOne(lqw);
        if (companyUser == null) {
            return false;
        }

        // 设置团队的部门数量，未指定部门则为0
        if (companyUser.getDeptId() == null) {
            group.setDeptNum(0);
        } else {
            group.setDeptNum(1);
        }

        // 将团队信息和团队成员信息插入数据库
        groupMapper.insert(group);
        groupUserMapper.insert(new GroupUser(null, group.getManagerId(), group.getId(), new Date(), false, 1L));

        return true;
    }


    /**
     * 通过Excel文件创建团队。
     *
     * @param companyId 公司ID，用于指定团队所属的公司。
     * @param groupFile 包含团队信息的Excel文件。
     * @return 总是返回true，表示创建过程完成。如果有异常发生，则会抛出运行时异常。
     */
    @Override
    @Transactional
    public boolean createByExcel(Long companyId, MultipartFile groupFile) {
        // 检查输入参数是否为null
        if (companyId == null || groupFile == null) {
            throw new RuntimeException("上传文件异常");
        }

        // 通过Excel文件逐个添加团队
        try {
            // 读取Excel文件
            ExcelReader reader = ExcelUtil.getReader(groupFile.getInputStream());
            List<List<Object>> rowList = reader.read(2); // 从第二行开始读取数据
            for (List<Object> row : rowList) {
                // 打印行数据用于调试
                System.out.println(row.get(0) + ", " + row.get(1) + ", " + row.get(2));
                // 初始化团队信息
                GroupDTO groupDTO = new GroupDTO();
                groupDTO.setName(row.get(0).toString());
                // 获取并设置团队经理的ID
                Long managerId = userClient.getUserByMail(row.get(1).toString());
                if (managerId == null) {
                    throw new RuntimeException("用户不存在");
                }
                groupDTO.setManagerId(managerId);
                groupDTO.setDescription(row.get(2).toString());

                // 创建团队
                boolean isCreated = createGroup(companyId, groupDTO);
                if (!isCreated) {
                    throw new RuntimeException("创建失败");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    @Override
    public boolean deleteGroup(Long deptId) {
        if (deptId == null) {
            return false;
        }

        int deleted = groupMapper.deleteById(deptId);
        return deleted > 0;
    }

    @Override
    public boolean updateGroup(GroupDTO groupDTO) {
        // 基本信息判断
        if (groupDTO == null || groupDTO.getId() == null || groupDTO.getName() == null || groupDTO.getManagerId() == null) {
            return false;
        }

        //创建数据库对象
        Group group = new Group();
        group.setId(groupDTO.getId());
        group.setName(groupDTO.getName());
        group.setManagerId(groupDTO.getManagerId());
        group.setDescription(groupDTO.getDescription());

        int updated = groupMapper.updateById(group);
        return updated > 0;
    }

    @Override
    public List<GroupDTO> getAllGroup(Long companyId) {
        // 基础检查
        if (companyId == null) {
            return new ArrayList<>();
        }

        System.out.println("companyId:" + companyId);

        // 查数据库
        // 1.查询公司信息
        Company company = companyMapper.selectById(companyId);
        String companyName = company.getName();
        // 2.查询所有团队信息
        LambdaQueryWrapper<Group> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Group::getCompanyId, companyId);
        List<Group> groupList = groupMapper.selectList(lqw);

        // 封装数据
        List<GroupDTO> groupDTOList = new ArrayList<>();
        for (Group group : groupList) {
            if (group == null) {
                continue;
            }
            GroupDTO groupDTO = new GroupDTO(group);
            groupDTO.setCompanyName(companyName);
            UserDTO user = userClient.getUserById(group.getManagerId());
            groupDTO.setManager(user);
            groupDTOList.add(groupDTO);
        }

        return groupDTOList;
    }

    @Override
    public GroupDTO getGroupById(Long id) {
        // 基础检查
        if (id == null) {
            return null;
        }

        // 查数据库
        // 1.查询团队信息
        Group group = groupMapper.selectById(id);
        if (group != null) {
            GroupDTO groupDTO = new GroupDTO(group);
            // 2.查询公司信息
            Company company = companyMapper.selectById(group.getCompanyId());
            String companyName = company.getName();
            groupDTO.setCompanyName(companyName);
            // 3.查询团队管理员信息
            UserDTO user = userClient.getUserById(group.getManagerId());
            groupDTO.setManager(user);
            return groupDTO;
        }
        return null;
    }

    /**
     * 获取成员列表
     *
     * @param id
     * @return
     */
    @Override
    public List<MemberDTO> getGroupMember(Long id) {
        List<MemberDTO> memberDTOS = groupUserMapper.selectByGroupId(id);
        for (MemberDTO memberDTO : memberDTOS) {
            String userId = memberDTO.getId();
            UserDTO user = userClient.getUserById(Long.valueOf(userId));
            memberDTO.setName(user.getName());
            memberDTO.setEmail(user.getEmail());
//            memberDTO.setPosition("职员");
        }
        System.out.println(memberDTOS);
        return memberDTOS;
    }

    /**
     * 通过邮箱添加用户加入团队
     *
     * @param emails
     * @param gid
     * @return
     */
    @Override
    public boolean addMemberToGroup(List<String> emails, Long gid) {
        if (emails == null || emails.isEmpty()) {
            return false;
        }
        for (String email : emails) {
            System.out.println(email);
            Long id = userClient.getUserByMail(email);
            if (id == null) {
                return false;
            }
            GroupUser groupUser = new GroupUser();
            groupUser.setGroupId(gid);
            groupUser.setUserId(id);
            groupUser.setIsDeleted(false);
            groupUser.setJoinTime(new Date());
            groupUser.setType(1L);
            int k = groupUserMapper.insert(groupUser);
            if (k == 0) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean delMemberFromGroup(Long uid, Long gid) {
        int k = groupUserMapper.delMemberFromGroup(uid, gid);
        if (k == 0) {
            return false;
        }
        return true;
    }

    @Override
    public boolean updateMember(UserGroupRequest request) {
        int k = groupUserMapper.updateMember(request.getId(), request.getGroupId(), request.getType());
        if (k == 0) {
            return false;
        }
        return true;
    }


}




