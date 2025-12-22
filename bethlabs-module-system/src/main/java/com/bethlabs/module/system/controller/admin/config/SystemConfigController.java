package com.bethlabs.module.system.controller.admin.config;

import com.bethlabs.framework.common.pojo.CommonResult;
import com.bethlabs.module.system.controller.admin.config.vo.SystemConfigSaveReqVO;
import com.bethlabs.module.system.service.config.SystemConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.bethlabs.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 系统配置")
@RestController
@RequestMapping("/system/config")
@Validated
public class SystemConfigController {

    @Resource
    private SystemConfigService systemConfigService;

    @PutMapping("/update")
    @Operation(summary = "更新系统配置")
    public CommonResult<Boolean> updateConfig(@Valid @RequestBody Map<String,Object> updateReqVO) {
        systemConfigService.updateConfig(updateReqVO);
        return success(true);
    }

    @GetMapping("/list")
    @Operation(summary = "系统配置列表")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<Map<String, Object>> getConfigList(@RequestParam("type") Integer type) {
        return success(systemConfigService.getConfigList(type));
    }


}