package com.bethlabs.module.system.dal.mysql.pagemaintenance;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bethlabs.framework.mybatis.core.mapper.BaseMapperX;
import com.bethlabs.module.system.controller.admin.pagemaintenance.vo.PageMaintenancePageReqVO;
import com.bethlabs.module.system.controller.admin.pagemaintenance.vo.PageMaintenanceRespVO;
import com.bethlabs.module.system.dal.dataobject.pagemaintenance.PageMaintenanceDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 页面维护 Mapper
 *
 * @author mjl
 */
@Mapper
public interface PageMaintenanceMapper extends BaseMapperX<PageMaintenanceDO> {


    IPage<PageMaintenanceRespVO> selectPage(@Param("page") IPage<PageMaintenanceRespVO> page, @Param("vo") PageMaintenancePageReqVO pageReqVO);
}