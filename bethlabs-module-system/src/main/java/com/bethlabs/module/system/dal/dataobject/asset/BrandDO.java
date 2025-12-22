package com.bethlabs.module.system.dal.dataobject.asset;

import com.baomidou.mybatisplus.annotation.*;
import com.bethlabs.framework.mybatis.core.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 品牌表
 *
 * @author ruoyi
 * @author 芋道源码
 */
@TableName("ops_brand")
@Data
@EqualsAndHashCode(callSuper = true)
public class BrandDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 品牌编号
     */
    @TableField("brand_code")
    private String brandCode;

    /**
     * 品牌名称
     */
    @TableField("brand_name")
    private String brandName;

    /**
     * 设备类型（关联字典表dict_type=brand_type的value）
     */
    @TableField("dev_type")
    private String devType;

    /**
     * 设备类型标签（非数据库字段，用于接收字典表的label展示）
     */
    @TableField(exist = false)
    private String devTypeLabel;

    /**
     * 设备数量
     */
    @TableField(exist = false)
    private Integer devNum;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}
