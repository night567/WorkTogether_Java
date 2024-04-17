package cn.edu.szu.company.service.impl;

import cn.edu.szu.company.dao.CompanyDao;
import cn.edu.szu.company.pojo.Company;
import cn.edu.szu.company.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyDao companyDao;

    @Override
    public Company getCompanyById(Long id) {
        return companyDao.selectById(id);
    }
}
