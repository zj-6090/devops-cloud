package com.bethlabs.module.system.dal.mysql.alarmtype;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bethlabs.framework.mybatis.core.mapper.BaseMapperX;
import com.bethlabs.module.system.controller.admin.alarmtype.vo.AlarmTypePageReqVO;
import com.bethlabs.module.system.controller.admin.alarmtype.vo.AlarmTypeRespVO;
import com.bethlabs.module.system.dal.dataobject.alarmtype.AlarmTypeDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 报警类型 Mapper
 *
 * @author mjl
 */
@Mapper
public interface AlarmTypeMapper extends BaseMapperX<AlarmTypeDO> {

    IPage<AlarmTypeRespVO> selectPage(@Param("page") IPage<AlarmTypeRespVO> page, @Param("vo") AlarmTypePageReqVO pageReqVO);

    List<AlarmTypeRespVO> selectPage(@Param("vo") AlarmTypePageReqVO pageReqVO);
}