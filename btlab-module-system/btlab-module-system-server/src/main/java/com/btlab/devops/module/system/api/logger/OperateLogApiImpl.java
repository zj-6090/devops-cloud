package com.btlab.devops.module.system.api.logger;

import com.btlab.devops.framework.common.biz.system.logger.dto.OperateLogCreateReqDTO;
import com.btlab.devops.framework.common.pojo.CommonResult;
import com.btlab.devops.framework.common.pojo.PageResult;
import com.btlab.devops.framework.common.util.object.BeanUtils;
import com.btlab.devops.module.system.api.logger.dto.OperateLogPageReqDTO;
import com.btlab.devops.module.system.api.logger.dto.OperateLogRespDTO;
import com.btlab.devops.module.system.dal.dataobject.logger.OperateLogDO;
import com.btlab.devops.module.system.service.logger.OperateLogService;
import org.springframework.context.annotation.Primary;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.btlab.devops.framework.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
@Primary // 由于 OperateLogCommonApi 的存在，必须声明为 @Primary Bean
public class OperateLogApiImpl implements OperateLogApi {

    @Resource
    private OperateLogService operateLogService;

    @Override
    public CommonResult<Boolean> createOperateLog(OperateLogCreateReqDTO createReqDTO) {
        operateLogService.createOperateLog(createReqDTO);
        return success(true);
    }

    @Override
    public CommonResult<PageResult<OperateLogRespDTO>> getOperateLogPage(OperateLogPageReqDTO pageReqDTO) {
        PageResult<OperateLogDO> operateLogPage = operateLogService.getOperateLogPage(pageReqDTO);
        return success(BeanUtils.toBean(operateLogPage, OperateLogRespDTO.class));
    }

}
