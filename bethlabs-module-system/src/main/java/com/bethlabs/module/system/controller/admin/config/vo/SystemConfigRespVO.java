package com.bethlabs.module.system.controller.admin.config.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 系统配置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SystemConfigRespVO {

    @Schema(description = "配置属性")
    @ExcelProperty("配置属性")
    private String attribute;

    @Schema(description = "属性值")
    @ExcelProperty("属性值")
    private String value;

    @Schema(description = "配置类型:1-客户端配置 2-系统功能配置 3-地图配置", example = "2")
    @ExcelProperty("配置类型:1-客户端配置 2-系统功能配置 3-地图配置")
    private Integer type;

}