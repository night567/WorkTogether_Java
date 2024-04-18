package cn.edu.szu.company.pojo.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 企业
 * @TableName wt_company
 */
@TableName(value ="wt_company")
@Data
public class Company implements Serializable {
    /**
     * 
     */
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 企业名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 创建人id
     */
    @TableField(value = "founder_id")
    private Long founderId;

    /**
     * 企业官网
     */
    @TableField(value = "url")
    private String url;

    /**
     * 所属行业
     */
    @TableField(value = "industry")
    private String industry;

    /**
     * 企业规模
     */
    @TableField(value = "size")
    private String size;

    /**
     * 企业地址
     */
    @TableField(value = "address")
    private String address;

    /**
     * 邮政编码
     */
    @TableField(value = "postal_code")
    private Integer postalCode;

    /**
     * 建立时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 逻辑删除
     */
    @TableField(value = "is_deleted")
    @TableLogic(value = "0",delval = "1")
    private Boolean isDeleted;
}