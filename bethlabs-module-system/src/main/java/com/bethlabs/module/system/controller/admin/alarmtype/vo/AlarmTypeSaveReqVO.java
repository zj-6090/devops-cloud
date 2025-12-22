package com.bethlabs.module.system.controller.admin.alarmtype.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "管理后台 - 报警类型新增/修改 Request VO")
@Data
public class AlarmTypeSaveReqVO {

    @Schema(description = "报警级别id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "报警名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "报警等级编号不能为空")
    private String alarmLevelId;

    @Schema(description = "设备报警编号（设备上传的报警编号）", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long deviceAlarmCode;

    @Schema(description = "产品编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "产品编号不能为空")
    private Long productId;

}