package cn.edu.szu.company.controller;


import cn.edu.szu.company.service.CompanyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company_user")
public class CompanyUserController {
    @Autowired private CompanyUserService companyUserService;

    /**
     *  根据公司ID返回用户ID集合
     * @param companyId 公司ID
     * @return 用户ID集合
     */
    @GetMapping("/selectUserIdsByCID")
    public List<Long> selectUserIdsByCID(@RequestParam Long companyId){
        List<Long> userIds= companyUserService.selectUserIdsByCompanyId(companyId);
        return userIds;
    }

    @GetMapping("/deleteMember")
    public boolean deleteMember(@RequestParam Long memberId){
        boolean b = companyUserService.deleteMember(memberId);
        return b;
    }


}
