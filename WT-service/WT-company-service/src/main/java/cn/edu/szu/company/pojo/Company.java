package cn.edu.szu.company.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "wt_company")
public class Company implements Serializable {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id; //主键
    @TableField(value = "name")
    private String name; //公司名称
    @TableField(value = "founder_id")
    private Long founder_id; //公司负责人ID
    @TableField(value = "size")
    private Integer size;  //公司规模
    @TableField(value = "address")
    private String address;  //公司地址
}