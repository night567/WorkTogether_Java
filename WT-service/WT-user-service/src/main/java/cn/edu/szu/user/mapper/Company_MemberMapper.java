package cn.edu.szu.user.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Company_MemberMapper {
    /**
     * 根据公司ID查询用户ID集合
     * @param companyID
     * @return 用户ID集合
     */
    List<Integer> selectUserIdByCID(Integer companyID);
}
