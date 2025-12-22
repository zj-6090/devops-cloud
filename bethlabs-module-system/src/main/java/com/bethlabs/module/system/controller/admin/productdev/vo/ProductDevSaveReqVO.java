package com.bethlabs.module.system.controller.admin.productdev.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "管理后台 - 产品设备接口新增/修改 Request VO")
@Data
public class ProductDevSaveReqVO {

    @Schema(description = "自增id", requiredMode = Schema.RequiredMode.REQUIRED, example = "10447")
    private Long id;

    @NotEmpty(message = "接口名称不能为空")
    @Schema(description = "接口名称", example = "李四")
    private String interfaceName;

    @Schema(description = "备注")
    private String remarks;

    @Schema(description = "自定义协议cmd")
    @NotNull(message = "cmd不能为空")
    private Integer cmd;

    @Schema(description = "io口")
    private Integer item;

    @Schema(description = "产品id", example = "6133")
    private Long productId;

}