package cn.edu.szu.user.pojo;

import lombok.Data;

@Data
public class InviteDTO {
    private String email;
    private Integer type;
    private Long companyId;
}
