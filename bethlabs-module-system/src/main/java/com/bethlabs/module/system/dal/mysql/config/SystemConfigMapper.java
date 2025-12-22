package com.bethlabs.module.system.dal.mysql.config;


import com.bethlabs.framework.mybatis.core.mapper.BaseMapperX;
import com.bethlabs.module.system.dal.dataobject.config.SystemConfigDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统配置 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface SystemConfigMapper extends BaseMapperX<SystemConfigDO> {

    void updateByAttribute(String attribute, Object value);
}