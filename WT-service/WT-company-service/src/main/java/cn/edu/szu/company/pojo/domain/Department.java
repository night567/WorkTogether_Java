package cn.edu.szu.company.pojo.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Builder
@Document("wt_department")
public class Department {
    @Id
    private String id;
    @Field("company_id")
    private Long companyID;
    @Field("manager_id")
    private Long managerID;
    @Field("name")
    private String name;
    @Field("description")
    private String description;
    @Field("member_num")
    private Integer memberNum;
    @Field("parent")
    private String parent;
    @Field("children")
    private List<Department> children;
}
