package cn.edu.szu.company.controller;

import cn.edu.szu.common.domain.Code;
import cn.edu.szu.common.domain.Result;
import cn.edu.szu.company.pojo.CompanyUser;
import cn.edu.szu.company.service.CompanyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/company_user")
public class CompanyUserController {
    @Autowired private CompanyUserService companyUserService;

    public List<Long>selectUserIdsByCID(Long companyId){
        List<Long> userIds= companyUserService.selectUserIdsByCompanyId(companyId);
        return userIds;
    }
}
