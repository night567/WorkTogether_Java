package cn.edu.szu.user.service.impl;

import cn.edu.szu.user.mapper.Company_MemberMapper;
import cn.edu.szu.user.service.Company_MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Company_MemberServiceImpl implements Company_MemberService {
    @Autowired
    private Company_MemberMapper companyMemberMapper;

    /**
     * 根据公司ID查询用户ID集合
     * @param companyID
     * @return 用户ID集合
     */
    @Override
    public List<Integer> selectUserIdByCID(Integer companyID) {
        return companyMemberMapper.selectUserIdByCID(companyID);
    }
}
