package cn.edu.szu.teamwork.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportCondition {
    private Integer status;
    private Integer weekNum;
    private String name;
}
