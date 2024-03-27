package cn.edu.szu.auth.domain;

import lombok.Data;

import java.util.List;

@Data
public class UserRoleListDTO {
    private Long userId;
    private List<Long> roleIds;
    private Long createUser;
}
