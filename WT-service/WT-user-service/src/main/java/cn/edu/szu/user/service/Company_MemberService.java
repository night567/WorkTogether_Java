package cn.edu.szu.user.service;

import java.util.List;

public interface Company_MemberService {
    /**
     * 根据公司ID查询用户ID集合
     * @param companyID
     * @return 用户ID集合
     */
    List<Integer> selectUserIdByCID(Integer companyID);
}
