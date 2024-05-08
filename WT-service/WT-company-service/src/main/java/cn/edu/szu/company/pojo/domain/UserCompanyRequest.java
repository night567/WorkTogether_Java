package cn.edu.szu.company.pojo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCompanyRequest {
    private Long uid;
    private Long did;
    private Long type;
    private String position;
}
