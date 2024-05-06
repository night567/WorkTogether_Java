package cn.edu.szu.company.pojo;

import lombok.Data;

/**
 * 临时返回成员数据，根据后续需要可以返回更多数据
 */
@Data
public class MemberDTO {
    private String id;
    private String name; // 姓名
    private String email; // 邮箱
    private String deptName; // 部门名称
    private String position; // 职位
}
