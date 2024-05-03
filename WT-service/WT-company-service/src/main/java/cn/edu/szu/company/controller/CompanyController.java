package cn.edu.szu.company.controller;

import cn.edu.szu.common.pojo.Code;
import cn.edu.szu.common.pojo.Result;
import cn.edu.szu.common.utils.JwtUtil;
import cn.edu.szu.company.pojo.domain.Company;
import cn.edu.szu.company.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/company")
public class CompanyController {
    @Autowired
    CompanyService companyService;

    /**
     * 更新公司
     *
     * @param company
     * @return
     */
    @PutMapping("/updateCompany")
    public Result updateCompany(@RequestBody Company company) {
        companyService.updateCompany(company);
        return new Result(Code.UPDATE_OK, null, "更新成功");
    }

    /**
     * 删除公司
     * @param id
     * @return
     */
    @DeleteMapping("/delCompany/{id}")
    public Result delCompany(@PathVariable Long id){
        companyService.deleteCompany(id);
        return new Result(Code.DELETE_OK,null,"更新成功");
    }

    /**
     * 新建公司
     * @param company
     * @return
     */
    @PostMapping("/createCompany")
    public Result addCompany(@RequestHeader("Authorization") String token, @RequestBody Company company){
        System.out.println("???");
        Long uid = JwtUtil.getUserId(token);
        company.setFounderId(uid);
        System.out.println(company);
        companyService.addCompany(company);

        return new Result(Code.SAVE_OK,null,"创建公司成功");
    }

    /**
     * 查询公司
     * @param id
     * @return
     */
    @GetMapping("/info/{id}")
    public Result selectCompany(@PathVariable Long id){
        Company company = companyService.selectCompany(id);
        return new Result(Code.GET_OK,company,"创建公司成功");
    }


}
