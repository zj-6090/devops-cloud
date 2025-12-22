package com.bethlabs.module.system.controller.admin.pagemaintenance.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "管理后台 - 页面维护新增/修改 Request VO")
@Data
public class PageMaintenanceEnableReqVO {

    @Schema(description = "自增id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1424")
    private Long id;

    @Schema(description = "是否启用：0-未启用 1-启用")
    private Integer isEnable;

}