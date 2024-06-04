package cn.edu.szu.user.controller;

import cn.edu.szu.common.pojo.Code;
import cn.edu.szu.common.pojo.Result;
import cn.edu.szu.feign.client.CompanyClient;
import cn.edu.szu.feign.pojo.UserDTO;
import cn.edu.szu.user.pojo.LoginDTO;
import cn.edu.szu.user.pojo.domain.User;
import cn.edu.szu.user.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private CompanyClient companyClient;


    @PostMapping("/createUser")
    public Result createAccount(@RequestBody LoginDTO loginDTO) {
        return userService.createAccount(loginDTO);
    }
    @PostMapping("/login")
    public Result login(@RequestBody LoginDTO loginDTO) {
        return userService.login(loginDTO);
    }

    @GetMapping("/findByEmail/{email}")
    public Result getUserByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        if (user != null) {
            return new Result(Code.GET_OK, user, "获取成功");
        } else {
            return new Result(Code.GET_ERR, null, "获取失败");
        }
    }
    @GetMapping("/findIdByEmail/{email}")
    public Long getIdByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        if (user == null){
            return null;
        }
        return user.getId();
    }
    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @GetMapping("/company/{id}")
    public Result getUserByCompany(@PathVariable Long id) {
        List<User> users = userService.getUserByCompany(id);
        if (users != null) {
            return new Result(Code.GET_OK, users, "获取成功");
        } else {
            return new Result(Code.GET_ERR, null, "获取失败");
        }
    }

    public Result updateStatus(User user) {
        boolean b = userService.updateById(user);
        if (b) {
            return new Result(Code.UPDATE_OK, true, "修改成功");
        } else {
            return new Result(Code.UPDATE_ERR, false, "修改失败");
        }
    }

    @DeleteMapping("/delete_member")
    public Result deleteMember(@Param(value = "ids") String ids,@RequestHeader("companyId") Long companyId) {

        boolean flag = companyClient.setMemberAsDeleted(Long.valueOf(ids.trim()),companyId);
        if (!flag) {
            return new Result(Code.DELETE_ERR, null, "移除失败");
        }

        return new Result(Code.DELETE_OK, null, "移除成功");
    }

    @DeleteMapping("/delete_members")
    public Result deleteMembers(@RequestParam List<Long> ids,@RequestHeader("companyId") Long companyId) {
        System.out.println(ids);
        for (Long id : ids){
            boolean flag = companyClient.setMemberAsDeleted(id,companyId);
            if (!flag) {
                return new Result(Code.DELETE_ERR, null, "移除失败");
            }

        }
        return new Result(Code.DELETE_OK, null, "移除成功");
    }

    @PutMapping("/update/user")
    public boolean updateUserInfo(@RequestParam String name,@RequestParam String phone,@RequestParam String avatar,@RequestParam Long userId){
        return  userService.updateUserInfo(name,phone,avatar,userId);
    }
}
