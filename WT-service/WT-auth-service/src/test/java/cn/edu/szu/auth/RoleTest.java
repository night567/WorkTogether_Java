package cn.edu.szu.auth;

import cn.edu.szu.auth.domain.AuthRole;
import cn.edu.szu.auth.service.AuthRoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class RoleTest {
    @Autowired
    private AuthRoleService roleService;

    @Test
    public void testAddRole() {
        AuthRole role = new AuthRole();
        role.setName("Admin1");
        role.setCode("ADMIN1");
        role.setDescribe("Administrator Role1");
        role.setStatus(true);
        role.setReadonly(false);
        role.setCreateUser(1L);
        role.setCreateTime(new Date());
        // 设置其他属性
        // ...

        roleService.addRole(role);

        // 可以添加断言或其他验证逻辑来确保角色添加成功
    }

    @Test
    public void testDelRole(){
        roleService.delRole(1772607007073775617l);
    }

    @Test
    public void testUpdateRole(){
        AuthRole role = new AuthRole();
        role.setId(1772607179489046529l);
        role.setName("admin_update_test");
        roleService.updateById(role);
    }

    @Test
    public void testGetAll(){
        System.out.println(roleService.selectAllRole());
        System.out.println(roleService.selectById(1772607179489046529l));
    }

}
