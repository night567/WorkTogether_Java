package cn.edu.szu.company;

import cn.edu.szu.company.pojo.domain.Department;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.ArrayList;

@SpringBootTest
public class MongoDBTest {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void testInsert() {
        Department dept1 = Department.builder()
                .companyID(1L)
                .managerID(1L)
                .name("测试部门")
                .description("测试部门描述")
                .memberNum(1)
                .parent(null)
                .children(null)
                .build();

        mongoTemplate.insert(dept1);
    }

    @Test
    void testGetDepartmentById() {
        Department dept = mongoTemplate.findById("662a40a86c5518150d787509", Department.class);
        System.out.println(dept);
    }

    @Test
    void testUpdateDepartment() {
        Department dept = Department.builder()
                .id("1")
                .companyID(1L)
                .managerID(1L)
                .name("测试部门")
                .description("测试部门描述")
                .memberNum(1)
                .parent("662a406df15b723a35c494b7")
                .children(new ArrayList<>())
                .build();

        mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is("662a406df15b723a35c494b7")),
                new Update().push("children", dept),
                Department.class);
    }
}
