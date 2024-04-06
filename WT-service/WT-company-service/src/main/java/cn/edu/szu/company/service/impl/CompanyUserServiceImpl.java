package cn.edu.szu.company.service.impl;

import cn.edu.szu.company.dao.CompanyUserDao;
import cn.edu.szu.company.service.CompanyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyUserServiceImpl implements CompanyUserService {
    @Autowired
    private CompanyUserDao companyUserDao;


    @Override
    public List<Long> selectUserIdsByCompanyId(Long companyId) {
        return companyUserDao.selectUserIdsByCompanyId(companyId);
    }
}
