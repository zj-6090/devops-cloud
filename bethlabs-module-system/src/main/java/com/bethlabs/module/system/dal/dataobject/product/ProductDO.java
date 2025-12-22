package com.bethlabs.module.system.dal.dataobject.product;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 产品表-设备系列 DO
 *
 * @author mjl
 */
@TableName("ops_product")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDO {

    /**
     * 自增id
     */
    @TableId
    private Long id;
    /**
     * 设备产品编号
     */
    private String productCode;
    /**
     * 设备产品名称
     */
    private String productName;
    /**
     * 备注
     */
    private String remark;
    /**
     * 更新者
     */
    private String updateUser;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

}