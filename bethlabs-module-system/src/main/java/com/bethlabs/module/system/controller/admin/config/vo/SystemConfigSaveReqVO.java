package com.bethlabs.module.system.controller.admin.config.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Map;

@Schema(description = "管理后台 - 系统配置新增/修改 Request VO")
@Data
public class SystemConfigSaveReqVO {

    @Schema(description = "配置类型:1-客户端配置 2-系统功能配置 3-地图配置", example = "2")
    private Integer type;

    @Schema(description = "配置数据")
    private Map<String, Object> data;

}