package cn.edu.szu.company.service;

import cn.edu.szu.company.pojo.MemberDTO;

import java.util.List;


public interface CompanyUserService {
    List<Long> selectUserIdsByCompanyId(Long companyId);

    boolean deleteMember(Long memberId);

    List<MemberDTO> getAllMember(Long companyId);
}
