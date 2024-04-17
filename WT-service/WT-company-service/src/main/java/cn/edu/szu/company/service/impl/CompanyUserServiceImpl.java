package cn.edu.szu.company.service.impl;

import cn.edu.szu.company.dao.CompanyUserDao;
import cn.edu.szu.company.pojo.MemberDTO;
import cn.edu.szu.company.service.CompanyUserService;
import cn.edu.szu.feign.client.UserClient;
import cn.edu.szu.feign.pojo.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyUserServiceImpl implements CompanyUserService {
    @Autowired
    private CompanyUserDao companyUserDao;
    @Autowired
    private UserClient userClient;

    @Override
    public List<Long> selectUserIdsByCompanyId(Long companyId) {
        return companyUserDao.selectUserIdsByCompanyId(companyId);
    }

    @Override
    public boolean deleteMember(Long memberId) {
        return companyUserDao.deleteMember(memberId);
    }

    @Override
    public List<MemberDTO> getAllMember(Long companyId) {
        // 获取企业成员数据
        List<MemberDTO> companyUsers = companyUserDao.selectAllByCompanyId(companyId);

        for (MemberDTO member : companyUsers) {
            UserDTO user = userClient.getUserById(member.getId());
            System.out.println(user);
            if (user != null) {
                member.setName(user.getName());
                member.setEmail(user.getEmail());
                member.setPosition("普通职员");
            } else {
                companyUsers.remove(member);
            }
        }

        return companyUsers;
    }
}
