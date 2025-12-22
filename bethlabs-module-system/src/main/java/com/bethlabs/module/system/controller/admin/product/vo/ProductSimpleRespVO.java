package com.bethlabs.module.system.controller.admin.product.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ProductSimpleRespVO {

    @Schema(description = "自增id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "设备产品编号")
    private String productCode;

    @Schema(description = "设备产品名称")
    private String productName;
}
