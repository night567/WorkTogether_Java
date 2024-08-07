package cn.edu.szu.company.service;

import cn.edu.szu.company.pojo.MemberDTO;

import java.util.List;

public interface CompanyUserService {
    List<Long> selectUserIdsByCompanyId(Long companyId);

    List<Long> selectUserIdsByCompanyIdWithPage(Long companyId, Integer pageNum, Integer pageSize);

    Long getCompanyIdByInviteCode(String code);

    List<MemberDTO> getAllMember(Long companyId);

    boolean joinCompany(String token, String code);

    boolean setMemberAsDeleted(Long memberId, Long companyId);
}
