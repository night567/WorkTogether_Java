package cn.edu.szu.user.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 王奇艳
 * 公司-用户关系实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company_User {
    private Integer id;
    //用户ID
    private Integer user_id;
    //公司ID
    private Integer company_id;
    //加入公司的时间
    private String join_time;
}
