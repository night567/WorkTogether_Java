package cn.edu.szu.user.controller;

import cn.edu.szu.common.domain.Code;
import cn.edu.szu.common.domain.Result;
import cn.edu.szu.user.pojo.User;
import cn.edu.szu.user.service.Company_MemberService;
import cn.edu.szu.user.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 王奇艳
 * 成员Controller
 */
@RestController
@RequestMapping("/api/member")
public class MemberController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private Company_MemberService companyMemberService;

    @GetMapping("/selectUserByUserIds")
    public Result selectUserByUserIds(@RequestParam Integer company_id){
        if(company_id==null)
            return new Result(Code.GET_ERR,null,"查询失败，公司ID不能为空！");

        List<Integer> user_ids = companyMemberService.selectUserIdByCID(company_id);
        if(user_ids.isEmpty()){
            return new Result(Code.GET_ERR,null,"查询失败，公司ID不存在！");
        }
        List<User> users = memberService.selectUserByUserIds(user_ids);
        if(users.isEmpty()){
            return new Result(Code.GET_ERR,null,"查询失败，公司员工不存在！");
        }
        return new Result(Code.GET_OK,users,"查询成功！");
    }
    @GetMapping("/s")
    public Object select(){
       return "成功";
    }

}
