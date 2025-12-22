package com.bethlabs.module.system.service.config;

import com.bethlabs.module.system.controller.admin.config.vo.SystemConfigSaveReqVO;
import jakarta.validation.Valid;

import java.util.Map;


/**
 * 系统配置 Service 接口
 *
 * @author 芋道源码
 */
public interface SystemConfigService {

    /**
     * 更新系统配置
     *
     * @param updateReqVO 更新信息
     */
    void updateConfig(@Valid Map<String,Object> updateReqVO);

    /**
     * 根据类型获取系统配置
     *
     * @param type
     * @return
     */
    Map<String, Object> getConfigList(Integer type);
}