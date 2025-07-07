package com.btlab.devops.module.infra.api.logger;

import com.btlab.devops.framework.common.biz.infra.logger.ApiErrorLogCommonApi;
import com.btlab.devops.framework.common.biz.infra.logger.dto.ApiErrorLogCreateReqDTO;
import com.btlab.devops.framework.common.pojo.CommonResult;
import com.btlab.devops.module.infra.service.logger.ApiErrorLogService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.btlab.devops.framework.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class ApiErrorLogApiImpl implements ApiErrorLogCommonApi {

    @Resource
    private ApiErrorLogService apiErrorLogService;

    @Override
    public CommonResult<Boolean> createApiErrorLog(ApiErrorLogCreateReqDTO createDTO) {
        apiErrorLogService.createApiErrorLog(createDTO);
        return success(true);
    }

}
