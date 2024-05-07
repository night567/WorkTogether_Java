package cn.edu.szu.company.pojo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserGroupRequest {
    private List<String> list;
    private Long groupId;
    private Long type;
    private Long id;
}
