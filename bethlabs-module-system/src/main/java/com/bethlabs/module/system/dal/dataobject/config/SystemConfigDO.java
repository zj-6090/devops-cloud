package com.bethlabs.module.system.dal.dataobject.config;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统配置 DO
 *
 * @author 芋道源码
 */
@TableName("ops_config")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemConfigDO {
    /**
     * 配置属性
     */
    private String attribute;
    /**
     * 属性值
     */
    private String value;
    /**
     * 配置类型:1-客户端配置 2-系统功能配置 3-地图配置
     */
    private Integer type;


}