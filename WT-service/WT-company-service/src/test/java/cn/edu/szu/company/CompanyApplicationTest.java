package cn.edu.szu.company;

import cn.edu.szu.company.service.DepartmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CompanyApplicationTest {
    @Autowired
    private DepartmentService departmentService;
    @Test
    void test(){
        String temp = "sdfsdfs\n" +
                "sdfsdfsfsd\n" +
                "sdfsdfsfff\n" +
                "aaaaaa";
        for (String s : temp.trim().split("\n")) {
            System.out.println(s);
        }

    }

    @Test
    void test1(){
        departmentService.selectDeptByID(1L);

    }
}
