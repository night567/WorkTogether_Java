package cn.edu.szu.auth;

import cn.edu.szu.auth.domain.UserRoleListDTO;
import cn.edu.szu.auth.service.UserRoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
class UserRoleTest {
    @Autowired
    private UserRoleService userRoleService;

    @Test
    void testSaveRoleToUser() {
        UserRoleListDTO userRoleList = new UserRoleListDTO();
        userRoleList.setUserId(1L);
        ArrayList<Long> list = new ArrayList<>();
        list.add(1772912147827785730L);
        userRoleList.setRoleIds(list);
        userRoleList.setCreateUser(1L);

        userRoleService.saveRoleToUser(userRoleList);
    }
}
