package com.bethlabs.module.system.controller.admin.alarmtype.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 报警类型 Response VO")
@Data
@ExcelIgnoreUnannotated
public class AlarmTypeRespVO {

    @Schema(description = "报警级别id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "报警名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String alarmLevelId;

    @Schema(description = "报警名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String alarmName;

    @Schema(description = "报警类型编号（平台使用）", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long alarmTypeCode;

    @Schema(description = "报警等级（低-1 中-2 高-3）", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer alarmLevel;

    @Schema(description = "设备报警编号（设备上传的报警编号）", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long deviceAlarmCode;

    @Schema(description = "产品编号", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long productId;

}