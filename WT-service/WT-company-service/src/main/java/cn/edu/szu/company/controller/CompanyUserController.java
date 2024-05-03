package cn.edu.szu.company.controller;

import cn.edu.szu.common.pojo.Code;
import cn.edu.szu.common.pojo.Result;
import cn.edu.szu.company.pojo.MemberDTO;
import cn.edu.szu.company.pojo.domain.Company;
import cn.edu.szu.company.service.CompanyService;
import cn.edu.szu.company.service.CompanyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company_user")
public class CompanyUserController {
    @Autowired
    private CompanyUserService companyUserService;
    @Autowired
    private CompanyService companyService;

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
    public boolean deleteMember(@RequestParam Long memberId,@RequestParam Long companyId) {
        boolean b = companyUserService.setMemberAsDeleted(memberId,companyId);
        return b;
    }

    @GetMapping("/getAllMember/{deptId}")
    public Result getAllMember(@RequestHeader("companyId") Long companyId,@PathVariable Long deptId) {
        List<MemberDTO> members = companyUserService.getAllMember(companyId,deptId);
        if (members != null && !members.isEmpty()) {
            return new Result(Code.GET_OK, members, "查询成功");
        } else {
            return new Result(Code.GET_ERR, null, "查询失败");
        }
    }

    @GetMapping("/getCompanyInfo")
    public Result getCompanyInfo(@RequestParam String code) {
        Long companyId = companyUserService.getCompanyIdByInviteCode(code);
        if (companyId != null) {
            Company company = companyService.getCompanyById(companyId);
            if (company != null) {
                return new Result(Code.GET_OK, company, "获取成功");
            }
            return new Result(Code.GET_ERR, null, "获取失败");
        }
        return new Result(Code.GET_ERR, null, "获取失败");
    }

    @PostMapping("/joinCompany/{code}")
    public Result joinCompany(@RequestHeader("Authorization") String token, @PathVariable String code) {
        boolean b = companyUserService.joinCompany(token, code);
        if (b) {
            return new Result(Code.SAVE_OK, true, "加入成功");
        }
        return new Result(Code.SAVE_ERR, false, "加入失败");
    }
}
