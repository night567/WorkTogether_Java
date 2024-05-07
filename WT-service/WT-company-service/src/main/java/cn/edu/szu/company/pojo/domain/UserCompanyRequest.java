package cn.edu.szu.company.pojo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCompanyRequest {
    Long uid;
    Long did;
    Long type;

}
