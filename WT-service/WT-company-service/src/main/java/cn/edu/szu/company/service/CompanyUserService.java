package cn.edu.szu.company.service;

import cn.edu.szu.company.pojo.MemberDTO;

import java.util.List;

public interface CompanyUserService {
    List<Long> selectUserIdsByCompanyId(Long companyId);

    Long getCompanyIdByInviteCode(String code);

    List<MemberDTO> getAllMember(Long companyId,Long deptId);

    boolean joinCompany(String token, String code);

    boolean setMemberAsDeleted(Long memberId, Long companyId);
}
