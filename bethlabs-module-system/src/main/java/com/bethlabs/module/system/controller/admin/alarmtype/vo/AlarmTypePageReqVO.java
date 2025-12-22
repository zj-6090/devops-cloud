package com.bethlabs.module.system.controller.admin.alarmtype.vo;

import com.bethlabs.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 报警类型分页 Request VO")
@Data
public class AlarmTypePageReqVO extends PageParam {

    @Schema(description = "产品ID")
    private String productId;

}