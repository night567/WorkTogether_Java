package cn.edu.szu.auth;

import cn.edu.szu.auth.domain.AuthAuthority;
import cn.edu.szu.auth.domain.AuthRoleAuthority;
import cn.edu.szu.auth.domain.AuthRoleAuthorityList;
import cn.edu.szu.auth.service.AuthRoleAuthorityService;
import cn.edu.szu.auth.service.impl.AuthRoleAuthorityServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class RoleAuthorityTest {

    @Autowired
    AuthRoleAuthorityService authorityService;
    @Test
    public void test(){
        AuthRoleAuthority authorityRole = new AuthRoleAuthority();
        AuthRoleAuthorityList roleAuthorityList = new AuthRoleAuthorityList();

        // 创建权限列表
        List<AuthAuthority> authorities = new ArrayList<>();
        roleAuthorityList.setRoleId(1772514141311909890l);

        // 添加五条数据

        AuthAuthority authority = new AuthAuthority();
        authority.setAuthorityId((long) 1);
        authority.setAuthorityType("MENU"); // 设置权限类型
        authorities.add(authority);
        AuthAuthority authority1 = new AuthAuthority();
        authority1.setAuthorityId((long) 2);
        authority1.setAuthorityType("MENU"); // 设置权限类型
        authorities.add(authority1);
        roleAuthorityList.setAuthorities(authorities);
//        System.out.println(authorities);
        authorityService.addRoleAuthority(roleAuthorityList);
//        System.out.println(authorityService.addRoleAuthoritys(roleAuthorityList));

    }

    @Test
    public void testdel(){
        authorityService.delRoleAuthorityById(1772606035119435778l);
    }
    @Test
    public void testdels(){
        List<Long> list = new ArrayList<>();
        list.add(1772610808837169153l);
        list.add(1772610809101410306l);
        authorityService.delRoleAuthorityByIds(list);
    }

    @Test
    public void testdeleteByAuId(){
        authorityService.deleteByRoleId(1l);
    }

}
;