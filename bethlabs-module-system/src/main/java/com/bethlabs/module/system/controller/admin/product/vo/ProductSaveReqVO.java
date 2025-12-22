package com.bethlabs.module.system.controller.admin.product.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 产品表-设备系列新增/修改 Request VO")
@Data
public class ProductSaveReqVO {

    @Schema(description = "自增id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "设备产品编号")
    private String productCode;

    @Schema(description = "设备产品名称")
    private String productName;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "更新者")
    private String updateUser;

}