package cn.edu.szu.company;

import cn.edu.szu.company.pojo.domain.Company;
import cn.edu.szu.company.pojo.domain.Group;
import cn.edu.szu.company.service.CompanyService;
import cn.edu.szu.company.service.DepartmentService;
import cn.edu.szu.company.service.GroupService;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CompanyApplicationTest {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private GroupService groupService;

    @Test
    void test() {
        String temp = "sdfsdfs\n" +
                "sdfsdfsfsd\n" +
                "sdfsdfsfff\n" +
                "aaaaaa";
        for (String s : temp.trim().split("\n")) {
            System.out.println(s);
        }

    }

    @Test
    void test1() {
        departmentService.selectDeptByID(1L);
    }

    @Test
    void test2() {
        List<Company> companyList = companyService.selectMyCompany(1L);
        companyList.forEach(System.out::println);
    }

    @Test
    void test3() {
        List<Group> groupList = groupService.selectMyGroup(1L, 1L);
        groupList.forEach(System.out::println);
    }

    @Test
    void test4() throws BadHanyuPinyinOutputFormatCombination {
        String s = groupService.getFirstLetter("啊哈");
        System.out.println(s);
    }
}
