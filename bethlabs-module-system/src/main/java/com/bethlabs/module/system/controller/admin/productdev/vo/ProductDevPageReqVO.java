package com.bethlabs.module.system.controller.admin.productdev.vo;

import com.bethlabs.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 产品设备接口分页 Request VO")
@Data
public class ProductDevPageReqVO extends PageParam {

    @Schema(description = "产品ID")
    private Long productId;

}