package com.bethlabs.module.system.service.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bethlabs.module.system.controller.admin.config.vo.SystemConfigSaveReqVO;
import com.bethlabs.module.system.dal.dataobject.config.SystemConfigDO;
import com.bethlabs.module.system.dal.mysql.config.SystemConfigMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统配置 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class SystemConfigServiceImpl implements SystemConfigService {

    @Resource
    private SystemConfigMapper systemConfigMapper;

    @Override
    public void updateConfig(Map<String,Object> updateReqVO) {
        // 遍历map根据key和类型进行更新
        if (CollectionUtils.isEmpty(updateReqVO)) {
            return;
        }
        updateReqVO.forEach((key, value) -> {
            systemConfigMapper.updateByAttribute(key, value);
        });
    }

    @Override
    public Map<String, Object> getConfigList(Integer type) {
        LambdaQueryWrapper<SystemConfigDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemConfigDO::getType, type);
        Map<String, Object> result = new HashMap<>();
        systemConfigMapper.selectList(queryWrapper).forEach(item -> {
            result.put(item.getAttribute(), item.getValue());
        });
        return result;
    }
}