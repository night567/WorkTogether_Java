package cn.edu.szu.company.service;

import java.util.List;


public interface CompanyUserService {
    List<Long> selectUserIdsByCompanyId(Long companyId);
    boolean deleteMember(Long memberId);
}
