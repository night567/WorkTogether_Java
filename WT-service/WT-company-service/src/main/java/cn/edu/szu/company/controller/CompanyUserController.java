package cn.edu.szu.company.controller;


import cn.edu.szu.common.pojo.Code;
import cn.edu.szu.common.pojo.Result;
import cn.edu.szu.company.pojo.MemberDTO;
import cn.edu.szu.company.service.CompanyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/company_user")
public class CompanyUserController {
    @Autowired
    private CompanyUserService companyUserService;

    /**
     * 根据公司ID返回用户ID集合
     *
     * @param companyId 公司ID
     * @return 用户ID集合
     */
    @GetMapping("/selectUserIdsByCID")
    public List<Long> selectUserIdsByCID(@RequestParam Long companyId) {
        List<Long> userIds = companyUserService.selectUserIdsByCompanyId(companyId);
        return userIds;
    }

    @GetMapping("/deleteMember")
    public boolean deleteMember(@RequestParam Long memberId) {
        boolean b = companyUserService.deleteMember(memberId);
        return b;
    }

    @GetMapping("/getAllMember")
    public Result getAllMember(@RequestParam Long companyId) {
        List<MemberDTO> members = companyUserService.getAllMember(companyId);
        if (members != null && !members.isEmpty()) {
            return new Result(Code.GET_OK, members, "查询成功");
        } else {
            return new Result(Code.GET_ERR, null, "查询失败");
        }
    }
}
