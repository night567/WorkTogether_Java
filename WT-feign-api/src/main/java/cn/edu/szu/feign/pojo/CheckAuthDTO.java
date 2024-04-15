package cn.edu.szu.feign.pojo;

import lombok.Data;

@Data
public class CheckAuthDTO {
    private Long userId;
    private String resource;
}
